package com.panshicloud.base.remote.dto.timed;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhangze
 * @version V1.0
 * @date 2024年05月21日 11:04:42
 * @packageName com.panshicloud.base.remote.dto
 * @className GroupTimedParameterDto
 * @describe 定时任务分组-接口信息
 */
@Data
public class GroupTimedInterfaceInfoDto implements Serializable {

    /**
     * 方法名（中文-返给前端）
     */
    private String methodName;

    /**
     * 方法
     */
    private String methodCode;

}
