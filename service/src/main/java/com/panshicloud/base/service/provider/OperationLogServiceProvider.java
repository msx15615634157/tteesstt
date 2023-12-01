package com.panshicloud.base.service.provider;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.panshicloud.base.dao.entity.OperationLog;
import com.panshicloud.base.dao.mapper.OperationLogMapper;
import com.panshicloud.base.remote.dto.LogDto;
import com.panshicloud.base.remote.dto.OperationLogDto;
import com.panshicloud.base.remote.service.IOperationLogService;
import com.panshicloud.common.base.PageDto;
import com.panshicloud.common.export.excel.ExcelExportUtils;
import com.panshicloud.common.export.excel.entity.Col;
import com.panshicloud.common.export.excel.entity.Excel;
import com.panshicloud.common.export.excel.entity.Row;
import com.panshicloud.common.export.excel.entity.Sheet;
import com.panshicloud.common.helper.ConvertHelper;
import com.panshicloud.common.utils.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.xml.crypto.Data;
import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 操作日志服务实现类
 * </p>
 *
 * @author huangrankun
 */

@DubboService
public class OperationLogServiceProvider extends ServiceImpl<OperationLogMapper, OperationLog> implements IOperationLogService {

    @Autowired
    private OperationLogMapper operationLogMapper;

    @Override
    public boolean insert(OperationLogDto operationLogDto) {
        return save(ConvertHelper.tToV(operationLogDto, OperationLog.class));
    }

    @Override
    public void deleteByData(Data data) {
        lambdaUpdate()
                .gt(OperationLog::getOperationTime, data)
                .remove();
    }

    @Override
    public PageDto<LogDto> findPage(Integer pageNumber, Integer pageSize, Date startTime, Date endTime, String type, String operation, String operationUserId) {
        IPage<OperationLog> page = new Page(pageNumber, pageSize);
        LambdaQueryWrapper<OperationLog> queryWrapper = getQueryWapper(startTime, endTime, type, operation, operationUserId);
        page = operationLogMapper.selectPage(page, queryWrapper);
        return new PageDto<>(page, LogDto.class);
    }

    @Override
    public ByteArrayOutputStream archiveExport(Date startTime, Date endTime, String type, String operation, String operationUserId) {
        LambdaQueryWrapper<OperationLog> queryWrapper = getQueryWapper(startTime, endTime, type, operation, operationUserId);
        List<OperationLog> operationLogs = operationLogMapper.selectList(queryWrapper);
        Excel excel = new Excel();
        Sheet sheet = new Sheet("日志");
        excel.addSheet(sheet);
        Row firstRow = new Row();
        sheet.addRow(firstRow);
        // 表头
        {
            Col col1 = new Col();
            col1.value = "类型";
            Col col2 = new Col();
            col2.value = "操作";
            Col col3 = new Col();
            col3.value = "日志内容";
            Col col4 = new Col();
            col4.value = "操作时间实体";
            Col col5 = new Col();
            col5.value = "操作组织机构Id";
            Col col6 = new Col();
            col6.value = "操作用户id";
            Col col7 = new Col();
            col7.value = "接口名";
            Col col8 = new Col();
            col8.value = "本机IP";
            Col col9 = new Col();
            col9.value = "客户IP";
            Col col10 = new Col();
            col10.value = "客户名";
            Col col11 = new Col();
            col11.value = "URI";
            Col col12 = new Col();
            col12.value = "URL";
            Col col13 = new Col();
            col13.value = "token";
            Col col14 = new Col();
            col14.value = "查询字符串";
            firstRow.addCol(col1);
            firstRow.addCol(col2);
            firstRow.addCol(col3);
            firstRow.addCol(col4);
            firstRow.addCol(col5);
            firstRow.addCol(col6);
            firstRow.addCol(col7);
            firstRow.addCol(col8);
            firstRow.addCol(col9);
            firstRow.addCol(col10);
            firstRow.addCol(col11);
            firstRow.addCol(col12);
            firstRow.addCol(col13);
            firstRow.addCol(col14);
        }
        for (OperationLog operationLog : operationLogs) {
            Row row = new Row();
            sheet.addRow(row);
            {
                Col col = new Col();
                if (operationLog.getType() != null) {
                    col.value = operationLog.getType();
                }
                row.addCol(col);
            }
            {
                Col col = new Col();
                if (operationLog.getOperation() != null) {
                    col.value = operationLog.getOperation();
                }
                row.addCol(col);
            }
            {
                Col col = new Col();
                if (operationLog.getLogInfo() != null) {
                    col.value = operationLog.getLogInfo();
                }
                row.addCol(col);
            }
            {
                Col col = new Col();
                if (operationLog.getOperationTime() != null) {
                    col.value = String.valueOf(operationLog.getOperationTime());
                }
                row.addCol(col);
            }
            {
                Col col = new Col();
                if (operationLog.getOperationOrganizationId() != null) {
                    col.value = operationLog.getOperationOrganizationId();
                }
                row.addCol(col);
            }
            {
                Col col = new Col();
                if (operationLog.getOperationUserId() != null) {
                    col.value = operationLog.getOperationUserId();
                }
                row.addCol(col);
            }
            {
                Col col = new Col();
                if (operationLog.getInterfaceName() != null) {
                    col.value = operationLog.getInterfaceName();
                }
                row.addCol(col);
            }
            {
                Col col = new Col();
                if (operationLog.getServerIp() != null) {
                    col.value = operationLog.getServerIp();
                }
                row.addCol(col);
            }
            {
                Col col = new Col();
                if (operationLog.getClientIp() != null) {
                    col.value = operationLog.getClientIp();
                }
                row.addCol(col);
            }
            {
                Col col = new Col();
                if (operationLog.getClientCode() != null) {
                    col.value = operationLog.getClientCode();
                }
                row.addCol(col);
            }
            {
                Col col = new Col();
                if (operationLog.getUri() != null) {
                    col.value = operationLog.getUri();
                }
                row.addCol(col);
            }
            {
                Col col = new Col();
                if (operationLog.getUrl() != null) {
                    col.value = operationLog.getUrl();
                }
                row.addCol(col);
            }
            {
                Col col = new Col();
                if (operationLog.getToken() != null) {
                    col.value = operationLog.getToken();
                }
                row.addCol(col);
            }
            {
                Col col = new Col();
                if (operationLog.getQueryString() != null) {
                    col.value = operationLog.getQueryString();
                }
                row.addCol(col);
            }
        }
        return ExcelExportUtils.toXlsx(excel);

    }

    private LambdaQueryWrapper<OperationLog> getQueryWapper(Date startTime, Date endTime, String type, String operation, String operationUserId) {
        LambdaQueryWrapper<OperationLog> queryWrapper = new LambdaQueryWrapper<>();
        if (!Objects.isNull(startTime)) {
            queryWrapper.ge(OperationLog::getOperationTime, startTime);
        }
        if (!Objects.isNull(endTime)) {
            queryWrapper.le(OperationLog::getOperationTime, endTime);
        }
        if (StringUtils.isNotBlank(type)) {
            queryWrapper.like(OperationLog::getType, type);
        }
        if (StringUtils.isNotBlank(operation)) {
            queryWrapper.like(OperationLog::getOperation, operation);
        }
        if (StringUtils.isNotBlank(operationUserId)) {
            queryWrapper.eq(OperationLog::getOperationUserId, operationUserId);
        }
        queryWrapper.orderByDesc(OperationLog::getOperationTime);
        return queryWrapper;
    }

    @Override
    public void delete(Date startTime, Date endTime, String type, String operation, String operationUserId) {
        LambdaQueryWrapper<OperationLog> queryWrapper = getQueryWapper(startTime, endTime, type, operation, operationUserId);
        remove(queryWrapper);
    }
}
