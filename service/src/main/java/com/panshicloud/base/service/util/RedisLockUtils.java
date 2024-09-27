package com.panshicloud.base.service.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisLockUtils {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 原子性操作 加锁
     */
    public boolean lock(String key, String value, long timeout, TimeUnit unit) {
        return Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(key, value, timeout, unit));
    }

    /**
     * 原子性操作 解锁
     */
    public void unlock(String key) {
        redisTemplate.delete(key);
    }
}
