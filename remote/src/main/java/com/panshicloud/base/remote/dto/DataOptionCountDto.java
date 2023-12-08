package com.panshicloud.base.remote.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 枚举统计Do
 * </p>
 *
 * @author huangrankun
 */

@Data
public class DataOptionCountDto implements Serializable {

    /**
     * 领域
     */
    private String domain;

    /**
     * 统计数
     */
    private Integer number;

}
