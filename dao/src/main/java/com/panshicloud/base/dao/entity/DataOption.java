package com.panshicloud.base.dao.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 字典表
 * </p>
 *
 * @author wanglibin
 * @since 2021-09-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("jp_data_option")
public class DataOption implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 类别/领域
     */
    private String domain;

    /**
     * 字典编码
     */
    private String code;

    /**
     * 字典名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 上级编码
     */
    private String parentCode;

    /**
     * 状态，0：弃用，1：使用
     */
    private Integer status;

}
