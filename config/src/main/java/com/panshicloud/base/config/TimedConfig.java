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
@ConfigurationProperties(prefix = "timed")
public class TimedConfig {

    /**
     * 定时任务分组信息
     */
    private List<GroupTimed> groupList;

}
