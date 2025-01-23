package com.panshicloud.base.service.provider;

import com.panshicloud.base.remote.dto.OperationLogDto;
import com.panshicloud.base.remote.service.IOperationLogDataService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author 86176
 */
@Configuration
@Component
public class OperationLogCustomer implements CommandLineRunner {

    @DubboReference
    private IOperationLogDataService operationLogDataService;

    @Override
    @Async
    public void run(String... args) {
        while (true) {
            // 超过3000，保存一次
            if (OperationLogCache.COUNT.get() >= 3000) {
                synchronized (OperationLogCache.class) {
                    try {
                        ConcurrentLinkedQueue<OperationLogDto> queue = OperationLogCache.find();
                        operationLogDataService.insertBatch(new ArrayList<>(queue));
                        OperationLogCache.clear();
                    } catch (Exception e) {
                        e.printStackTrace();
                        continue;
                    }
                }
            }
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 另起一个线程，每隔一段时间保存一次日志
     */
    @Scheduled(cron = "0 0 0/1 * * ?")
    public void truncateTmp() {
        synchronized (OperationLogCache.class) {
            ConcurrentLinkedQueue<OperationLogDto> queue = OperationLogCache.find();
            if (!queue.isEmpty()) {
                operationLogDataService.insertBatch(new ArrayList<>(queue));
                OperationLogCache.clear();
            }
        }
    }

}
