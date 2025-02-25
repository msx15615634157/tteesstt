package com.panshicloud.base.remote.service;

import java.sql.SQLException;
import java.util.List;

/**
 * @Author yantao
 * @since 2023/12/5
 **/
public interface IQueryIndexTempService {

    /**
     * 批量新增
     *
     * @param queryIdList 需要查询id集合
     * @param md5         mds值
     */
    void insertBatch(List<String> queryIdList, String md5);

    /**
     * 更新数据
     *
     * @param queryIdList 需要查询id集合
     * @return md5 mds值
     */
    String update(List<String> queryIdList);

    /**
     * 清空临时表
     */
    void truncateTmp();

    /**
     * 根据md5获取统计数
     *
     * @param md5 mds值
     * @return 统计数
     */
    Integer getCountByMd5(String md5);

}
