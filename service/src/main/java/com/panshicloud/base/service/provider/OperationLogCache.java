package com.panshicloud.base.service.provider;

import com.panshicloud.base.remote.dto.OperationLogDto;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author xingxingfa
 * @since 2025/1/17
 */
public class OperationLogCache {

    /**
     * 日志
     */
    private static final ConcurrentLinkedQueue<OperationLogDto> OPERATION_LOG_LIST = new ConcurrentLinkedQueue<>();

    /**
     * 计数器
     */
    public static final AtomicInteger COUNT = new AtomicInteger(0);

    /**
     * 添加日志
     *
     * @param operationLog 日志
     */
    public static void add(OperationLogDto operationLog) {
        COUNT.incrementAndGet();
        OPERATION_LOG_LIST.add(operationLog);
    }

    public static ConcurrentLinkedQueue<OperationLogDto> find() {
        COUNT.set(0);
        return OPERATION_LOG_LIST;
    }

    public static void clear() {
        OPERATION_LOG_LIST.clear();
    }
}
