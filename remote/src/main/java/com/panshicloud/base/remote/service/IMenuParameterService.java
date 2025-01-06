package com.panshicloud.base.remote.service;


import com.panshicloud.base.remote.dto.MenuDto;
import com.panshicloud.base.remote.dto.MenuEnablePermissionDto;
import com.panshicloud.base.remote.dto.MenuParameterDto;

import java.util.List;

/**
 * <p>
 * 菜单参数服务
 * </p>
 *
 * @author yantao
 */
public interface IMenuParameterService {

    /**
     * 按菜单id删除
     *
     * @param menuId 菜单id
     */
    void deleteByMenuId(String menuId);

    /**
     * 按菜单id查找
     *
     * @param menuId 菜单id
     * @return List
     */
    List<MenuParameterDto> findByMenuId(String menuId);

    /**
     * 获取开启权限的按钮
     *
     * @param menus 菜单集合
     * @return List<MenuEnablePermissionDto>
     */
    //List<MenuEnablePermissionDto> findEnablePermissions(List<MenuDto> menus);

    /**
     * 获取开启权限的按钮（新）
     *
     * @param menus 菜单集合
     * @return List<MenuEnablePermissionDto>
     */
    List<MenuEnablePermissionDto> findEnablePermissions(List<MenuDto> menus);

    /**
     * 更新接口
     *
     * @param menuId     菜单id
     * @param parameters 更新参数
     */
    void update(String menuId, List<MenuParameterDto> parameters);

}
