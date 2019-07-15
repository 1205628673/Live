package com.jlau.live.component;

import org.springframework.cache.Cache;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.lang.Nullable;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by cxr1205628673 on 2019/7/7.
 */
public class LocalRedisCache implements Cache{
    ConcurrentHashMap<Object,Object> local = new ConcurrentHashMap<>();
    RedisCache redisCache;
    TwoLevelCacheManager cacheManager;

    public LocalRedisCache(RedisCache redisCache, TwoLevelCacheManager cacheManager) {
        this.redisCache = redisCache;
        this.cacheManager = cacheManager;
    }

    public void clearOtherJVM(){
        this.cacheManager.publishMessage(redisCache.getName());//获取缓存的名称
        this.local.clear();
    }
    public void clearLocal(){
        this.local.clear();
    }
    @Override
    public String getName() {
        return redisCache.getName();
    }

    @Override
    public Object getNativeCache() {
        return redisCache.getNativeCache();
    }

    @Override
    public ValueWrapper get(Object key) {
        ValueWrapper wrapper = (ValueWrapper) local.get(key);
        if(wrapper != null){
            return wrapper;
        }else{
            wrapper = redisCache.get(key);
            if(wrapper != null){
                local.put(key,wrapper);
            }
            return wrapper;
        }
    }

    @Override
    public <T> T get(Object key, @Nullable Class<T> aClass) {
         ValueWrapper wrapper = (ValueWrapper) local.get(key);
        if(wrapper != null){
            return (T)wrapper;
        }else{
            wrapper = (ValueWrapper) redisCache.get(key,aClass);
            if(wrapper != null){
                local.put(key,wrapper);
            }
            return (T)wrapper;
        }
    }

    @Override
    public <T> T get(Object key, Callable<T> callable) {
        return redisCache.get(key,callable);
    }

    @Override
    public void put(Object key, @Nullable Object o1) {
        redisCache.put(key,o1);
        clearOtherJVM();
    }

    @Override
    public ValueWrapper putIfAbsent(Object key, @Nullable Object o1) {
        ValueWrapper wrapper = (ValueWrapper) local.get(key);
        if(wrapper != null){
            return wrapper;
        }else{
            wrapper = redisCache.putIfAbsent(key,o1);
            clearOtherJVM();
            return wrapper;
        }
    }

    @Override
    public void evict(Object key) {
        redisCache.evict(key);
        clearOtherJVM();
    }

    @Override
    public void clear() {
        redisCache.clear();
    }
}
