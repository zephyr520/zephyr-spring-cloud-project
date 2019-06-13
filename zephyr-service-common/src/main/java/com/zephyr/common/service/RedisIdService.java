package com.zephyr.common.service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 * @DATE 2018/11/29
 */
@Component
public class RedisIdService {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    public void set(String key, int value, long timeout, TimeUnit unit) {
        RedisAtomicLong counter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
        counter.set(value);
        counter.expire(timeout, unit);
    }

    /**
     * @Title: set
     * @Description: set cache.
     * @param key
     * @param value
     * @param expireTime
     */
    public void set(String key,int value,Date expireTime) {
        RedisAtomicLong counter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
        counter.set(value);
        counter.expireAt(expireTime);
    }

    /**
     * 获取指定key的自增序列
     * @param key
     * @return
     * @Title: generate
     * @Description: Atomically increments by one the current value.
     */
    public long getNextID(String key) {
        RedisAtomicLong counter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
        return counter.incrementAndGet();
    }

    /**
     * 获取指定key的自增序列，并设置key的到期时间
     * @param key
     * @return
     * @Title: generate
     * @Description: Atomically increments by one the current value.
     */
    public long getNextID(String key, Date expireTime) {
        RedisAtomicLong counter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
        counter.expireAt(expireTime);
        return counter.incrementAndGet();
    }

    /**
     * 获取指定key的当前值，并在此基础上增加指定的值
     * @Title: generate
     * @Description: Atomically adds the given value to the current value.
     * @param key
     * @param increment
     * @return
     */
    public long getNextID(String key,int increment) {
        RedisAtomicLong counter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
        return counter.addAndGet(increment);
    }

    /**
     * 获取指定key的当前值，并在此基础上增加指定的值，并设置过期时间
     * @Title: generate
     * @Description: Atomically adds the given value to the current value.
     * @param key
     * @param increment
     * @param expireTime
     * @return
     */
    public long getNextID(String key,int increment,Date expireTime) {
        RedisAtomicLong counter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
        counter.expireAt(expireTime);
        return counter.addAndGet(increment);
    }
}
