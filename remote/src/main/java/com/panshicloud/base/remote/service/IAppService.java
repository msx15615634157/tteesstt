package com.panshicloud.base.remote.service;

import com.panshicloud.base.remote.dto.AppDto;
import com.panshicloud.base.remote.dto.AppInsertDto;
import com.panshicloud.base.remote.dto.AppUpdateDto;

import java.util.List;

/**
 * <p>
 * 应用服务
 * </p>
 *
 * @author wanglibin
 */
public interface IAppService {

    /**
     * 新增
     *
     * @param dto
     */
    void insert(AppInsertDto dto);

    /**
     * 删除
     *
     * @param id
     */
    void delete(String id);

    /**
     * 查询
     *
     * @return
     */
    List<AppDto> findAll();

    /**
     * 获取
     *
     * @param id
     * @return
     */
    AppDto getById(String id);

    /**
     * 获取
     *
     * @param code
     * @return
     */
    AppDto getByCode(String code);

    /**
     * 更新
     *
     * @param dto 更新实体
     * @return
     */
    void update(AppUpdateDto dto);

}
