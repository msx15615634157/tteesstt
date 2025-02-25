package com.panshicloud.base.config;

import lombok.Data;

import java.io.Serializable;

/**
 * 分组dto
 */
@Data
public class GroupTimed implements Serializable {

    private String code;

    private String name;

    private String interfaceName;
}
