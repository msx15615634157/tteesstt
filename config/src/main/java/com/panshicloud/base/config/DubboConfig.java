package com.panshicloud.base.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @author xuwenqiang
 */
@Data
@Component
@RefreshScope
public class DubboConfig {

    @Value("${dubbo.registry.address}")
    private String registryAddress;

    @Value("${dubbo.registry.parameters.namespace}")
    private String namespace;

}
