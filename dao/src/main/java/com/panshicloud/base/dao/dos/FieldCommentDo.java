package com.panshicloud.base.dao.dos;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 字段属性类
 * </p>
 *
 * @author yantao
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class FieldCommentDo {

    /**
     * 表字段
     */
    private String field;

    /**
     * 表字段注释
     */
    private String comments;
}
