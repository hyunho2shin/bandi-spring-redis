package com.data.redis.config;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

import java.time.Duration;

@Configuration
public class RedisConnectionFactoryConfig {
    private final RedisProperties redisProperties;

//    @Value("${spring.data.redis.host}")
//    private String redisHost;

//    @Value("${spring.data.redis.port}")
//    private int redisPort;

//    @Value("${spring.data.redis.database}")
//    private int redisDatabase;

    public RedisConnectionFactoryConfig(RedisProperties redisProperties) {
        this.redisProperties = redisProperties;
    }

    /**
     * RedisTemplate(동기API)와 ReactiveRedisTemplate(비동기API)를 둘다 사용시
     * RedisConnectionFactory, ReactiveRedisConnectionFactory를 둘다 구현하고 있는
     * LettuceConnectionFactory로 사용합니다.
     * @return
     */
    @Bean
    @Primary // 여러 ConnectionFactory가 있을 경우 기본으로 사용될 Fatory 지정 (선택적)
//    public RedisConnectionFactory redisConnectionFactory() {
    public LettuceConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(redisProperties.getHost());
        config.setPort(redisProperties.getPort());
        config.setDatabase(redisProperties.getDatabase());

        // Lettuce Client 설정 (선택 사항: 타임아웃 등)
        LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
                .commandTimeout(Duration.ofSeconds(10)) // 명령어 타임아웃
//                .shutdownTimeout(Duration.ZERO) // 종료 타임아웃
                .build();

        return new LettuceConnectionFactory(config, clientConfig);
    }
}
