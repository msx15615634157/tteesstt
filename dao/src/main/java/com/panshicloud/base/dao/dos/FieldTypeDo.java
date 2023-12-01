package com.panshicloud.base.dao.dos;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 字段类型
 * </p>
 *
 * @author yantao
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class FieldTypeDo {

    /**
     * 表字段名称
     */
    private String fieldName;

    /**
     * 表字段注释
     */
    private String type;
}
