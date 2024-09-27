package com.panshicloud.base.remote.dto.timed;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 日志对象
 */
@Data
public class TimedTaskRecordDto implements Serializable {

    /**
     * 主键
     */
    private String id;

    /**
     * 执行用户(账号：默认用户、指定用户)
     */
    private String operateUserName;

    /**
     * 执行时长
     */
    private String costTime;

    /**
     * 日志内容
     */
    private String content;

    /**
     * 结果(成功/失败/进行中)
     */
    private String status;

    /**
     * 任务ID
     */
    private String taskId;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;
}
