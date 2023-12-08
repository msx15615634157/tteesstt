package com.panshicloud.base.service.provider.remote;

import com.panshicloud.base.config.DubboConfig;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.service.GenericService;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 参数服务提供
 * </>
 *
 * @author xuwenqiang
 * @date 2023/6/15
 */
@Component
public class SystemConfigRemote {

    private final DubboConfig dubboConfig;

    public SystemConfigRemote(DubboConfig dubboConfig) {
        this.dubboConfig = dubboConfig;
    }

    /**
     * 获得泛化接口
     *
     * @param serverName 服务名称
     * @return GenericService
     */
    public GenericService getGenericService(String serverName, Class<?> clazz) {
        ApplicationConfig application = new ApplicationConfig();
        ReferenceConfig<GenericService> reference = new ReferenceConfig<>();
        application.setName(serverName);
        // 弱类型接口名
        reference.setInterface(clazz);
        RegistryConfig registry = new RegistryConfig();
        registry.setAddress(dubboConfig.getRegistryAddress());
        // 注册
        application.setRegistry(registry);
        // 声明为泛化接口
        reference.setGeneric("true");
        reference.setGroup(serverName);
        reference.setVersion("1.0");
        reference.setApplication(application);
        return reference.get();
    }

    /**
     * 获得泛化接口
     *
     * @return GenericService
     */
    public GenericService getGenericService(String interfaceName, String name) {
        ApplicationConfig application = new ApplicationConfig();
        application.setName(name);
        ReferenceConfig<GenericService> reference = new ReferenceConfig<>();
        // 弱类型接口名
        reference.setInterface(interfaceName);
        RegistryConfig registry = new RegistryConfig();
        registry.setAddress(dubboConfig.getRegistryAddress());
        // 设置命名空间
        Map<String, String> map = new HashMap<>(16);
        map.put("namespace", dubboConfig.getNamespace());
        registry.setParameters(map);
        application.setParameters(map);
        // 注册
        application.setRegistry(registry);
        // 声明为泛化接口
        reference.setGeneric("true");
        reference.setApplication(application);
        return reference.get();
    }

}
