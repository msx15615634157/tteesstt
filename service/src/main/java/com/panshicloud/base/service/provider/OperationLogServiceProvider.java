package com.panshicloud.base.service.provider;

import com.panshicloud.base.remote.dto.OperationLogDto;
import com.panshicloud.base.remote.service.IOperationLogService;
import com.panshicloud.common.context.CommonContext;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.Date;

/**
 * <p>
 * 操作日志服务实现类
 * </p>
 *
 * @author huangrankun
 */

@DubboService
public class OperationLogServiceProvider implements IOperationLogService {

    @Override
    public void insert(OperationLogDto operationLogDto) {
        CommonContext.User user = CommonContext.getUser();
        operationLogDto.setOperationTime(new Date());
        operationLogDto.setOperationUserId(user == null ? "NO LOGIN" : user.getId());
        operationLogDto.setOperationOrganizationId(user == null ? "NO LOGIN" : user.getOrganizationId());
        OperationLogCache.add(operationLogDto);
    }
}
