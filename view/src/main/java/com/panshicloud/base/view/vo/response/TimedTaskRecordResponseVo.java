package com.panshicloud.base.view.vo.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 定时任务响应类
 *
 * @author shimengmeng
 * @since 2024/4/29
 */
@ApiModel
@Data
@EqualsAndHashCode(callSuper = false)
public class TimedTaskRecordResponseVo implements Serializable {

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date startTime;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date endTime;
}
