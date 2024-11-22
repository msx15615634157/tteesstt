package com.panshicloud.base.view.controller;

import com.panshicloud.base.operationlog.OperationLog;
import com.panshicloud.base.remote.dto.AppDto;
import com.panshicloud.base.remote.dto.MenuDto;
import com.panshicloud.base.remote.dto.MenuInsertDto;
import com.panshicloud.base.remote.dto.MenuUpdateDto;
import com.panshicloud.base.remote.service.IAppService;
import com.panshicloud.base.remote.service.IMenuParameterService;
import com.panshicloud.base.remote.service.IMenuService;
import com.panshicloud.base.view.vo.request.*;
import com.panshicloud.base.view.vo.response.MenuAndAppResponseVo;
import com.panshicloud.base.view.vo.response.MenuEnablePermissionResponseVo;
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

import java.util.ArrayList;
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

    @ApiOperation("通过全部应用查询菜单")
    @PostMapping("/findByAllAppId")
    public GenericResponseVo<List<MenuAndAppResponseVo>> findByAllAppId() {
        List<AppDto> appList = appService.findAll();
        List<MenuAndAppResponseVo> rst = new ArrayList<>();
        for (AppDto app : appList) {
            MenuAndAppResponseVo menuAndAppResponseVo = ConvertHelper.tToV(app, MenuAndAppResponseVo.class);
            List<MenuResponseVo> menuResponseVos = ConvertHelper.tToV(menuService.findByAppId(app.getId()), MenuResponseVo.class);
            menuAndAppResponseVo.setMenuList(menuResponseVos);
            rst.add(menuAndAppResponseVo);
        }
        return new GenericResponseVo<>(rst);
    }

    @ApiOperation("通过所属应用查询菜单")
    @PostMapping("/findEnablePermissionByAppId")
    public GenericResponseVo<List<MenuEnablePermissionResponseVo>> findEnablePermissionByAppId(@RequestBody @Validated MenuFindRequestVo vo) {
        return new GenericResponseVo(ConvertHelper.tToV(menuService.findEnablePermissionByAppId(vo.getAppId()), MenuEnablePermissionResponseVo.class));
    }

    @ApiOperation("新增菜单")
    @PostMapping("/insert")
    @OperationLog(type = "菜单-新增", operation = "新增菜单", value = "'应用Id='+#vo.appId+'菜单编码='+#vo.code+'菜单名称='+#vo.name+'菜单类型='+#vo.type+'父级菜单id='+#vo.parentId+'路由名称='+#vo.routerName+'菜单参数='+#vo.param")
    public SuccessResponseVo insert(@RequestBody @Validated MenuInsetRequestVo vo) {
        menuService.insert(ConvertHelper.tToV(vo, MenuInsertDto.class));
        return ResponseHelper.SUCCESS;
    }

    @ApiOperation("修改菜单")
    @PostMapping("/update")
    @OperationLog(type = "菜单-更新", operation = "修改菜单", value = "'应用Id='+#vo.appId+'菜单编码='+#vo.code+'菜单名称='+#vo.name+'菜单类型='+#vo.type+'父级菜单id='+#vo.parentId+'路由名称='+#vo.routerName+'菜单参数='+#vo.param")
    public SuccessResponseVo update(@RequestBody @Validated MenuUpdateRequestVo vo) {
        menuService.update(ConvertHelper.tToV(vo, MenuUpdateDto.class));
        return ResponseHelper.SUCCESS;
    }

    @ApiOperation("更新排序")
    @PostMapping("/updateSort")
    @OperationLog(type = "菜单-更新排序", operation = "修改菜单排序", value = "'菜单id='+#vo.id+'排序='+#vo.sort")
    public SuccessResponseVo updateSort(@RequestBody @Validated MenuUpdateSortRequestVo vo) {
        menuService.updateSort(vo.getId(), vo.getSort(), vo.getParentId());
        return ResponseHelper.SUCCESS;
    }

    @ApiOperation("删除")
    @PostMapping("/delete")
    @OperationLog(type = "菜单-删除", operation = "删除菜单", value = "'菜单id='+#vo.id")
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

//    暂不使用
//    @ApiOperation("查询菜单参数")
//    @PostMapping("/findEnablePermissions")
//    public GenericResponseVo<List<MenuParameterResponseVo>> findEnablePermissions(@RequestBody @Validated IdRequestVo vo) {
//        return new GenericResponseVo<>(ConvertHelper.tToV(menuParameterService.findEnablePermissions(vo.getId()), MenuParameterResponseVo.class));
//    }

    @ApiOperation("根据菜单编码查询菜单参数")
    @PostMapping("/findParametersByCode")
    public GenericResponseVo<List<MenuParameterResponseVo>> findParametersByCode(@RequestBody @Validated MenuGetRequestVo vo) {
        AppDto app = appService.getByCode(vo.getAppCode());
        if (app == null) {
            throw ExceptionHelper.newException(JpErrorCodeCst.SYSTEM_ERROR, "应用不存在");
        }
        MenuDto menu = menuService.getByCode(app.getId(), vo.getCode());
        if (menu == null) {
            throw ExceptionHelper.newException(JpErrorCodeCst.SYSTEM_ERROR, "菜单不存在");
        }
        return new GenericResponseVo<>(ConvertHelper.tToV(menuParameterService.findByMenuId(menu.getId()), MenuParameterResponseVo.class));
    }

}
