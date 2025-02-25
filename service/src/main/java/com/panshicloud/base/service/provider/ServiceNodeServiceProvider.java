package com.panshicloud.base.service.provider;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.client.naming.NacosNamingService;
import com.panshicloud.base.config.ServiceNodeConfig;
import com.panshicloud.base.remote.dto.ServiceNodeDto;
import com.panshicloud.base.remote.dto.ServiceNodeFindAllDto;
import com.panshicloud.base.remote.service.IServiceNodeService;
import com.panshicloud.common.constants.JpErrorCodeCst;
import com.panshicloud.common.helper.ExceptionHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author zhangze
 * @version V1.0
 * @date 2024年07月30日 17:26:49
 * @packageName com.panshicloud.fa.service.systemmanager.impl
 * @className OperationMonitoringServiceImpl
 * @describe 系统管理-运行监控
 */
@Slf4j
@DubboService
public class ServiceNodeServiceProvider implements IServiceNodeService {

    @Autowired
    private ServiceNodeConfig serviceNodeConfig;

    private final NacosNamingService nacosNamingService;

    public ServiceNodeServiceProvider(NacosNamingService nacosNamingService) {
        this.nacosNamingService = nacosNamingService;
    }

    @Override
    public List<ServiceNodeFindAllDto> findAll() {
        ArrayList<ServiceNodeFindAllDto> serviceNodeRst = new ArrayList<>();
        serviceNodeConfig.getNames().forEach(serviceNode -> {
            ServiceNodeFindAllDto serviceNodeFindAllDto = new ServiceNodeFindAllDto();
            serviceNodeFindAllDto.setServiceName(serviceNode);
            List<Instance> allInstances = null;
            try {
                allInstances = nacosNamingService.getAllInstances(serviceNode);
            } catch (NacosException e) {
                log.error("查询nacos服务节点出现错误：" + e.getMessage());
                throw ExceptionHelper.newException(JpErrorCodeCst.SYSTEM_ERROR, "查询nacos服务节点出现错误！");
            }
            ArrayList<ServiceNodeDto> serviceNodeDtos = new ArrayList<>();
            allInstances.forEach(allInstance -> {
                allInstance.setInstanceId(allInstance.getIp() + "#" + allInstance.getPort() + "#" + allInstance.getServiceName());
                ServiceNodeDto serviceNodeDto = new ServiceNodeDto();
                try {
                    serviceNodeDto.setId(URLEncoder.encode(allInstance.getInstanceId(), "UTF-8"));
                } catch (Exception e) {
                    log.error("nacos服务节点IP编码失败：" + e.getMessage());
                    throw ExceptionHelper.newException(JpErrorCodeCst.SYSTEM_ERROR, "nacos服务节点IP编码失败！");
                }
                serviceNodeDto.setServiceName(serviceNode);
                serviceNodeDto.setIp(allInstance.getIp());
                serviceNodeDto.setPort(allInstance.getPort());
                serviceNodeDto.setHealthy(allInstance.isHealthy());
                serviceNodeDtos.add(serviceNodeDto);
            });
            if (serviceNodeDtos.isEmpty()) {
                return;
            }
            serviceNodeFindAllDto.setServiceNodes(serviceNodeDtos);
            serviceNodeRst.add(serviceNodeFindAllDto);
        });
        return serviceNodeRst;
    }

}

