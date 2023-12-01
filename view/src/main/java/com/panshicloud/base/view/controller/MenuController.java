package com.panshicloud.base.view.controller;

import com.panshicloud.base.remote.dto.AppDto;
import com.panshicloud.base.remote.dto.MenuInsertDto;
import com.panshicloud.base.remote.dto.MenuUpdateDto;
import com.panshicloud.base.remote.service.IAppService;
import com.panshicloud.base.remote.service.IMenuParameterService;
import com.panshicloud.base.remote.service.IMenuService;
import com.panshicloud.base.view.vo.request.*;
import com.panshicloud.base.view.vo.response.MenuParameterResponseVo;
import com.panshicloud.base.view.vo.response.MenuResponseVo;
import com.panshicloud.common.base.request.IdRequestVo;
import com.panshicloud.common.base.response.GenericResponseVo;
import com.panshicloud.common.base.response.SuccessResponseVo;
import com.panshicloud.common.constants.JpErrorCodeCst;
import com.panshicloud.common.helper.ConvertHelper;
import com.panshicloud.common.helper.ExceptionHelper;
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
 * @author luowenyao
 * @since 2022-04-21
 */
@Api(tags = "菜单相关API")
@RestController
@RequestMapping("/menu")
public class MenuController {

    @DubboReference
    private IAppService appService;
    @DubboReference
    private IMenuService menuService;
    @DubboReference
    private IMenuParameterService menuParameterService;

    @ApiOperation("通过所属应用查询菜单")
    @PostMapping("/findByAppId")
    public GenericResponseVo<List<MenuResponseVo>> findByAppId(@RequestBody @Validated MenuFindRequestVo vo) {
        return new GenericResponseVo<>(ConvertHelper.tToV(menuService.findByAppId(vo.getAppId()), MenuResponseVo.class));
    }

    @ApiOperation("新增菜单")
    @PostMapping("/insert")
    public SuccessResponseVo insert(@RequestBody @Validated MenuInsetRequestVo vo) {
        menuService.insert(ConvertHelper.tToV(vo, MenuInsertDto.class));
        return ResponseHelper.SUCCESS;
    }

    @ApiOperation("修改菜单")
    @PostMapping("/update")
    public SuccessResponseVo update(@RequestBody @Validated MenuUpdateRequestVo vo) {
        menuService.update(ConvertHelper.tToV(vo, MenuUpdateDto.class));
        return ResponseHelper.SUCCESS;
    }

    @ApiOperation("更新排序")
    @PostMapping("/updateSort")
    public SuccessResponseVo updateSort(@RequestBody @Validated MenuUpdateSortRequestVo vo) {
        menuService.updateSort(vo.getId(), vo.getSort(), vo.getParentId());
        return ResponseHelper.SUCCESS;
    }

    @ApiOperation("删除")
    @PostMapping("/delete")
    public SuccessResponseVo batchDelete(@RequestBody @Validated IdRequestVo vo) {
        menuService.delete(vo.getId());
        return ResponseHelper.SUCCESS;
    }

    @ApiOperation("根据id查菜单")
    @PostMapping("/get")
    public GenericResponseVo<MenuResponseVo> get(@RequestBody @Validated IdRequestVo vo) {
        return new GenericResponseVo<>(ConvertHelper.tToV(menuService.get(vo.getId()), MenuResponseVo.class));
    }

    @ApiOperation("根据id查菜单")
    @PostMapping("/getByCode")
    public GenericResponseVo<MenuResponseVo> getByCode(@RequestBody @Validated MenuGetRequestVo vo) {
        AppDto app = appService.getByCode(vo.getAppCode());
        if (app == null) {
            throw ExceptionHelper.newException(JpErrorCodeCst.SYSTEM_ERROR, "应用不存在");
        }
        return new GenericResponseVo<>(ConvertHelper.tToV(menuService.getByCode(app.getId(), vo.getCode()), MenuResponseVo.class));
    }

    @ApiOperation("查询菜单参数")
    @PostMapping("/findParameters")
    public GenericResponseVo<List<MenuParameterResponseVo>> findParameters(@RequestBody @Validated IdRequestVo vo) {
        return new GenericResponseVo<>(ConvertHelper.tToV(menuParameterService.findByMenuId(vo.getId()), MenuParameterResponseVo.class));
    }

}
