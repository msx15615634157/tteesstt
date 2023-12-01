package com.panshicloud.base.service.parameter;

import com.panshicloud.base.parameter.AbstractGroup;
import com.panshicloud.base.parameter.AbstractTab;
import com.panshicloud.base.remote.dto.SystemConfigDto;
import com.panshicloud.base.remote.dto.SystemConfigInsertDto;
import com.panshicloud.base.remote.service.ISystemConfigService;
import com.panshicloud.base.service.parameter.def.DefaultTab;
import com.panshicloud.base.service.parameter.def.IsUse;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 报表参数
 * </>
 *
 * @author xuwenqiang
 * @sinse 2023/5/8
 */
@Component
public class DefaultParameter extends AbstractGroup {

    @DubboReference
    private ISystemConfigService systemConfigService;

    @Override
    public String code() {
        return "default";
    }

    @Override
    public String name() {
        return "系统参数";
    }

    @Override
    public SystemConfigDto getModel(String code) {
        SystemConfigDto systemConfig = systemConfigService.get(code(), code);
        if (systemConfig == null){
            SystemConfigInsertDto systemConfigInsertDto = new SystemConfigInsertDto();
            systemConfigInsertDto.setGroupCode(code());
            systemConfigInsertDto.setCode(code);
            systemConfigInsertDto.setValue(new IsUse().value());
            systemConfigService.insert(systemConfigInsertDto);
            systemConfig = systemConfigService.get(code(), code);
        }
        return systemConfig;
    }

    @Override
    public List<AbstractTab> tabs() {
        //处理分组下的页签
        List<AbstractTab> rst = new ArrayList();
        rst.add(new DefaultTab());
        return rst;
    }



}
