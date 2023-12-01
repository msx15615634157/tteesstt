package com.panshicloud.base.remote.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 应用实体
 * </p>
 *
 * @author wanglibin
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AppDto implements Serializable {

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

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建用户id
     */
    private String createUserId;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 更新用户id
     */
    private String updateUserId;

}
