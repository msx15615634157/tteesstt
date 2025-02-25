package com.panshicloud.base.service.provider;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.panshicloud.base.dao.entity.TimedTaskRecord;
import com.panshicloud.base.dao.mapper.TimedTaskRecordMapper;
import com.panshicloud.base.remote.constants.TimedTaskCst;
import com.panshicloud.base.remote.dto.timed.TimedTaskRecordDto;
import com.panshicloud.base.remote.service.ITimedTaskRecordService;
import com.panshicloud.base.service.util.RedisLockUtils;
import com.panshicloud.common.base.PageDto;
import com.panshicloud.common.constants.JpErrorCodeCst;
import com.panshicloud.common.helper.ExceptionHelper;
import com.panshicloud.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author shimengmeng
 * @since 2024/4/29
 */
@DubboService
@Slf4j
public class TimedTaskRecordServiceProvider extends ServiceImpl<TimedTaskRecordMapper, TimedTaskRecord> implements ITimedTaskRecordService {

    @Autowired
    private TimedTaskRecordMapper timedTaskRecordMapper;
    @Autowired
    private RedisLockUtils redisLockUtils;

    @Override
    public PageDto<TimedTaskRecordDto> findByTask(String taskId, Date startTime, Date endTime, String status, Integer pageNumber, Integer pageSize) {
        // 先查询流程标识
        QueryWrapper<TimedTaskRecord> wrapper = new QueryWrapper();
        wrapper.select("DISTINCT OPERATION_TIMED_CODE").orderByDesc("OPERATION_TIMED_CODE");
        if (null != startTime) {
            wrapper.ge("CREATE_TIME", startTime);
        }
        if (null != endTime) {
            wrapper.le("CREATE_TIME", endTime);
        }
        if (null != status && StringUtils.isNotBlank(status)) {
            if (status.equals(TimedTaskCst.TIMED_TASK_EXECUTION_INPROGRESS)) {

            } else {
                wrapper.eq("STATUS", status);
            }
        }
        wrapper.eq("TASK_ID", taskId);
        IPage<TimedTaskRecord> page = new Page<>(pageNumber, pageSize);
        page = timedTaskRecordMapper.selectPage(page, wrapper);
        // 查询各流程下的日志信息进行处理
        LambdaQueryWrapper<TimedTaskRecord> queryWrapper = new LambdaQueryWrapper<>();
        page.getRecords().forEach(item -> {
            queryWrapper.clear();
            queryWrapper.eq(TimedTaskRecord::getOperationTimedCode, item.getOperationTimedCode());
            queryWrapper.orderByAsc(TimedTaskRecord::getCreateTime);
            List<TimedTaskRecord> list = list(queryWrapper);
            if (null == list || list.isEmpty()) {
                return;
            }
            // 开始日志
            TimedTaskRecord startTimedTask = list.stream().filter(timedtaskRecprd -> timedtaskRecprd.getContent().equals(TimedTaskCst.TIMED_TASK_EXECUTION_START_CONTENT)).collect(Collectors.toList()).get(0);
            // 结束日志
            List<TimedTaskRecord> endTimedTask = list.stream().filter(timedtaskRecprd -> timedtaskRecprd.getContent().equals(TimedTaskCst.TIMED_TASK_EXECUTION_COMPLETE_CONTENT)).collect(Collectors.toList());
            // 日志集合
            List<String> statusList = list.stream().map(TimedTaskRecord::getStatus).collect(Collectors.toList());
            //设置执行时间
            item.setTaskId(startTimedTask.getTaskId());
            item.setOperateUserName(startTimedTask.getOperateUserName());
            item.setCreateTime(startTimedTask.getCreateTime());
            item.setStartTime(startTimedTask.getCreateTime());
            if (endTimedTask.isEmpty()) {
                Date nowDate = new Date();
                item.setEndTime(nowDate);
                item.setCostTime((nowDate.getTime() - startTimedTask.getCreateTime().getTime()) + "ms");
                item.setStatus(TimedTaskCst.TIMED_TASK_EXECUTION_INPROGRESS);
            } else {
                TimedTaskRecord taskRecord = endTimedTask.get(0);
                item.setEndTime(taskRecord.getCreateTime());
                item.setCostTime((taskRecord.getCreateTime().getTime() - startTimedTask.getCreateTime().getTime()) + "ms");
                if (statusList.contains(TimedTaskCst.TIMED_TASK_EXECUTION_ERROR)) {
                    item.setStatus(TimedTaskCst.TIMED_TASK_EXECUTION_ERROR);
                } else {
                    item.setStatus(TimedTaskCst.TIMED_TASK_EXECUTION_SUCCESS);
                }
            }

            //设置日志信息
            List<String> contentList = list.stream().map(TimedTaskRecord::getContent).collect(Collectors.toList());
            StringBuffer contentBuffer = new StringBuffer();
            contentBuffer.append("定时任务执行完成日志如下：");
            AtomicInteger index = new AtomicInteger(1);
            contentList.forEach(content -> {
                contentBuffer.append("<div style='text-indent:28px'>").append(index.get()).append(".").append(content).append("</div>");
                index.getAndIncrement();
            });
            item.setContent(contentBuffer.toString());
        });
        // 设置开始时间
        return new PageDto<>(page, TimedTaskRecordDto.class);
    }

    @Override
    public void insert(String operationTimedCode, String content, Boolean status) {
        TimedTaskRecord taskRecord = new TimedTaskRecord();
        taskRecord.setOperationTimedCode(operationTimedCode);
        taskRecord.setContent(content);
        taskRecord.setStatus(TimedTaskCst.TIMED_TASK_EXECUTION_ERROR);
        taskRecord.setCostTime(TimedTaskCst.COST_TIME);
        if (status) {
            taskRecord.setStatus(TimedTaskCst.TIMED_TASK_EXECUTION_SUCCESS);
        }
        save(taskRecord);
    }

    @Override
    public void insert(String operateUserName, String content, Boolean status, String taskId, String operationTimedCode) {
        TimedTaskRecord timedTaskRecord = new TimedTaskRecord();
        timedTaskRecord.setOperateUserName(operateUserName);
        timedTaskRecord.setCostTime(TimedTaskCst.COST_TIME);
        timedTaskRecord.setContent(content);
        timedTaskRecord.setStatus(TimedTaskCst.TIMED_TASK_EXECUTION_ERROR);
        if (status) {
            timedTaskRecord.setStatus(TimedTaskCst.TIMED_TASK_EXECUTION_SUCCESS);
        }
        timedTaskRecord.setTaskId(taskId);
        timedTaskRecord.setOperationTimedCode(operationTimedCode);
        save(timedTaskRecord);
    }

    @Override
    public void timedTaskComplete(String operationTimedCode, Boolean unlock) {
        String taskId = null;
        try {
            LambdaQueryWrapper<TimedTaskRecord> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(TimedTaskRecord::getOperationTimedCode, operationTimedCode);
            queryWrapper.eq(TimedTaskRecord::getContent, TimedTaskCst.TIMED_TASK_EXECUTION_START_CONTENT);
            TimedTaskRecord taskRecord = getOne(queryWrapper);
            if (null == taskRecord) {
                log.error("未查询到定时任务日志信息,operationTimedCode:【" + operationTimedCode + "】");
                throw ExceptionHelper.newException(JpErrorCodeCst.SYSTEM_ERROR, "未查询到定时任务日志信息,operationTimedCode:【" + operationTimedCode + "】");
            }
            taskId = taskRecord.getTaskId();
            String operateUserName = taskRecord.getOperateUserName();
            this.insert(operateUserName, "定时任务执行完成！", Boolean.TRUE, taskId, operationTimedCode);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.updateOperateUserName(taskId, operationTimedCode, operateUserName);
        } finally {
            if (unlock) {
                redisLockUtils.unlock(TimedTaskCst.TASK_JOB_EXECUTE_REDIS_LOCK_KEY + taskId);
            }
        }
    }

    @Override
    public void timedTaskComplete(String operationTimedCode) {
        String taskId = null;
        try {
            LambdaQueryWrapper<TimedTaskRecord> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(TimedTaskRecord::getOperationTimedCode, operationTimedCode);
            queryWrapper.eq(TimedTaskRecord::getContent, TimedTaskCst.TIMED_TASK_EXECUTION_START_CONTENT);
            TimedTaskRecord taskRecord = getOne(queryWrapper);
            if (null == taskRecord) {
                log.error("未查询到定时任务日志信息,operationTimedCode:【" + operationTimedCode + "】");
                throw ExceptionHelper.newException(JpErrorCodeCst.SYSTEM_ERROR, "未查询到定时任务日志信息,operationTimedCode:【" + operationTimedCode + "】");
            }
            taskId = taskRecord.getTaskId();
            String operateUserName = taskRecord.getOperateUserName();
            this.insert(operateUserName, "定时任务执行完成！", Boolean.TRUE, taskId, operationTimedCode);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.updateOperateUserName(taskId, operationTimedCode, operateUserName);
        } finally {
            redisLockUtils.unlock(TimedTaskCst.TASK_JOB_EXECUTE_REDIS_LOCK_KEY + taskId);
        }
    }

    @Override
    public void updateOperateUserName(String taskId, String operationTimedCode, String operateUserName) {
        // 通过流程标识查询出当前流程所有日志记录
        LambdaQueryWrapper<TimedTaskRecord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TimedTaskRecord::getOperationTimedCode, operationTimedCode);
        List<TimedTaskRecord> list = list(queryWrapper);
        List<String> statusList = list.stream().map(TimedTaskRecord::getStatus).collect(Collectors.toList());
        String status = TimedTaskCst.TIMED_TASK_EXECUTION_SUCCESS;
        if (statusList.contains(TimedTaskCst.TIMED_TASK_EXECUTION_ERROR)) {
            status = TimedTaskCst.TIMED_TASK_EXECUTION_ERROR;
        }
        // 批量修改操作用户、状态
        String finalStatus = status;
        list.forEach(item -> {
            item.setOperateUserName(operateUserName);
            item.setTaskId(taskId);
            item.setStatus(finalStatus);
        });
        updateBatchById(list);
    }

    @Override
    public void delete(String taskId) {
        LambdaQueryWrapper<TimedTaskRecord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TimedTaskRecord::getTaskId, taskId);
        remove(queryWrapper);
    }
}
