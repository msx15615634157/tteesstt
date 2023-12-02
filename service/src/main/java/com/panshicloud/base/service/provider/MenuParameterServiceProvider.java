package com.panshicloud.base.service.provider;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.panshicloud.base.dao.entity.MenuParameter;
import com.panshicloud.base.dao.mapper.MenuParameterMapper;
import com.panshicloud.base.remote.dto.BtnParameterDto;
import com.panshicloud.base.remote.dto.MenuDto;
import com.panshicloud.base.remote.dto.MenuEnablePermissionDto;
import com.panshicloud.base.remote.dto.MenuParameterDto;
import com.panshicloud.base.remote.service.IMenuParameterService;
import com.panshicloud.common.helper.ConvertHelper;
import com.panshicloud.common.utils.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜单参数服务
 * </p>
 *
 * @author yantao
 */

@DubboService
public class MenuParameterServiceProvider extends ServiceImpl<MenuParameterMapper, MenuParameter> implements IMenuParameterService {

    @Override
    public void deleteByMenuId(String menuId) {
        lambdaUpdate().eq(MenuParameter::getMenuId, menuId).remove();
    }

    @Override
    public List<MenuParameterDto> findByMenuId(String menuId) {
        List<MenuParameter> find = lambdaQuery().eq(MenuParameter::getMenuId, menuId).list();
        List<MenuParameterDto> rst = new ArrayList<>();
        for (MenuParameter item : find) {
            MenuParameterDto parameter = new MenuParameterDto();
            parameter.setCode(item.getCode());
            parameter.setIsTab(item.getIsTab());
            // 转换为原来的格式
            parameter.setValue(StringUtils.isBlank(item.getValue()) ? "" : StringUtils.toObject(item.getValue()));
            rst.add(parameter);
        }
        return rst;
    }

    @Override
    public List<MenuEnablePermissionDto> findEnablePermissions(List<MenuDto> menus) {
        List<MenuEnablePermissionDto> rst = new ArrayList<>();
        // 获取菜单id集合
        List<String> menuIds = menus.stream().map(MenuDto::getId).collect(Collectors.toList());
        // 获取全部的菜单参数
        List<MenuParameter> allMenuParameter = lambdaQuery().in(MenuParameter::getMenuId, menuIds).list();
        // 按菜单id分组
        Map<String, List<MenuParameter>> groupMenu = allMenuParameter.stream().sorted(Comparator.comparing(MenuParameter::getMenuId)).collect(Collectors.groupingBy(MenuParameter::getMenuId));
        for (String menuId : groupMenu.keySet()) {
            Optional<MenuDto> optional = menus.stream().filter(it -> it.getId().equals(menuId)).findAny();
            if (!optional.isPresent()){
                continue;
            }
            MenuEnablePermissionDto menuEnablePermission = ConvertHelper.tToV(optional.get(), MenuEnablePermissionDto.class);
            List<MenuParameterDto> menuParameterResults = new ArrayList<>();
            List<MenuParameter> menuParameters = groupMenu.get(menuId);
            for (MenuParameter menuParameter : menuParameters) {
                MenuParameterDto parameter = new MenuParameterDto();
                parameter.setCode(menuParameter.getCode());
                parameter.setIsTab(menuParameter.getIsTab());
                // 转换为原来的格式
                parameter.setValue(StringUtils.isBlank(menuParameter.getValue()) ? "" : StringUtils.toObject(menuParameter.getValue()));
                // 包含按钮权限的
                if (menuParameter.getIsTab() != null && "true".equalsIgnoreCase(menuParameter.getIsTab())) {
                    List<BtnParameterDto> btnParameterList = JSONObject.parseArray((String) parameter.getValue(), BtnParameterDto.class);
                    List<BtnParameterDto> btnParameters = btnParameterList.stream().filter(it -> it.getIsPermission() != null && it.getIsPermission()).collect(Collectors.toList());
                    if (btnParameters.size() > 0) {
                        parameter.setValue(btnParameters);
                        menuParameterResults.add(parameter);
                    }
                }
            }
            if (menuParameterResults.size() != 0) {
                menuEnablePermission.setMenuParameters(menuParameterResults);
                rst.add(menuEnablePermission);
            }
        }
        return rst;
    }

    @Override
    public void update(String menuId, List<MenuParameterDto> parameters) {
        List<MenuParameter> updates = new ArrayList<>();
        for (MenuParameterDto parameter : parameters) {
            MenuParameter update = new MenuParameter();
            update.setMenuId(menuId);
            update.setCode(parameter.getCode());
            update.setIsTab(parameter.getIsTab());
            // 转换String，并保留类型
            update.setValue(parameter.getValue() == null ? "" : StringUtils.toStr(parameter.getValue()));
            updates.add(update);
        }
        // 先删除
        deleteByMenuId(menuId);
        // 后新增
        saveBatch(updates);
    }

}
