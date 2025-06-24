package com.data.redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisKeyValueAdapter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
// basePackages를 생략하는경우 해당 애노테이션이 선언된 클래스의 패키지와
// 그하위 패키지 내의 CrudRepository를 상속한 인터페이스들을 자동으로 스캔합니다.
@EnableRedisRepositories(basePackages = "com.data.redis.repository"

        // ttl이 만료된경우 나머지 보조 인덱스 데이터(SET)도 삭제합니다.
        // user:id:'phantom' 의 이름으로 hash data가 추가 생성되고, ttl이 hash본 데이터 + 5(300초)로 생성됨.
        // ttl을 0,-1(영구 저장)로 저장시에는 생성되지 않음.
        // 삭제 되는 내역 확인 할때는 redis-cli의 monitor 기능으로 확인함.
        ,enableKeyspaceEvents = RedisKeyValueAdapter.EnableKeyspaceEvents.OFF // ON_STARTUP, ON_DEMAND 활성화 하기

        // RedisTemplate bean name 명시
        ,redisTemplateRef = "redisTemplate"
//@EnableRedisRepositories(basePackages = "com.data.redis.repository" //,
//        includeFilters = { @ComponentScan.Filter(type = FilterType.REGEX, pattern = ".*SomeRepository") },
//        excludeFilters = { @ComponentScan.Filter(type = FilterType.REGEX, pattern = ".*SomeOtherRepository") }
)
public class RedisRepositoryConfig {
    /**
     * @param redisConnectionFactory LettuceConnectionFactory Bean이 자동으로 주입됩니다.
     * @return
     */
//    @Primary
    @Bean("redisTemplate")
    public RedisTemplate<?, ?> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<byte[], byte[]> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setEnableTransactionSupport(false); // 트랙재션 비활성화 (기본값 false)
        return redisTemplate;
    }
}
