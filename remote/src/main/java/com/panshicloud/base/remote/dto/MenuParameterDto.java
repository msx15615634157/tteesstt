package com.panshicloud.base.remote.dto;


import lombok.*;

import java.io.Serializable;
import java.util.Map;

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
     * 参数值
     */
    private Object value;

}
