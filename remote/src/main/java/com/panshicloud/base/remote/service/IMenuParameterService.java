package com.panshicloud.base.remote.service;


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
     * 更新接口
     *
     * @param menuId     菜单id
     * @param parameters 更新参数
     */
    void update(String menuId, List<MenuParameterDto> parameters);

}
