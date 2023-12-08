package com.panshicloud.base.dao.dos;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 生成表字段Do
 * </p>
 *
 * @author wanglibin
 */
@Data
public class GenerateTableFieldDo implements Serializable {

    /**
     * 字段名
     */
    private String name;

    /**
     * 字段类型
     */
    private Integer type;

    /**
     * 字段长度
     */
    private Integer length;

    /**
     * 是否允许为空
     */
    private Integer isAllowNull;

    /**
     * 是否是主键
     */
    private Integer isPrimaryKey;

    /**
     * 是否自增
     */
    private Integer isAutoIncrement;

    /**
     * 备注
     */
    private String comment;

}
