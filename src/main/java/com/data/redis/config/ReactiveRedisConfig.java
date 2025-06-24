package com.redis.command.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class ReactiveRedisConfig {
    private final RedisProperties redisProperties;

    public ReactiveRedisConfig(RedisProperties redisProperties) {
        this.redisProperties = redisProperties;
    }

    @Primary
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
//        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL); // Deprecated

        return mapper;
    }

    /**
     * @param factory LettuceConnectionFactory Bean이 ReactiveRedisConnectionFactory 타입으로 자동 주입됩니다.
     * @param mapper
     */
    @Bean
    public ReactiveRedisTemplate<String, Object> reactiveRedisTemplate(ReactiveRedisConnectionFactory factory, ObjectMapper mapper) {
        StringRedisSerializer defaultSerializer = new StringRedisSerializer();

        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(mapper, Object.class);

        // 직렬화 방식 설정
        RedisSerializationContext<String, Object> context = RedisSerializationContext
                .<String, Object>newSerializationContext(defaultSerializer)
                .value(serializer)
                .build();

        return new ReactiveRedisTemplate<>(factory, context);
    }
}
