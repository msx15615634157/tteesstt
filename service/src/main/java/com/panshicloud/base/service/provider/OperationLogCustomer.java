package com.panshicloud.base.service.provider;

import com.panshicloud.base.remote.dto.OperationLogDto;
import com.panshicloud.base.remote.service.IOperationLogDataService;
import com.panshicloud.base.service.constants.RedisKeyCst;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 86176
 */
@Configuration
@Component
public class OperationLogCustomer implements CommandLineRunner {

    @Autowired
    private RedisTemplate redisTemplate;
    @DubboReference
    private IOperationLogDataService operationLogDataService;

    private final List<OperationLogDto> logs = new ArrayList<>();

    private final byte[] lock = new byte[0];

    @Override
    @Async
    public void run(String... args) {
        while (true) {
            OperationLogDto log = (OperationLogDto) redisTemplate.boundListOps(RedisKeyCst.OPERATION_LOG_QUEUE).rightPop();
            if (log != null) {
                logs.add(log);
            }
            // 超过100，保存一次
            if (logs.size() >= 100) {
                synchronized (lock) {
                    if (logs.size() >= 100) {
                        operationLogDataService.insertBatch(logs);
                        logs.clear();
                    }
                }
            }
        }
    }

    /**
     * 另起一个线程，每隔一段时间保存一次日志
     */
    @Scheduled(cron = "0 0 0/1 * * ?")
    public void truncateTmp() {
        synchronized (lock) {
            if (!logs.isEmpty()) {
                operationLogDataService.insertBatch(logs);
                logs.clear();
            }
        }
    }

}
