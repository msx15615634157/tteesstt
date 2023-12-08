package com.panshicloud.base.remote.service;

import com.panshicloud.base.remote.dto.OperationLogDto;

/**
 * <p>
 * 操作日志服务
 * </p>
 *
 * @author huangrankun
 */

public interface IOperationLogService {

    /**
     * 新增
     *
     * @param operationLogDto 操作日志
     */
    void insert(OperationLogDto operationLogDto);
}
