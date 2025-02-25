package com.panshicloud.base.remote.dto;

import lombok.Data;

/**
 * @author zhangze
 * @version V1.0
 * @date 2024年11月04日 13:42:29
 * @packageName com.panshicloud.base.view.vo.response
 * @className ServiceNodeResponseVo
 * @describe 服务节点信息返回对象
 */
@Data
public class ServiceNodeDto {

    /**
     * 节点ID
     */
    private String id;

    /**
     * 服务名称
     */
    private String serviceName;

    /**
     * ip
     */
    private String ip;

    /**
     * 端口
     */
    private Integer port;

    /**
     * 健康状态 true健康 false不健康
     */
    private Boolean healthy;
}
