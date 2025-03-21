package com.panshicloud.base.view.controller;

import com.panshicloud.base.remote.constants.CommonCst;
import com.panshicloud.base.remote.dto.GroupDto;
import com.panshicloud.base.remote.dto.GroupParameterDto;
import com.panshicloud.base.remote.service.ISystemConfigService;
import com.panshicloud.base.view.vo.request.GroupCodeRequestVo;
import com.panshicloud.base.view.vo.response.GroupDefineResponseVo;
import com.panshicloud.base.view.vo.response.GroupParameterResponseVo;
import com.panshicloud.base.view.vo.response.SystemConfigResponseVo;
import com.panshicloud.common.base.response.GenericResponseVo;
import com.panshicloud.common.base.response.SuccessResponseVo;
import com.panshicloud.common.helper.ConvertHelper;
import com.panshicloud.common.helper.ResponseHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 参数相关API
 * </>
 *
 * @author xuwenqiang
 * @sinse 2023/5/5
 */
@Api(tags = "参数相关API")
@RestController
@RequestMapping("/parameter")
public class ParameterController {

    @DubboReference
    private ISystemConfigService systemConfigService;

    @ApiOperation("获取分组参数")
    @PostMapping("/getGroup")
    public GenericResponseVo<GroupParameterResponseVo> getGroup(@RequestBody @Validated GroupCodeRequestVo vo) {
        GroupParameterDto groupParameterDto = systemConfigService.getGroupParameter(vo.getCode());
        return new GenericResponseVo<>(ConvertHelper.tToV(groupParameterDto, GroupParameterResponseVo.class));
    }

    @ApiOperation("获取分组定义")
    @PostMapping("/findGroupDefine")
    public GenericResponseVo<List<GroupDefineResponseVo>> findGroupDefine() {
        List<GroupDto> group = systemConfigService.findGroupDefine();
        return new GenericResponseVo(ConvertHelper.tToV(group, GroupDto.class));
    }

    @ApiOperation("获取分组参数")
    @PostMapping("/find")
    public GenericResponseVo<List<SystemConfigResponseVo>> find(@RequestBody @Validated GroupCodeRequestVo vo) {
        return new GenericResponseVo<>(ConvertHelper.tToV(systemConfigService.findByGroupCode(vo.getCode()), SystemConfigResponseVo.class));
    }

    @ApiOperation("清除系统参数缓存")
    @PostMapping("/deleteCache")
    public SuccessResponseVo deleteCache() {
        List<GroupDto> groupDefine = systemConfigService.findGroupDefine();
        List<String> groupCodes = groupDefine.stream()
                .map(GroupDto::getCode)
                .collect(Collectors.toList());
        groupCodes.add(CommonCst.SYSTEM_CONFIG_DEFAULT_GROUP);
        systemConfigService.deleteRedisCache(groupCodes);
        return ResponseHelper.SUCCESS;
    }

}