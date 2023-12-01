package com.panshicloud.base.remote.service;

import com.panshicloud.base.remote.dto.SystemConfigDto;
import com.panshicloud.base.remote.dto.SystemConfigInsertOrUpdateDto;

import java.util.List;

/**
 * @author xingxingfa
 * @since 2022/12/22
 */
public interface ISystemConfigDefaultService {

    /**
     * 查询所有键值对
     *
     * @return
     */
    List<SystemConfigDto> findAll();

    /**
     * 按照key获取指定的键值对
     *
     * @param code 键值
     * @return
     */
    SystemConfigDto get(String code);

    /**
     * 根据键值删除键值对
     *
     * @param code 键值
     */
    void delete(String code);

    /**
     * 更新或新增键值对
     *
     * @param systemConfig 更新或新增对象
     */
    void insertOrUpdate(SystemConfigInsertOrUpdateDto systemConfig);

}
