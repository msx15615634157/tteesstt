package com.panshicloud.base.remote.service;

import com.panshicloud.base.remote.dto.ServiceNodeDto;
import com.panshicloud.base.remote.dto.ServiceNodeFindAllDto;

import java.util.List;

/**
 * @author zhangze
 * @version V1.0
 * @date 2024年07月30日 17:26:20
 * @packageName com.panshicloud.fa.service.systemmanager
 * @className IOperationMonitoringService
 * @describe 系统管理-运行监控
 */
public interface IServiceNodeService {

    /**
     * 获取注册中心上的服务信息
     *
     * @return ServiceNodeDto 服务信息列表
     */
    List<ServiceNodeFindAllDto> findAll();

}
