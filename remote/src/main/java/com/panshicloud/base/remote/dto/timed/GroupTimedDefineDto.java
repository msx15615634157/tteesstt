package com.panshicloud.base.remote.dto.timed;

import lombok.Data;

import java.io.Serializable;

/**
 * 定时任务分组定义
 */
@Data
public class GroupTimedDefineDto implements Serializable {

    /**
     * 服务标识
     */
    private String code;

    /**+
     * 服务名
     */
    private String name;
}
