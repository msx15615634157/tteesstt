package com.panshicloud.base.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * zhangze
 */
@Data
@Component
@RefreshScope
@ConfigurationProperties(prefix = "service-node")
public class ServiceNodeConfig {

    /**
     * 服务节点名称
     */
    private List<String> names;

}
