package com.panshicloud.base.remote.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author gxk
 * @version v0.1
 * @createTime 2024/8/27 10:11
 */
@Data
public class DirDto implements Serializable {

    /**
     * 目录编码
     */
    private String code;

    /**
     * 目录名称
     */
    private String name;

    /**
     * 目录类型
     */
    private Integer type;

    /**
     * 上级编码
     */
    private String parentCode;

    /**
     * 排序
     */
    private Integer sort;

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
