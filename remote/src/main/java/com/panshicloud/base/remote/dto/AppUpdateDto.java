package com.panshicloud.base.remote.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 应用更新Dto
 * </p>
 *
 * @author wanglibin
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AppUpdateDto implements Serializable {

    /**
     * id
     */
    private String id;

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
