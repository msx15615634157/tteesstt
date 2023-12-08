package com.panshicloud.base.dao.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xingxingfa
 * @since 2022/12/22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("psc_sys_config")
public class SystemConfig implements Serializable {

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 分组值
     */
    private String groupCode;

    /**
     * key值
     */
    private String code;

    /**
     * value值
     */
    private String value;

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
