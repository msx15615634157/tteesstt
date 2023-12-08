package com.panshicloud.base.service.provider;

import com.panshicloud.base.remote.dto.OperationLogDto;
import com.panshicloud.base.remote.service.IOperationLogDataService;
import com.panshicloud.common.context.CommonContext;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author xingxingfa
 * @since 2023/11/24
 */
@Service
public class OperationLogAsyncService {

    @DubboReference
    private IOperationLogDataService operationLogDataService;

    @Async
    public void asyncInsert(OperationLogDto operationLogDto, CommonContext.User user) {
        if (CommonContext.getUser() == null) {
            CommonContext.setUser(user);
        }
        operationLogDataService.insert(operationLogDto);
    }
}
