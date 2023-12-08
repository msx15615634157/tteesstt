package com.panshicloud.base.remote.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author xingxingfa
 * @since 2022/12/22
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SystemConfigUpdateDto implements Serializable {

    /**
     * id
     */
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

}
