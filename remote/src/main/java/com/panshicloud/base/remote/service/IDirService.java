package com.panshicloud.base.remote.service;

import com.panshicloud.base.remote.dto.DirDto;
import com.panshicloud.base.remote.dto.DirInsertDto;
import com.panshicloud.base.remote.dto.DirTreeDto;
import com.panshicloud.base.remote.dto.DirUpdateDto;

import java.util.List;

/**
 * @author gxk
 * @version v0.1
 * @createTime 2024/8/27 11:18
 */
public interface IDirService {

    /**
     * 新增目录
     * @param dir 目录数据
     */
    void insert(DirInsertDto dir);

    /**
     * 修改目录
     * @param dir 目录数据
     */
    void update(DirUpdateDto dir);

    /**
     * 删除指定目录，会删除关联下级
     * @param type 目录类型
     * @param code 目录编码
     */
    void delete(String type, String code);

    /**
     * 获取目录
     * @param type 目录类型
     * @param code 目录编码
     * @return 目录数据
     */
    DirDto get(String type, String code);

    /**
     * 获取目录
     * @param type 目录类型
     * @return 目录数据
     */
    List<DirDto> find(String type);

    /**
     * 获取目录树
     * @param type 目录类型
     * @return 目录树
     */
    List<DirTreeDto> findToTree(String type);

}
