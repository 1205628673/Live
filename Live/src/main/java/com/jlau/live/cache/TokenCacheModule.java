package com.jlau.live.cache;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * Created by cxr1205628673 on 2019/7/7.
 */
@Service
public class TokenCacheModule {
    @Autowired
    StringRedisTemplate redisTemplate;
    @Autowired
    ObjectMapper objectMapper;
    Log log = LogFactory.getLog(TokenCacheModule.class);
    @Cacheable("token")
    public String checkToken(String key){
        System.out.println("找不到一级缓存,查找二级缓存并清除一级缓存");
        String token =  redisTemplate.opsForValue().get(key);
        if(token != null && !token.equals("")){
            try {
                return token;
            }catch (Exception e){
                e.printStackTrace();
                log.error(e.getCause());
                return null;
            }
        }else{
            return null;
        }
    }
}
