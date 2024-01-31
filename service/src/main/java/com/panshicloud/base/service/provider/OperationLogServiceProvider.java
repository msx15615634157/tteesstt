package com.panshicloud.base.service.provider;

import com.panshicloud.base.remote.dto.OperationLogDto;
import com.panshicloud.base.remote.service.IOperationLogService;
import com.panshicloud.base.service.constants.RedisKeyCst;
import com.panshicloud.common.context.CommonContext;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

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

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void insert(OperationLogDto operationLogDto) {
        CommonContext.User user = CommonContext.getUser();
        operationLogDto.setOperationTime(new Date());
        operationLogDto.setOperationUserId(user == null ? "NO LOGIN" : user.getId());
        operationLogDto.setOperationOrganizationId(user == null ? "NO LOGIN" : user.getOrganizationId());
        redisTemplate.boundListOps(RedisKeyCst.OPERATION_LOG_QUEUE).leftPush(operationLogDto);
    }
}
