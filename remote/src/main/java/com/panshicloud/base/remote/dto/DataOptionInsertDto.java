package com.panshicloud.base.remote.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 枚举新增Dto
 * </p>
 *
 * @author huangrankun
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DataOptionInsertDto implements Serializable {

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
     * 状态，0：弃用，1：使用
     */
    private Integer status;

    /**
     * 上级编码
     */
    private String parentCode;

}
