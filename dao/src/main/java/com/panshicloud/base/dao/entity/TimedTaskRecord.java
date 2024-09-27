package com.panshicloud.base.dao.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 *
 * @author shimengmeng
 * @since 2024/4/30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("PSC_TIMED_TASK_RECORD")
public class TimedTaskRecord {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
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
     * 结果(成功/失败)
     */
    private String status;

    /**
     * 任务ID
     */
    private String taskId;

    /**
     * 定时任务流程标识
     */
    private String operationTimedCode;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 开始时间
     */
    @TableField(exist = false)
    private Date startTime;

    /**
     * 结束时间
     */
    @TableField(exist = false)
    private Date endTime;
}
