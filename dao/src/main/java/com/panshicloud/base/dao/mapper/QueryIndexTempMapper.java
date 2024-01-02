package com.panshicloud.base.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.panshicloud.base.dao.entity.QueryIndexTemp;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author yantao
 * @since 2023-12-06
 */
@Repository
public interface QueryIndexTempMapper extends BaseMapper<QueryIndexTemp> {

    /**
     * 新增
     * @param queryId 查询id
     * @param md5
     */
    void insert(@Param("queryId") String queryId, @Param("md5") String md5);

    /**
     * 批量插入
     * @param queryIdList 查询id集合
     * @param md5 MD5
     */
    void insertBatch(@Param("queryIdList") List<String> queryIdList, @Param("md5") String md5);

    /**
     * 清空表
     */
    void truncateTmp();

}
