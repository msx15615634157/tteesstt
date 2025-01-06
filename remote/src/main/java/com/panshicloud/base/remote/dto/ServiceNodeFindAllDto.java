package com.panshicloud.base.remote.dto;

import lombok.Data;

import java.util.List;

/**
 * @author zhangze
 * @version V1.0
 * @date 2024年11月05日 16:48:39
 * @packageName com.panshicloud.base.remote.dto
 * @className ServiceNodeFindAllDto
 * @describe 查询所有服务节点
 */
@Data
public class ServiceNodeFindAllDto {

    /**
     * 服务名称
     */
    private String serviceName;

    /**
     * 服务节点集合
     */
    List<ServiceNodeDto> serviceNodes;
}
