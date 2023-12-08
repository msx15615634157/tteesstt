package com.panshicloud.base.service.provider;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.panshicloud.base.dao.entity.App;
import com.panshicloud.base.dao.mapper.AppMapper;
import com.panshicloud.base.remote.dto.AppDto;
import com.panshicloud.base.remote.dto.AppInsertDto;
import com.panshicloud.base.remote.dto.AppUpdateDto;
import com.panshicloud.base.remote.service.IAppService;
import com.panshicloud.base.service.constants.CommonCst;
import com.panshicloud.base.service.constants.ErrorCodeCst;
import com.panshicloud.common.helper.ConvertHelper;
import com.panshicloud.common.helper.ExceptionHelper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author wanglibin
 * @Date Created in 2022/5/17 15:20
 * @Description AppServiceProvider.java
 * @Version 1.0
 */
@DubboService
public class AppServiceProvider implements IAppService {

    @Autowired
    private AppMapper appMapper;

    @Override
    public void insert(AppInsertDto dto) {
        AppDto exist = getByCode(dto.getCode());
        if (exist != null) {
            throw ExceptionHelper.newException(ErrorCodeCst.APP_IS_EXIST, "应用编码已存在");
        }
        appMapper.insert(ConvertHelper.tToV(dto, App.class));
    }

    @Override
    public void delete(String id) {
        appMapper.deleteById(id);
    }

    @Override
    public List<AppDto> findAll() {
        LambdaQueryWrapper<App> query = new LambdaQueryWrapper<>();
        return ConvertHelper.tToV(appMapper.selectList(query), AppDto.class);
    }

    @Override
    public AppDto getById(String id) {
        return ConvertHelper.tToV(appMapper.selectById(id), AppDto.class);
    }

    @Override
    public AppDto getByCode(String code) {
        LambdaQueryWrapper<App> query = new LambdaQueryWrapper<>();
        query.eq(App::getCode, code);
        return ConvertHelper.tToV(appMapper.selectOne(query), AppDto.class);
    }

    @Override
    public void update(AppUpdateDto dto) {
        LambdaQueryWrapper<App> query = new LambdaQueryWrapper<>();
        query.ne(App::getId, dto.getId());
        query.eq(App::getCode, dto.getCode());
        App exist = appMapper.selectOne(query);
        if (exist != null) {
            throw ExceptionHelper.newException(ErrorCodeCst.APP_IS_EXIST, "应用编码已存在");
        }
        appMapper.updateById(ConvertHelper.tToV(dto, App.class));
    }

}
