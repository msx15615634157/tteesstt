package com.panshicloud.base.service.provider;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.panshicloud.base.config.ParameterConfig;
import com.panshicloud.base.dao.entity.SystemConfig;
import com.panshicloud.base.dao.mapper.SystemConfigMapper;
import com.panshicloud.base.parameter.AbstractGroup;
import com.panshicloud.base.parameter.AbstractParameter;
import com.panshicloud.base.parameter.AbstractTab;
import com.panshicloud.base.remote.dto.*;
import com.panshicloud.base.remote.service.ISystemConfigService;
import com.panshicloud.base.service.constants.CommonCst;
import com.panshicloud.base.service.constants.ErrorCodeCst;
import com.panshicloud.base.service.constants.RedisKeyCst;
import com.panshicloud.base.service.parameter.DefaultParameter;
import com.panshicloud.base.service.provider.remote.SystemConfigRemote;
import com.panshicloud.common.constants.JpErrorCodeCst;
import com.panshicloud.common.helper.ConvertHelper;
import com.panshicloud.common.helper.ExceptionHelper;
import com.panshicloud.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.rpc.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author xingxingfa
 * @since 2022/12/22
 */
@DubboService
@Slf4j
public class SystemConfigServiceProvider extends ServiceImpl<SystemConfigMapper, SystemConfig> implements ISystemConfigService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SystemConfigMapper systemConfigMapper;

    @Autowired
    private ParameterConfig parameterConfig;

    @Autowired
    private SystemConfigRemote systemConfigRemote;

    @Autowired
    @Lazy
    private DefaultParameter defaultParameter;

    @Override
    public List<SystemConfigDto> findByGroupCode(String groupCode) {
        if (StringUtils.isBlank(groupCode)) {
            groupCode = CommonCst.SYSTEM_CONFIG_DEFAULT_GROUP;
        }
        // 加rides缓存
        BoundValueOperations operations = redisTemplate.boundValueOps(RedisKeyCst.SYSTEM_CONFIG_PARAMETER + groupCode);
        // 判断缓存是不是空
        if (operations.get() != null) {
            return (List<SystemConfigDto>) operations.get();
        }
        AbstractGroup abstractGroup = getGroup(groupCode);
        List<SystemConfigDto> rst = findByGroupCode(abstractGroup);
        // 往redis塞缓存数据
        operations.set(rst);
        return rst;
    }

    @Override
    public void deleteRedisCache(List<String> groupCodes) {
        redisTemplate.delete(
                groupCodes
                        .stream()
                        .map(it -> RedisKeyCst.SYSTEM_CONFIG_PARAMETER + it)
                        .collect(Collectors.toList())
        );
    }

    /**
     * 获取参数并且初始化
     *
     * @param abstractGroup 抽象分组
     * @return List<SystemConfigDto>
     */
    private List<SystemConfigDto> findByGroupCode(AbstractGroup abstractGroup) {
        List<SystemConfigDto> rst = new ArrayList<>();
        List<SystemConfig> systemConfigs = lambdaQuery()
                .eq(SystemConfig::getGroupCode, abstractGroup.code())
                .orderByAsc(SystemConfig::getCreateTime)
                .list();
        // 遍历抽象分组参数对象
        for (AbstractTab tab : abstractGroup.tabs()) {
            // 遍历当前页签
            for (AbstractParameter parameter : tab.parameters()) {
                SystemConfigDto systemConfig = new SystemConfigDto();
                // 筛选配置集，从中拿到配置对象
                Optional<SystemConfig> optional = systemConfigs.stream()
                        .filter(it -> StringUtils.equals(it.getCode(), parameter.code()))
                        .findAny();
                systemConfig.setGroupCode(abstractGroup.code());
                systemConfig.setCode(parameter.code());
                // 如果配置对象存在，则向参数中set值
                if (optional.isPresent()) {
                    systemConfig.setValue(format(optional.get().getValue(), parameter.dataType()));
                    rst.add(systemConfig);
                    continue;
                }
                // 新增
                SystemConfigInsertDto systemConfigInsertDto = new SystemConfigInsertDto();
                systemConfigInsertDto.setCode(parameter.code());
                systemConfigInsertDto.setGroupCode(abstractGroup.code());
                systemConfigInsertDto.setValue(parameter.getValue());
                this.insert(systemConfigInsertDto);
                systemConfig.setValue(parameter.getValue());
                rst.add(systemConfig);
            }
        }
        return rst;
    }

    @Override
    public SystemConfigDto get(String groupCode, String code) {
        SystemConfig systemConfig = lambdaQuery()
                .eq(SystemConfig::getGroupCode, groupCode)
                .eq(SystemConfig::getCode, code)
                .one();
        return ConvertHelper.tToV(systemConfig, SystemConfigDto.class);
    }

    @Override
    public void delete(String groupCode, String code) {
        lambdaUpdate()
                .eq(SystemConfig::getGroupCode, groupCode)
                .eq(SystemConfig::getCode, code)
                .remove();
    }

    @Override
    public void insert(SystemConfigInsertDto dto) {
        SystemConfigDto exist = this.get(dto.getGroupCode(), dto.getCode());
        if (exist != null) {
            throw ExceptionHelper.newException(ErrorCodeCst.SYSTEM_CONFIG_IS_EXIST, "系统配置已存在");
        }
        systemConfigMapper.insert(ConvertHelper.tToV(dto, SystemConfig.class));
    }

    @Override
    public void update(SystemConfigUpdateDto dto) {
        SystemConfig old = systemConfigMapper.selectById(dto.getId());
        if (old == null) {
            throw ExceptionHelper.newException(ErrorCodeCst.SYSTEM_CONFIG_IS_NOT_EXIST, "系统配置不存在");
        }
        // 分组已改变或编码已改变
        if (StringUtils.notEquals(old.getCode(), dto.getCode()) || StringUtils.notEquals(old.getGroupCode(), dto.getGroupCode())) {
            SystemConfigDto exist = this.get(dto.getGroupCode(), dto.getCode());
            if (exist != null) {
                throw ExceptionHelper.newException(ErrorCodeCst.SYSTEM_CONFIG_IS_EXIST, "系统配置已存在");
            }
        }
        systemConfigMapper.updateById(ConvertHelper.tToV(dto, SystemConfig.class));
    }

    @Override
    public List<GroupDto> findGroupDefine() {
        return ConvertHelper.tToV(parameterConfig.getGroupList(), GroupDto.class);
    }

    @Override
    public GroupParameterDto getGroupParameter(String groupCode) {
        if (StringUtils.isBlank(groupCode)) {
            groupCode = CommonCst.SYSTEM_CONFIG_DEFAULT_GROUP;
        }
        AbstractGroup abstractGroup = getGroup(groupCode);
        // 查询配置集
        List<SystemConfigDto> configList = this.findByGroupCode(abstractGroup);
        // 构建结果集分组对象
        GroupParameterDto group = new GroupParameterDto();
        // 结果集分组对象页签集
        List<GroupParameterDto.Tab> tabs = new ArrayList<>();
        // 遍历抽象分组参数对象
        for (AbstractTab abstractTab : abstractGroup.tabs()) {
            // 创建当前分组页签
            GroupParameterDto.Tab groupTab = new GroupParameterDto.Tab();
            // 创建当前分组页签参数集
            List<GroupParameterDto.Tab.Parameter> parameters = new ArrayList<>();
            // 遍历当前页签
            for (AbstractParameter parameter : abstractTab.parameters()) {
                Object value = parameter.value();
                // 筛选配置集，从中拿到配置对象
                Optional<SystemConfigDto> optional = configList.stream()
                        .filter(it -> StringUtils.equals(it.getCode(), parameter.code()))
                        .findAny();
                // 如果配置对象存在，则想参数中set值
                if (optional.isPresent()) {
                    value = optional.get().getValue();
                }
                // 格式化数据
                value = format(value, parameter.dataType());
                GroupParameterDto.Tab.Parameter tabParameter = ConvertHelper.tToV(parameter, GroupParameterDto.Tab.Parameter.class);
                tabParameter.setValue(value);
                tabParameter.setCode(parameter.code());
                tabParameter.setName(parameter.name());
                tabParameter.setOptions(parameter.options());
                tabParameter.setName(parameter.name());
                tabParameter.setDescription(parameter.description());
                tabParameter.setType(parameter.type());
                // 构造Parameter，添加到parameters中
                parameters.add(tabParameter);
            }
            // 添加到groupTab中
            groupTab.setName(abstractTab.name());
            groupTab.setParameters(parameters);
            tabs.add(groupTab);
        }
        // 添加到group中
        group.setCode(abstractGroup.code());
        group.setTabs(tabs);
        group.setName(abstractGroup.name());
        return group;
    }

    /**
     * 获取抽象分组
     *
     * @param groupCode 分组编码
     * @return AbstractGroup
     */
    public AbstractGroup getGroup(String groupCode) {
        if (StringUtils.equals(groupCode, CommonCst.SYSTEM_CONFIG_DEFAULT_GROUP)) {
            return defaultParameter;
        }
        List<GroupDto> groupDtos = ConvertHelper.tToV(parameterConfig.getGroupList(), GroupDto.class);
        for (GroupDto groupDto : groupDtos) {
            if (StringUtils.equals(groupDto.getCode(), groupCode)) {
                String rst;
                // 获取一个参数
                GenericService genericService;
                try {
                    genericService = systemConfigRemote.getGenericService(groupDto.getInterfaceName(), groupDto.getCode());
                } catch (Exception e) {
                    log.info("detailMessage:" + e.getMessage());
                    throw ExceptionHelper.newException(JpErrorCodeCst.SYSTEM_ERROR, groupDto.getName() + "服务未上线");
                }
                rst = (String) genericService.$invoke("getParameter", new String[]{"java.lang.String"}, new String[]{groupDto.getCode()});
                if (StringUtils.isBlank(rst)) {
                    throw ExceptionHelper.newException(JpErrorCodeCst.SYSTEM_ERROR, "参数分组定义为空");
                }
                // 构建抽象分组参数对象
                return AbstractGroup.build(rst);
            }
        }
        throw ExceptionHelper.newException(JpErrorCodeCst.SYSTEM_ERROR, "未查询到参数");
    }


    @Override
    public void update(List<SystemConfigUpdateDto> updates) {
        for (SystemConfigUpdateDto update : updates) {
            SystemConfigDto systemConfigDto = this.get(update.getGroupCode(), update.getCode());
            if (systemConfigDto == null) {
                continue;
            }
            update.setId(systemConfigDto.getId());
        }
        this.saveOrUpdateBatch(ConvertHelper.tToV(updates, SystemConfig.class));
    }

    /**
     * 仅用于格式化参数value，因为参数value值库中存varchar，都可以转String
     */
    private static Object format(Object obj, Integer dataType) {
        String str = formatStr(obj);
        switch (dataType) {
            case 0:
                return str;
            case 1:
                return Integer.parseInt(str);
            case 2:
                return Float.parseFloat(str);
            case 3:
                return Double.parseDouble(str);
            default:
        }
        throw ExceptionHelper.newException(JpErrorCodeCst.SYSTEM_ERROR, "参数类型不存在");
    }

    public static String formatStr(Object value) {
        if (value == null) {
            return "";
        }
        if (value instanceof String) {
            return (String) value;
        }
        return String.valueOf(value);
    }
}
