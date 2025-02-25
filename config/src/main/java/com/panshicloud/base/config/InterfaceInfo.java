package com.panshicloud.base.config;

import lombok.Data;

/**
 * @author zhangze
 * @version V1.0
 * @date 2024年05月20日 09:54:01
 * @packageName com.panshicloud.base.config
 * @className InterfaceInfo
 * @describe 接口
 */
@Data
public class InterfaceInfo {

    /**
     * 接口/方法名
     */
    private String name;

    /**
     * 接口描述
     */
    private String description;
}
