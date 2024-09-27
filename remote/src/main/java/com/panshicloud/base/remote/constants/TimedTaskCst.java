package com.panshicloud.base.remote.constants;

/**
 * @author zhangze
 * @version V1.0
 * @date 2024年05月20日 16:35:38
 * @packageName com.panshicloud.base.timed.constants
 * @className TimedTaskCst
 * @describe 定时任务常量
 */
public class TimedTaskCst {

    /**
     * 定时任务锁
     */
    public static final String TASK_JOB_EXECUTE_REDIS_LOCK_KEY = "task:execute:lock:";

    /**
     * 定时任务路径
     */
    public static final String TASK_JOB_CLASS = "com.panshicloud.base.service.timed.TimedJobBean";

    /**
     * 调用其他服务的方法名
     */
    public static final String TIMED_TASK_INTERFACE_CALL_METHOD_NAME = "execute";

    /**
     * 分组CODE
     */
    public static final String SERVICE_CODE = "serviceCode";

    /**
     * 方法CODE
     */
    public static final String METHOD_CODE = "methodCode";

    /**
     * 要调用的方法路径
     */
    public static final String METHOD_CLASS_PATH = "methodClassPath";

    /**
     * 要调用的方法名
     */
    public static final String METHOD_NAME = "methodName";

    /**
     * 参数标识
     */
    public static final String PARAMS = "params";

    /**
     * 执行用户
     */
    public static final String OPERATE_USER_NAME = "operateUserName";

    public static final String OPERATE_COMMONCON_TEXT_USER = "commonContextUser";

    /**
     * 任务ID
     */
    public static final String TASK_ID = "taskId";

    /**
     * 无参数
     */
    public static final String PARAMS_NULL = "null";

    /**
     * 默认耗时
     */
    public static final String COST_TIME = "0ms";

    /**
     * 定时任务启动状态
     */
    public static final Integer TASK_JOB_START = 1;

    /**
     * 定时任务停止状态
     */
    public static final Integer TASK_JOB_STOP = 2;

    /**
     * 定时任务执行成功
     */
    public static final String TIMED_TASK_EXECUTION_SUCCESS = "true";

    /**
     * 定时任务执行失败
     */
    public static final String TIMED_TASK_EXECUTION_ERROR = "false";

    /**
     * 定时任务执行进行中
     */
    public static final String TIMED_TASK_EXECUTION_INPROGRESS = "inProgress";

    /**
     * 时间格式yyyy-MM-dd hh:mm:ss
     */
    public static final String DATE_FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd hh:mm:ss";

    /**
     * 时间格式yyyyMMddHHmmss
     */
    public static final String DATE_FORMAT_YYYYMMDDHHMMSSSSS = "yyyyMMddHHmmssSSS";


    /**
     * 定时任务执行开始日志内容
     */
    public static final String TIMED_TASK_EXECUTION_START_CONTENT = "定时任务开始执行：";

    /**
     * 定时任务执行完成日志内容
     */
    public static final String TIMED_TASK_EXECUTION_COMPLETE_CONTENT = "定时任务执行完成！";

}
