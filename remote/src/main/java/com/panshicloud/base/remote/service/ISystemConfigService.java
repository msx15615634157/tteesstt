package com.panshicloud.base.remote.service;

import com.panshicloud.base.remote.dto.*;

import java.util.List;

/**
 * @author xingxingfa
 * @since 2022/12/22
 */
public interface ISystemConfigService {

    /**
     * 按分组编码查
     *
     * @param groupCode 分组编码
     * @return
     */
    List<SystemConfigDto> findByGroupCode(String groupCode);

    /**
     * 按分组编码跟参数编码获取
     *
     * @param groupCode 分组编码
     * @param code      参数编码
     * @return
     */
    SystemConfigDto get(String groupCode, String code);

    /**
     * 按分组编码跟参数编码删除
     *
     * @param groupCode
     * @param code
     */
    void delete(String groupCode, String code);

    /**
     * 新增
     *
     * @param dto 新增实体
     */
    void insert(SystemConfigInsertDto dto);

    /**
     * 更新
     *
     * @param dto 更新实体
     */
    void update(SystemConfigUpdateDto dto);

    /**
     * 查询分组定义
     *
     * @return List<GroupDto>
     */
    List<GroupDto> findGroupDefine();

    /**
     * 获取分组参数
     *
     * @param groupCode 分组编码
     * @return GroupParameterDto
     */
    GroupParameterDto getGroupParameter(String groupCode);

    /**
     * 更新
     *
     * @param updates 更新dto
     */
    void update(List<SystemConfigUpdateDto> updates);
}
