package com.panshicloud.base.view.vo.response;

import com.panshicloud.base.remote.dto.ServiceNodeDto;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * @author zhangze
 * @version V1.0
 * @date 2024年11月04日 13:42:29
 * @packageName com.panshicloud.base.view.vo.response
 * @className ServiceNodeResponseVo
 * @describe 服务节点信息返回对象
 */
@ApiModel
@Data
public class ServiceNodeFindAllResponseVo {

    /**
     * 服务名称
     */
    private String serviceName;

    /**
     * 服务节点集合
     */
    List<ServiceNodeDto> serviceNodes;
}
