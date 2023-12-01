package com.panshicloud.base.service.provider;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.panshicloud.base.dao.entity.SystemConfig;
import com.panshicloud.base.dao.mapper.SystemConfigMapper;
import com.panshicloud.base.remote.dto.SystemConfigDto;
import com.panshicloud.base.remote.dto.SystemConfigInsertDto;
import com.panshicloud.base.remote.dto.SystemConfigInsertOrUpdateDto;
import com.panshicloud.base.remote.dto.SystemConfigUpdateDto;
import com.panshicloud.base.remote.service.ISystemConfigDefaultService;
import com.panshicloud.base.remote.service.ISystemConfigService;
import com.panshicloud.base.service.constants.CommonCst;
import com.panshicloud.common.constants.JpErrorCodeCst;
import com.panshicloud.common.helper.ConvertHelper;
import com.panshicloud.common.helper.ExceptionHelper;
import com.panshicloud.common.utils.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.List;

/**
 * @author xingxingfa
 * @since 2022/12/22
 */
@DubboService
public class SystemConfigDefaultServiceProvider extends ServiceImpl<SystemConfigMapper, SystemConfig> implements ISystemConfigDefaultService {

    @DubboReference
    private ISystemConfigService systemConfigService;

    @Override
    public List<SystemConfigDto> findAll() {
        return systemConfigService.findByGroupCode(CommonCst.SYSTEM_CONFIG_DEFAULT_GROUP);
    }

    @Override
    public SystemConfigDto get(String code) {
        return systemConfigService.get(CommonCst.SYSTEM_CONFIG_DEFAULT_GROUP, code);
    }


    @Override
    public void delete(String code) {
        systemConfigService.delete(CommonCst.SYSTEM_CONFIG_DEFAULT_GROUP, code);
    }

    @Override
    public void insertOrUpdate(SystemConfigInsertOrUpdateDto dto) {
        String id = dto.getId();
        String code = dto.getCode();
        if (StringUtils.isBlank(code)) {
            throw ExceptionHelper.newException(JpErrorCodeCst.SYSTEM_ERROR, "系统配置编码不能为空");
        }
        // 判断id是不是空，id为空就是新增，否则是修改
        if (StringUtils.isBlank(id)) {
            SystemConfigInsertDto insert = ConvertHelper.tToV(dto, SystemConfigInsertDto.class);
            insert.setGroupCode(CommonCst.SYSTEM_CONFIG_DEFAULT_GROUP);
            systemConfigService.insert(insert);
        } else {
            SystemConfigUpdateDto update = ConvertHelper.tToV(dto, SystemConfigUpdateDto.class);
            update.setGroupCode(CommonCst.SYSTEM_CONFIG_DEFAULT_GROUP);
            systemConfigService.update(update);
        }
    }

}
