package com.panshicloud.base.view.controller;

import com.panshicloud.base.remote.service.IServiceNodeService;
import com.panshicloud.base.view.vo.response.ServiceNodeFindAllResponseVo;
import com.panshicloud.common.base.response.GenericResponseVo;
import com.panshicloud.common.helper.ConvertHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zhangze
 * @version V1.0
 * @date 2024年07月30日 17:21:34
 * @packageName com.panshicloud.fa.controller.systemmanager
 * @className SystemManagerRadarController
 * @describe 系统管理-雷达
 */
@Api(tags = "服务节点管理")
@RestController
@Slf4j
@RequestMapping("/serviceNode")
public class ServiceNodeController {

    @DubboReference
    private IServiceNodeService systemManagerRadarService;

    @ApiOperation("获取所有节点信息")
    @PostMapping("findAll")
    public GenericResponseVo<List<ServiceNodeFindAllResponseVo>> findAll() {
        return new GenericResponseVo<>(ConvertHelper.tToV(systemManagerRadarService.findAll(), ServiceNodeFindAllResponseVo.class));
    }

}
