package com.panshicloud.base.view.controller;

import com.panshicloud.base.operationlog.OperationLog;
import com.panshicloud.base.remote.dto.SystemConfigDto;
import com.panshicloud.base.remote.dto.SystemConfigInsertOrUpdateDto;
import com.panshicloud.base.remote.dto.SystemConfigUpdateDto;
import com.panshicloud.base.remote.service.ISystemConfigDefaultService;
import com.panshicloud.base.remote.service.ISystemConfigService;
import com.panshicloud.base.view.vo.request.SystemConfigInsertOrUpdateRequestVo;
import com.panshicloud.base.view.vo.request.SystemParameterUpdateRequestVo;
import com.panshicloud.base.view.vo.response.SystemConfigResponseVo;
import com.panshicloud.common.base.request.CodeRequestVo;
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

import java.util.ArrayList;
import java.util.List;

/**
 * @author xingxingfa
 * @since 2022/12/22
 */
@Api(tags = "系统参数")
@RestController
@RequestMapping("/systemConfig")
public class SystemConfigController {

    @DubboReference
    private ISystemConfigDefaultService systemConfigDefaultService;

    @DubboReference
    private ISystemConfigService systemConfigService;

    @ApiOperation("新增或更新")
    @PostMapping("/insertOrUpdate")
    public SuccessResponseVo insertOrUpdate(@RequestBody @Validated SystemConfigInsertOrUpdateRequestVo vo) {
        systemConfigDefaultService.insertOrUpdate(ConvertHelper.tToV(vo, SystemConfigInsertOrUpdateDto.class));
        return ResponseHelper.SUCCESS;
    }

    @ApiOperation("获取全部")
    @PostMapping("/findAll")
    public GenericResponseVo<List<SystemConfigResponseVo>> findAll() {
        List<SystemConfigDto> list = systemConfigDefaultService.findAll();
        return new GenericResponseVo<>(ConvertHelper.tToV(list, SystemConfigResponseVo.class));
    }

    @ApiOperation("根据code获取配置")
    @PostMapping("/getByCode")
    public GenericResponseVo<SystemConfigResponseVo> getByCode(@RequestBody @Validated CodeRequestVo vo) {
        SystemConfigDto find = systemConfigDefaultService.get(vo.getCode());
        return new GenericResponseVo<>(ConvertHelper.tToV(find, SystemConfigResponseVo.class));
    }

    @ApiOperation("根据code删除配置")
    @PostMapping("/deleteByCode")
    public SuccessResponseVo deleteByCode(@RequestBody @Validated CodeRequestVo vo) {
        systemConfigDefaultService.delete(vo.getCode());
        return ResponseHelper.SUCCESS;
    }

    @ApiOperation("更新")
    @PostMapping("/batchUpdate")
    @OperationLog("'分组编码=' + #vo.groupCode+ '，参数=' + #vo.parameters")
    public SuccessResponseVo update(@RequestBody @Validated SystemParameterUpdateRequestVo vo) {
        List<SystemConfigUpdateDto> systemConfigUpdates = new ArrayList<>();
        List<SystemParameterUpdateRequestVo.SystemParameter> parameters = vo.getParameters();
        for (SystemParameterUpdateRequestVo.SystemParameter parameter : parameters) {
            SystemConfigUpdateDto systemConfigUpdate = new SystemConfigUpdateDto();
            systemConfigUpdate.setValue(parameter.getValue());
            systemConfigUpdate.setCode(parameter.getCode());
            systemConfigUpdate.setGroupCode(vo.getGroupCode());
            systemConfigUpdates.add(systemConfigUpdate);
        }
        systemConfigService.update(systemConfigUpdates);
        return ResponseHelper.SUCCESS;
    }

}
