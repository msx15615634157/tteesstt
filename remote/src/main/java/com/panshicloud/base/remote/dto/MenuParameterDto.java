package com.panshicloud.base.remote.dto;


import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author luowenyao
 * @since 2022-04-21
 */
@Data
public class MenuParameterDto implements Serializable {

    /**
     * 参数编码
     */
    private String code;

    /**
     * 参数名称
     */
    private String name;

    /**
     * 参数值
     */
    private Object value;

    /**
     * 是否有按钮权限
     */
    private String isTab;

    /**
     * 子集元素
     */
    private List<BtnParameterDto> children;
}
