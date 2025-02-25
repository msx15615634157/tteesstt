package com.panshicloud.base.dao.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * @author gxk
 * @version v0.1
 * @createTime 2024/8/26 18:24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("psc_dir")
public class Dir implements Serializable {

    /**
     * 目录编码
     */
    private String code;

    /**
     * 目录名称
     */
    private String name;

    /**
     * 目录类型，0-指标集目录，1-报表目录
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
