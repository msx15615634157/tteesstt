package com.panshicloud.base.remote.service;

import com.panshicloud.base.remote.dto.timed.TimedTaskRecordDto;
import com.panshicloud.common.base.PageDto;

import java.util.Date;

/**
 * <p>
 * 定时任务日志
 * </p>
 *
 * @author shimengmeng
 * @since 2024/4/30
 */
public interface ITimedTaskRecordService {

    /**
     * 分页查询日志信息
     *
     * @param taskId     任务ID
     * @param startTime  开始时间
     * @param endTime    结束时间
     * @param status     状态
     * @param pageNumber 页数
     * @param pageSize   条数
     * @return
     */
    PageDto<TimedTaskRecordDto> findByTask(String taskId, Date startTime, Date endTime, String status, Integer pageNumber, Integer pageSize);

    /**
     * 新增日志记录
     *
     * @param operationTimedCode 定时任务流程标识
     * @param content            内容
     * @param status             状态（true成功，false失败）
     */
    void insert(String operationTimedCode, String content, Boolean status);

    /**
     * 新增日志记录
     *
     * @param operateUserName    执行用户
     * @param content            日志内容
     * @param status             结果（成功/失败）
     * @param taskId             任务ID
     * @param operationTimedCode 定时任务流程标识
     */
    void insert(String operateUserName, String content, Boolean status, String taskId, String operationTimedCode);

    /**
     * 任务执行完成
     *
     * @param operationTimedCode 流程标识
     * @param unlock             是否解锁 true解锁，false不解锁
     */
    void timedTaskComplete(String operationTimedCode, Boolean unlock);

    /**
     * 任务执行完成
     *
     * @param operationTimedCode 流程标识
     */
    void timedTaskComplete(String operationTimedCode);

    /**
     * 通过定时任务流程标识修改当前流程下执行用户
     *
     * @param taskId             定时任务ID
     * @param operationTimedCode 定时任务流程标识
     * @param operateUserName    执行用户
     */
    void updateOperateUserName(String taskId, String operationTimedCode, String operateUserName);

    /**
     * 通过定时任务ID删除日志
     *
     * @param taskId 任务ID
     */
    void delete(String taskId);
}
