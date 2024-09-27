package com.panshicloud.base.service.timed;

import com.panshicloud.base.remote.constants.TimedTaskCst;
import com.panshicloud.base.remote.service.ITimedTaskManageService;
import com.panshicloud.base.remote.service.ITimedTaskRecordService;
import com.panshicloud.base.service.provider.remote.SystemConfigRemote;
import com.panshicloud.base.service.util.RedisLockUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.rpc.service.GenericException;
import org.apache.dubbo.rpc.service.GenericService;
import org.apache.http.client.utils.DateUtils;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 定时任务执行核心
 */
@Slf4j
public class TimedJobBean extends QuartzJobBean {

    @Autowired
    private SystemConfigRemote systemConfigRemote;
    @Autowired
    private ITimedTaskManageService timedTaskService;
    @Autowired
    private ITimedTaskRecordService timedTaskRecordService;
    @Autowired
    private RedisLockUtils redisLockUtils;

    @Override
    protected void executeInternal(JobExecutionContext context) {
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        // 方法名
        String methodName = TimedTaskCst.TIMED_TASK_INTERFACE_CALL_METHOD_NAME;
        // 方法code
        String methodCode = jobDataMap.get(TimedTaskCst.METHOD_CODE).toString();
        // 方法路径
        String methodClassPath = jobDataMap.get(TimedTaskCst.METHOD_CLASS_PATH).toString();
        // 服务code
        String serviceCode = jobDataMap.get(TimedTaskCst.SERVICE_CODE).toString();
        // 传参
        String params = jobDataMap.get(TimedTaskCst.PARAMS).toString();
        // 定时任务ID
        String taskId = jobDataMap.get(TimedTaskCst.TASK_ID).toString();
        // 用户
        String user = jobDataMap.get(TimedTaskCst.OPERATE_COMMONCON_TEXT_USER).toString();
        // 执行用户
        String operateUserName = timedTaskService.getById(taskId).getOperateUserName();
        // 定时任务流程标识
        String operationTimedCode = serviceCode + methodName + DateUtils.formatDate(new Date(), TimedTaskCst.DATE_FORMAT_YYYYMMDDHHMMSSSSS);
        // 创建传参对象
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("operationTimedCode", operationTimedCode);
        paramsMap.put("operateUserName", operateUserName);
        paramsMap.put("params", params);
        paramsMap.put("user", user);
        timedTaskRecordService.insert(operateUserName, TimedTaskCst.TIMED_TASK_EXECUTION_START_CONTENT, Boolean.TRUE, taskId, operationTimedCode);
        try {
            // 获取锁-判断当前任务是否正在执行，如果正在执行直接打印到日志中。
            boolean lock = redisLockUtils.lock(TimedTaskCst.TASK_JOB_EXECUTE_REDIS_LOCK_KEY + taskId, taskId, 7, TimeUnit.DAYS);
            if (lock) {
                timedTaskRecordService.insert(operateUserName, "开始调用：【" + serviceCode + "】服务的【" + methodName + "】方法，传入参数：【" + params + "】", Boolean.TRUE, taskId, operationTimedCode);
                GenericService genericService = systemConfigRemote.getGenericService(methodClassPath, serviceCode);
                genericService.$invokeAsync(methodName, new String[]{"java.lang.String","java.util.Map"}, new Object[]{methodCode,paramsMap});
            } else {
                timedTaskRecordService.insert(operateUserName, "【" + serviceCode + "】服务的【" + methodName + "】方法定时任务-执行失败！【上次任务未执行完，本次任务延时】，跳过执行，时间：【" + new SimpleDateFormat("yyyy年MM月dd日  HH时mm分ss秒").format(new Date()) + "】", Boolean.FALSE, taskId, operationTimedCode);
                timedTaskRecordService.timedTaskComplete(operationTimedCode, Boolean.FALSE);
            }
        } catch (GenericException e) {
            StackTraceElement stackTraceElement = e.getStackTrace()[0];
            timedTaskRecordService.insert(operateUserName, "【" + serviceCode + "】服务的【" + methodName + "】方法定时任务出现错误：错误信息：【" + stackTraceElement.getClassName() + "】类，【" + stackTraceElement.getMethodName() + "】方法，【第" + stackTraceElement.getLineNumber() + "】行出现错误，错误信息【" + e.getMessage() + "】", Boolean.FALSE, taskId, operationTimedCode);
            timedTaskRecordService.timedTaskComplete(operationTimedCode);
        }
    }
}