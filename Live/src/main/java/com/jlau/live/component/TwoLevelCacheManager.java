package com.jlau.live.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Created by cxr1205628673 on 2019/7/7.
 */
public class TwoLevelCacheManager extends RedisCacheManager{
    private RedisTemplate redisTemplate;
    String topic = "token";//redis订阅频道
    public TwoLevelCacheManager(RedisTemplate redisTemplate, RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultConfiguration) {
        super(cacheWriter,defaultConfiguration);
        this.redisTemplate = redisTemplate;

    }

    @Override
    protected Cache decorateCache(Cache cache) {
        return new LocalRedisCache((RedisCache)cache,this);
    }

    public void publishMessage(String cacheName){
        redisTemplate.convertAndSend(topic,cacheName);
    }

    public void receiver(String name){
        LocalRedisCache localRedisCache = (LocalRedisCache) this.getCache(name);
        if(localRedisCache != null) {
            localRedisCache.clearLocal();
        }
    }
}
