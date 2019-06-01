package com.its.config;

import com.its.entity.CardEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@Slf4j
public class RedisConfig {
    @Bean
    ReactiveRedisOperations<String, CardEntity> redisOperations(ReactiveRedisConnectionFactory factory) {
        log.info("Entering RedisConfig : redisOperations");
        Jackson2JsonRedisSerializer<CardEntity> serializer = new Jackson2JsonRedisSerializer<>(CardEntity.class);

        RedisSerializationContext.RedisSerializationContextBuilder<String, CardEntity> builder =
                RedisSerializationContext.newSerializationContext(new StringRedisSerializer());

        RedisSerializationContext<String, CardEntity> context = builder.value(serializer).build();
        log.info("Leaving RedisConfig : redisOperations after return ReactiveRedisTemplate");
        return new ReactiveRedisTemplate<>(factory, context);
    }
}
