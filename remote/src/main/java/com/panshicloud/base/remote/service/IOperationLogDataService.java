package com.panshicloud.base.remote.service;

import com.panshicloud.base.remote.dto.LogDto;
import com.panshicloud.base.remote.dto.OperationLogDto;
import com.panshicloud.common.base.PageDto;

import javax.xml.crypto.Data;
import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.List;

/**
 * @author xingxingfa
 * @since 2023/11/24
 */
public interface IOperationLogDataService {
    /**
     * 新增
     *
     * @param operationLogDto 操作日志
     */
    void insert(OperationLogDto operationLogDto);

    /**
     * 批量新增
     *
     * @param operationLogs
     */
    void insertBatch(List<OperationLogDto> operationLogs);

    /**
     * 删除-按时间
     *
     * @param data
     */
    void deleteByData(Data data);

    /**
     * 查询所有操作日志--分页
     *
     * @param pageNumber
     * @param pageSize
     * @param startTime       开始时间
     * @param endTime         结束时间
     * @param type            类型/模块
     * @param operation       功能
     * @param operationUserId 操作用户id
     * @return
     */
    PageDto<LogDto> findPage(Integer pageNumber, Integer pageSize, Date startTime, Date endTime, String type, String operation, String operationUserId);

    /**
     * 归档导出
     *
     * @param startTime       开始时间
     * @param endTime         结束时间
     * @param type            类型
     * @param operation       操作
     * @param operationUserId 操作用户id
     * @return
     */
    ByteArrayOutputStream archiveExport(Date startTime, Date endTime, String type, String operation, String operationUserId);


    /**
     * 删除日志
     *
     * @param startTime       开始时间
     * @param endTime         结束时间
     * @param type            类型
     * @param operation       操作
     * @param operationUserId 操作用户id
     */
    void delete(Date startTime, Date endTime, String type, String operation, String operationUserId);
}
