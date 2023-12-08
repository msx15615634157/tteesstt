package com.panshicloud.base.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author xuwenqiang
 */
@Data
@Component
@RefreshScope
@ConfigurationProperties(prefix = "parameter")
public class ParameterConfig {

    private List<Group> groupList;

}
