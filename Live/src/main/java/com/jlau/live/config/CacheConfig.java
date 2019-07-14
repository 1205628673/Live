package com.jlau.live.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jlau.live.component.TwoLevelCacheManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.lang.Nullable;

/**
 * Created by cxr1205628673 on 2019/7/7.
 */
@Configuration
@PropertySource("classpath:redislocal.properties")
public class CacheConfig {
    @Value("${ext.cache.topic:cache}")
    String topic;
    @Bean
    @SuppressWarnings("all")
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory redisConnectionFactory, MessageListenerAdapter messageListenerAdapter){
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        container.addMessageListener(messageListenerAdapter,new PatternTopic(topic));
        return container;
    }
    @Bean
    public MessageListenerAdapter messageListenerAdapter(final TwoLevelCacheManager manager){
        return new MessageListenerAdapter(){
            @Override
            public void onMessage(Message message, @Nullable byte[] pattern) {
                try {
                    String cacheName = new String(message.getBody(), "UTF-8");
                    manager.receiver(cacheName);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
    }
    @Bean
    public TwoLevelCacheManager twoLevelCacheManager(StringRedisTemplate redisTemplate,ObjectMapper objectMapper){
        RedisCacheWriter writer = RedisCacheWriter.lockingRedisCacheWriter(redisTemplate.getConnectionFactory());
        RedisSerializationContext.SerializationPair pair = RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer(objectMapper));
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig().serializeValuesWith(pair);
        TwoLevelCacheManager manager = new TwoLevelCacheManager(redisTemplate,writer,redisCacheConfiguration);
        return manager;
    }
}
