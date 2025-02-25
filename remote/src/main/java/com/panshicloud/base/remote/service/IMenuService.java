package com.panshicloud.base.remote.service;

import com.panshicloud.base.remote.dto.MenuDto;
import com.panshicloud.base.remote.dto.MenuEnablePermissionDto;
import com.panshicloud.base.remote.dto.MenuInsertDto;
import com.panshicloud.base.remote.dto.MenuUpdateDto;

import java.util.List;


/**
 * @author wanglibin
 * @since 2022-04-21
 */
public interface IMenuService {

    /**
     * 获取所有菜单
     *
     * @param appId 所属应用
     * @return List
     */
    List<MenuDto> findByAppId(String appId);


    /**
     * 获取开启权限的所有菜单
     *
     * @param appId 所属应用
     * @return List
     */
    List<MenuEnablePermissionDto> findEnablePermissionByAppId(String appId);

    /**
     * 薪增菜单
     *
     * @param insert 新增
     */
    void insert(MenuInsertDto insert);

    /**
     * 更新菜单
     *
     * @param update 更新
     */
    void update(MenuUpdateDto update);

    /**
     * 更新排序
     *
     * @param id
     * @param sort
     * @param parentId
     */
    void updateSort(String id, Integer sort, String parentId);

    /**
     * 删除菜单
     *
     * @param id 主键
     */
    void delete(String id);

    /**
     * 根据id查菜单
     *
     * @param id 主键
     * @return MenuDto
     */
    MenuDto get(String id);

    /**
     * 根据菜单code查菜单
     *
     * @param code  菜单编码
     * @param appId 应用id
     * @return MenuDto
     */
    MenuDto getByCode(String appId, String code);

    /**
     * 复制菜单
     *
     * @param menuId 菜单id
     */
    String copy(String menuId);

}
