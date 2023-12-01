package com.panshicloud.base.remote.service;

import com.panshicloud.base.remote.dto.QueryDto;
import com.panshicloud.base.remote.dto.UpdateDto;

/**
 * <p>
 * 自定义sql服务
 * </p>
 *
 * @author xingxingfa
 * @since: 2022/11/17
 */
public interface ISqlExecutionService {
    /**
     * 查询语句执行结果
     *
     * @param sql 查询语句
     * @return ArrayList
     */
    QueryDto getQuerySql(String sql);

    /**
     * 增删改语句执行的结果
     *
     * @param sql 操作语句
     * @return
     */
    UpdateDto getUpdateSql(String sql);
}
