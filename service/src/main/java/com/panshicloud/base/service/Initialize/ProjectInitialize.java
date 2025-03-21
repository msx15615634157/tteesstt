package com.panshicloud.base.service.Initialize;

import com.panshicloud.base.remote.dto.GroupDto;
import com.panshicloud.base.remote.service.ISystemConfigService;
import com.panshicloud.base.service.constants.CommonCst;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xingxingfa
 * @since 2025/1/20
 */
@Configuration
@Slf4j
public class ProjectInitialize implements CommandLineRunner {

    @DubboReference
    private ISystemConfigService systemConfigService;

    @Override
    public void run(String... args) {
        log.info("==============项目启动初始化==============");
        // 项目启动时清除系统参数缓存
        {
            List<GroupDto> groupDefine = systemConfigService.findGroupDefine();
            List<String> groupCodes = groupDefine.stream()
                    .map(GroupDto::getCode)
                    .collect(Collectors.toList());
            groupCodes.add(CommonCst.SYSTEM_CONFIG_DEFAULT_GROUP);
            systemConfigService.deleteRedisCache(groupCodes);
        }
    }
}
