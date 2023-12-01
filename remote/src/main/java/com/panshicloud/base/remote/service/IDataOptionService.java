package com.panshicloud.base.remote.service;

import com.panshicloud.base.remote.dto.DataOptionCountDto;
import com.panshicloud.base.remote.dto.DataOptionDto;
import com.panshicloud.base.remote.dto.DataOptionInsertDto;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 字典表 服务类
 * </p>
 *
 * @author wanglibin
 * @since 2021-09-09
 */
public interface IDataOptionService {

    /**
     * 新增
     *
     * @param domain      领域
     * @param code        字典编码
     * @param name        字典名称
     * @param description 描述
     */
    void insert(String domain, String code, String name, String description);

    /**
     * 批量保存
     *
     * @param list 列表
     */
    void insertBatch(List<DataOptionInsertDto> list);

    /**
     * 删除
     *
     * @param domain 领域
     * @param code   字典编码
     */
    void delete(String domain, String code);

    /**
     * 查询
     *
     * @param domain 领域
     * @param name   名称
     * @return List
     */
    List<DataOptionDto> findByDomain(String domain, String name);

    /**
     * 查询
     *
     * @param domain 领域
     * @return List
     */
    List<DataOptionDto> findByDomain(String domain);

    /**
     * 按code查询
     *
     * @param domain 领域
     * @param codes
     * @return List
     */
    List<DataOptionDto> findByDomainAndCodes(String domain, List<String> codes);

    /**
     * 查询并转为map
     *
     * @param domain 领域
     * @return Map
     */
    Map<String, String> findByDomainToMap(String domain);

    /**
     * 更新
     *
     * @param domain      领域
     * @param code        字典编码
     * @param name        字典名称
     * @param description 描述
     */
    void update(String domain, String code, String name, String description);

    /**
     * 更新状态
     *
     * @param domain 领域
     * @param code   字典编码
     * @param status 状态
     */
    void updateStatus(String domain, String code, Integer status);

    /**
     * 按domain统计
     *
     * @return
     */
    List<DataOptionCountDto> countByDomain();

    /**
     * 查询并转换为树视图
     *
     * @param domain 领域
     * @param name   名称
     * @return 树视图
     */
    List<DataOptionDto> findByDomainToTree(String domain, String name);

}
