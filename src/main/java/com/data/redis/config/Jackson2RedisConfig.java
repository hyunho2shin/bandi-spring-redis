package com.redis.command.config;

import com.redis.command.dto.UserDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class Jackson2RedisConfig {
    /**
     * @param factory LettuceConnectionFactory Bean이 자동으로 주입됩니다.
     * @return
     */
    @Bean(name = "jackson")
    public RedisTemplate<String, UserDto> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, UserDto> redisTemplate = new RedisTemplate<>();
        // RedisRepository 사용시에는 Redis의 트랜잭션 기능과는 호환되지 않습니다.
        // 따라서, 사용할 때는 트랜잭션 기능이 비활성화된 RedisTemplate을 사용해야 합니다.
        // https://docs.spring.io/spring-data/redis/reference/repositories.html
        // redisTemplate.setEnableTransactionSupport(false); // 트랙재션 활성화 (기본값은 false)

        redisTemplate.setConnectionFactory( factory );
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());

        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer(UserDto.class));
        redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer(UserDto.class));
        return redisTemplate;
    }

}
