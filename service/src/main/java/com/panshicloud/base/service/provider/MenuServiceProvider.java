package com.panshicloud.base.service.provider;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.panshicloud.base.dao.entity.Menu;
import com.panshicloud.base.dao.mapper.MenuMapper;
import com.panshicloud.base.remote.dto.*;
import com.panshicloud.base.remote.service.IMenuParameterService;
import com.panshicloud.base.remote.service.IMenuService;
import com.panshicloud.base.service.constants.ErrorCodeCst;
import com.panshicloud.common.constants.JpErrorCodeCst;
import com.panshicloud.common.helper.ConvertHelper;
import com.panshicloud.common.helper.ExceptionHelper;
import com.panshicloud.common.utils.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author luowenyao
 * @since 2022-04-21
 */
@DubboService
public class MenuServiceProvider extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Autowired
    private MenuMapper menuMapper;
    @DubboReference
    private IMenuParameterService menuParameterService;

    private String rootParentId = "root";


    @Override
    public List<MenuDto> findByAppId(String appId) {
        List<MenuDto> menuList = find(appId);
        return toTree(menuList, rootParentId);
    }

    @Override
    public List<MenuEnablePermissionDto> findEnablePermissionByAppId(String appId) {
        List<MenuDto> menus = find(appId);
        return menuParameterService.findEnablePermissions(menus);
    }

    @Override
    public void insert(MenuInsertDto insert) {
        {
            Menu exist = lambdaQuery().eq(Menu::getCode, insert.getCode()).eq(Menu::getAppId, insert.getAppId()).one();
            if (exist != null) {
                throw ExceptionHelper.newException(JpErrorCodeCst.SYSTEM_ERROR, "菜单编码已存在");
            }
        }
        {
            Menu exist = lambdaQuery().eq(Menu::getName, insert.getName()).eq(Menu::getAppId, insert.getAppId()).one();
            if (exist != null) {
                throw ExceptionHelper.newException(JpErrorCodeCst.SYSTEM_ERROR, "菜单名称已存在");
            }
        }
        if (StringUtils.isBlank(insert.getParentId())) {
            insert.setParentId(rootParentId);
        }
        Menu menu = ConvertHelper.tToV(insert, Menu.class);
        save(menu);
        // 保存菜单参数
        menuParameterService.update(menu.getId(), insert.getParam());
    }

    @Override
    public void update(MenuUpdateDto update) {
        Menu menu = menuMapper.selectById(update.getId());
        if (StringUtils.notEquals(menu.getName(), update.getName())) {
            Menu exist = lambdaQuery().eq(Menu::getName, update.getName()).eq(Menu::getAppId, update.getAppId()).one();
            if (exist != null) {
                throw ExceptionHelper.newException(ErrorCodeCst.APP_IS_EXIST, "菜单名称已存在");
            }
        }
        if (StringUtils.notEquals(menu.getCode(), update.getCode())) {
            Menu exist = lambdaQuery().eq(Menu::getCode, update.getCode()).eq(Menu::getAppId, update.getAppId()).one();
            if (exist != null) {
                throw ExceptionHelper.newException(ErrorCodeCst.APP_IS_EXIST, "菜单编码已存在");
            }
        }
        if (StringUtils.isBlank(update.getParentId())) {
            update.setParentId(rootParentId);
        }
        // 更新菜单
        updateById(ConvertHelper.tToV(update, Menu.class));
        // 更新菜单参数
        menuParameterService.update(menu.getId(), update.getParam());
    }

    @Override
    public void updateSort(String id, Integer sort, String parentId) {
        if (StringUtils.isBlank(parentId)) {
            parentId = rootParentId;
        }
        Menu current = getById(id);
        String currentParentId = current.getParentId();
        Integer currentSort = current.getSort();
        // 本分组拖拽 parentId目标父分组  currentParentId当前父分组
        if (parentId.equals(currentParentId)) {
            // 向上拖拽
            if (currentSort > sort) {
                lambdaUpdate()
                        .eq(Menu::getParentId, currentParentId)
                        .ge(Menu::getSort, sort)
                        .lt(Menu::getSort, currentSort)
                        .setSql("sort = sort + 1")
                        .update();
            }
            // 向下拖拽
            else {
                lambdaUpdate()
                        .eq(Menu::getParentId, currentParentId)
                        .gt(Menu::getSort, currentSort)
                        .le(Menu::getSort, sort)
                        .setSql("sort = sort - 1")
                        .update();
            }
        } else {
            // 非本分组拖拽
            lambdaUpdate()
                    .eq(Menu::getParentId, currentParentId)
                    .gt(Menu::getSort, currentSort)
                    .setSql("sort = sort - 1")
                    .update();
            lambdaUpdate()
                    .eq(Menu::getParentId, parentId)
                    .ge(Menu::getSort, sort)
                    .setSql("sort = sort + 1")
                    .update();
        }
        lambdaUpdate()
                .eq(Menu::getId, id)
                .set(Menu::getParentId, parentId)
                .set(Menu::getSort, sort)
                .update();
    }

    @Override
    public void delete(String id) {
        // 根据id删除菜单
        menuMapper.deleteById(id);
        // 根据菜单id删除菜单参数
        menuParameterService.deleteByMenuId(id);
    }

    @Override
    public MenuDto get(String id) {
        return ConvertHelper.tToV(super.getById(id), MenuDto.class);
    }

    @Override
    public MenuDto getByCode(String appId, String code) {
        return ConvertHelper.tToV(lambdaQuery().eq(Menu::getAppId, appId).eq(Menu::getCode, code).one(), MenuDto.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String copy(String menuId) {
        // 获取菜单信息
        MenuDto menuDto = get(menuId);
        if (menuDto == null) {
            throw new RuntimeException("未找到指定的菜单信息");
        }
        // 复制菜单代码
        String copySuffix = generateCopySuffix(menuDto);
        String newMenuCode = menuDto.getCode() + copySuffix;
        String newMenuName = menuDto.getName() + copySuffix;
        menuDto.setCode(newMenuCode);
        menuDto.setName(newMenuName);
        // 获取菜单参数列表
        List<MenuParameterDto> menuParameterDtoList = menuParameterService.findByMenuId(menuId);
        // 转换为插入对象
        MenuInsertDto menuInsertDto = ConvertHelper.tToV(menuDto, MenuInsertDto.class);
        menuInsertDto.setParam(menuParameterDtoList);
        menuInsertDto.setCurrentSort(menuDto.getSort());
        // 插入新菜单
        insert(menuInsertDto);
        return newMenuCode;
    }
    private String generateCopySuffix(MenuDto menuDto) {
        int count = lambdaQuery()
                .eq(Menu::getAppId, menuDto.getAppId())
                .like(Menu::getCode, menuDto.getCode() + "-copy%")
                .count() + 1;
        return numberToLetter(count);
    }

    private String numberToLetter(int number) {
        char[] letters = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        return "-copy" + letters[number - 1];
    }
    private List<MenuDto> toTree(List<MenuDto> list, String id) {
        List<MenuDto> result = new ArrayList<>();
        Iterator<MenuDto> item = list.iterator();
        while (item.hasNext()) {
            MenuDto next = item.next();
            if (StringUtils.equals(next.getParentId(), id)) {
                item.remove();
                result.add(next);
            }
        }
        result.forEach(it -> it.setChildren(toTree(list, it.getId())));
        return result;
    }

    /**
     * 通过appId查询菜单List
     *
     * @param appId appId
     * @return
     */
    private List<MenuDto> find(String appId) {
        //查询菜单，并排序
        LambdaQueryWrapper<Menu> query = new LambdaQueryWrapper<>();
        query.eq(Menu::getAppId, appId);
        query.orderByAsc(Menu::getSort);
        List<MenuDto> menuList = ConvertHelper.tToV(menuMapper.selectList(query), MenuDto.class);
        return menuList;
    }
}
