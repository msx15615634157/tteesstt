package com.panshicloud.base.service.provider;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.panshicloud.base.dao.entity.MenuParameter;
import com.panshicloud.base.dao.mapper.MenuParameterMapper;
import com.panshicloud.base.remote.dto.MenuParameterDto;
import com.panshicloud.base.remote.service.IMenuParameterService;
import com.panshicloud.common.utils.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.ArrayList;
import java.util.List;

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
            // 转换为原来的格式
            parameter.setValue(StringUtils.isBlank(item.getValue()) ? "" : StringUtils.toObject(item.getValue()));
            rst.add(parameter);
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
            // 转换String，并保留类型
            update.setValue( parameter.getValue() == null ? "":StringUtils.toStr(parameter.getValue()));
            updates.add(update);
        }
        // 先删除
        deleteByMenuId(menuId);
        // 后新增
        saveBatch(updates);
    }

}
