package com.panshicloud.base.remote.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 应用新增Dto
 * </p>
 *
 * @author wanglibin
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class AppInsertDto implements Serializable {

    /**
     * 应用编码
     */
    private String code;

    /**
     * 应用名称
     */
    private String name;

    /**
     * 应用描述
     */
    private String description;

    /**
     * url地址
     */
    private String url;

}
