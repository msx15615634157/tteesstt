package com.panshicloud.base.dao.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * @author luowenyao
 * @since 2022-04-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("psc_menu")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 物理主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 编码
     */
    private String code;

    /**
     * 所属应用
     */
    private String appId;

    /**
     * 菜单类型
     *
     * 0:普通, 1:超链接, 2:预留
     */
    private Integer type;

    /**
     * 父级菜单
     */
    private String parentId;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 权重值
     */
    private Integer sort;

    /**
     * 路由名称
     */
    private String routerName;

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
