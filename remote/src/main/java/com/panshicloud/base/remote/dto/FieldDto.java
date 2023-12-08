package com.panshicloud.base.remote.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 字段属性Dto
 * </p>
 *
 * @author yantao
 */
@Data
public class FieldDto implements Serializable {

    /**
     * 表字段
     */
    private String field;

    /**
     * 表字段注释
     */
    private String comments;

}
