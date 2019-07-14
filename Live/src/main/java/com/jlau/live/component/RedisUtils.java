package com.jlau.live.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.CollectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by cxr1205628673 on 2019/7/6.
 */
@Component
public class RedisUtils {
    @Autowired
    private RedisTemplate<String,String> template;

    public boolean expire(String key,long time){
        try {
            template.expire(key, time, TimeUnit.SECONDS);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public long getExpire(String key){
        return template.getExpire(key,TimeUnit.SECONDS);
    }
    public boolean hasKey(String key){
        try{
            return template.hasKey(key);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    public void del(String... key){
        if(key != null && key.length>0){
            if(key.length == 1){
                template.delete(key[0]);
            }else{
                template.delete(Arrays.asList(key));
            }
        }
    }
    public String get(String key){
        return key==null? null : template.opsForValue().get(key);
    }
    public boolean set(String key,String value){
        try{
            template.opsForValue().set(key,value);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public boolean set(String key,String value,long time){
        try{
            template.opsForValue().set(key,value,time);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public long incr(String key,long delta){
        if(delta <0){
            throw new RuntimeException("delta 必须大于0");
        }
        return template.opsForValue().increment(key,delta);
    }
    public long decr(String key,long delta){
        if(delta<0){
            throw new RuntimeException("delta 必须大于0");
        }
        return template.opsForValue().decrement(key,delta);
    }
    public Object hget(String key,String item){
        return template.opsForHash().get(key,item);
    }
    public boolean hset(String key,String item,String value){
        try{
            template.opsForHash().put(key,item,value);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    public boolean hset(String key,String item,String value,long time){
        try{
            template.opsForHash().put(key,item,value);
            if(time > 0){
                expire(key,time);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    public Map<Object,Object> hmget(String key){
        return template.opsForHash().entries(key);
    }
    public boolean hmset(String key,Map<Object,Object> item){
        try{
            template.opsForHash().putAll(key,item);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    public boolean hmset(String key,Map<Object,Object> item,long time){
        try{
            template.opsForHash().putAll(key,item);
            if(time>0){
                expire(key,time);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
