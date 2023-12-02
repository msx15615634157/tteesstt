package com.panshicloud.base.service.provider;

import com.panshicloud.base.remote.dto.OperationLogDto;
import com.panshicloud.base.remote.service.IOperationLogService;
import com.panshicloud.common.context.CommonContext;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 操作日志服务实现类
 * </p>
 *
 * @author huangrankun
 */

@DubboService
public class OperationLogServiceProvider implements IOperationLogService {

    @Autowired
    private OperationLogAsyncService operationLogAsyncService;

    @Override
    public void insert(OperationLogDto operationLogDto) {
        CommonContext.User user = CommonContext.getUser();
        operationLogAsyncService.asyncInsert(operationLogDto, user);
    }
}
