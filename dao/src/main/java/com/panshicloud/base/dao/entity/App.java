package com.panshicloud.base.dao.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 应用实体类
 * </p>
 *
 * @author wanglibin
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("psc_application")
public class App implements Serializable {

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
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
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 创建用户id
     */
    @TableField(fill = FieldFill.INSERT)
    private String createUserId;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 更新用户id
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateUserId;

}
