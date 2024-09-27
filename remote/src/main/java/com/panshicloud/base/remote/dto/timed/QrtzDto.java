package com.panshicloud.base.remote.dto.timed;

import lombok.Data;

import java.io.Serializable;

/**
 * 调用QRTZ工具类dto
 */
@Data
public class QrtzDto implements Serializable {
    /**
     * 任务id
     */
    private String id;
    
    /**
     * 任务名称
     */
    private String jobName;
    
    /**
     * 任务执行类
     */
    private String jobClass;
    
    /**
     * 组名
     */
    private String groupName;
    
    /**
     * 任务 参数信息
     */
    private String jobParam;
    
    /**
     * 任务状态 启动还是暂停
     */
    private Integer status;
    
    /**
     * 任务运行时间表达式
     */
    private String cronExpression;

    /**
     * 执行用户
     */
    private String operateUserName;

    /**
     * 任务ID
     */
    private String taskId;

    /**
     * 服务标识
     */
    private String serviceCode;

    /**
     * 方法标识
     */
    private String methodCode;

    /**
     * 要调用的具体类路径
     */
    private String methodClassPath;

    /**
     * 要调用的具体方法名
     */
    private String methodName;
}