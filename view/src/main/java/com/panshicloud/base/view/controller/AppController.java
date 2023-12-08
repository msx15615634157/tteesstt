package com.panshicloud.base.view.controller;

import com.panshicloud.base.remote.dto.AppInsertDto;
import com.panshicloud.base.remote.dto.AppUpdateDto;
import com.panshicloud.base.remote.service.IAppService;
import com.panshicloud.base.view.vo.request.AppCodeRequestVo;
import com.panshicloud.base.view.vo.request.AppInsertRequestVo;
import com.panshicloud.base.view.vo.request.AppUpdateRequestVo;
import com.panshicloud.base.view.vo.response.AppResponseVo;
import com.panshicloud.common.base.request.IdRequestVo;
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

/**
 * @author wanglibin
 * @since 2022-05-17
 */
@Api(tags = "应用相关API")
@RestController
@RequestMapping("/app")
public class AppController {

    @DubboReference
    private IAppService appService;

    @ApiOperation("通过code查应用")
    @PostMapping("/getByCode")
    public GenericResponseVo<AppResponseVo> findByCode(@RequestBody @Validated AppCodeRequestVo vo){
        return new GenericResponseVo<>(ConvertHelper.tToV(appService.getByCode(vo.getCode()),AppResponseVo.class));
    }

    @ApiOperation(value = "查询所有")
    @PostMapping("findAll")
    public GenericResponseVo<List<AppResponseVo>> findAll() {
        return new GenericResponseVo<>(ConvertHelper.tToV(appService.findAll(), AppResponseVo.class));
    }

    @ApiOperation(value = "新增")
    @PostMapping("insert")
    public SuccessResponseVo insert(@RequestBody @Validated AppInsertRequestVo vo) {
        appService.insert(ConvertHelper.tToV(vo, AppInsertDto.class));
        return ResponseHelper.SUCCESS;
    }

    @ApiOperation(value = "修改")
    @PostMapping("update")
    public SuccessResponseVo update(@RequestBody @Validated AppUpdateRequestVo vo) {
        appService.update(ConvertHelper.tToV(vo, AppUpdateDto.class));
        return ResponseHelper.SUCCESS;
    }

    @ApiOperation(value = "删除")
    @PostMapping("delete")
    public SuccessResponseVo update(@RequestBody @Validated IdRequestVo vo) {
        appService.delete(vo.getId());
        return ResponseHelper.SUCCESS;
    }

}
