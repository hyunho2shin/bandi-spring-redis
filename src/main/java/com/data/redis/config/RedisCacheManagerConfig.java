package com.data.redis.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;
import java.util.Collections;

// Spring Boot 환경에서는 @EnableCaching 어노테이션만 추가하고 spring-boot-starter-data-redis
// 의존성을 포함하면 기본적인 Redis 캐시 설정이 자동으로 완료됩니다.
// @Cacheable, @CachePut, @CacheEvict 어노테이션을 사용하여 메서드 수준에서 캐시 동작을 선언적으로 제어할 수 있습니다.
@EnableCaching // 스프링 캐시 기능 활성화
@Configuration
public class RedisCacheManagerConfig {
    /*
    * 참고 URL
    * https://dkswhdgur246.tistory.com/85
    * https://innovation123.tistory.com/273
    */

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        // ObjectMapper를 등록하지 않고, dto의 컬럼에 annotation을 이용하여 개별 제어 가능 (ex)null field 제외
        // 전역 ObjectMapper 설정이 더 유지보수 및 확장에 유리

        // 기본은 전역 설정, 특수한 경우에만 DTO에 어노테이션을 추가하는 혼합 전략을 선택.
        ObjectMapper objectMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                // null인 항목은(필드) 저장시 포함시키지 않음.
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                // json string 저장시 @Class 정보를 포함시킴.
                // Redis에서 조회하여 원래의 객체로 변환시 ClassCastException 오류를 방지함.
                .activateDefaultTyping(LaissezFaireSubTypeValidator.instance,
                        ObjectMapper.DefaultTyping.NON_FINAL,
                        JsonTypeInfo.As.PROPERTY);

        // 기본 설정을 정의.
        RedisCacheConfiguration defaultConfig = RedisCacheConfiguration.defaultCacheConfig()

                // 메서드 반환 값이 null일 경우, 기본적으로 캐시에 저장됩니다. NullPointerException과 같은 예외 상황을 막기 위함이지만,
                // 원치 않을 경우 비활성화할 수 있습니다.
                .disableCachingNullValues() // null 값 캐싱 비활성화(DO_NOT_CACHE_NULL_VALUES) -- null을 return시 오류발생.

                // .computePrefixWith(cacheName -> "my-prefix." + cacheName + ":") // 접두사 커스터마이징
                // return name -> name + SEPARATOR; SEPARATOR='::' // 기본 접두사 설정

                .entryTtl(Duration.ofMinutes(30)) //30분 동안 캐시 유지.

                // 기본적으로 Spring의 JdkSerializationRedisSerializer를 사용하여 Java 직렬화를 수행합니다.
                // 이는 사람이 읽기 어렵고 다른 언어와 호환되지 않습니다.
                // Jackson2JsonRedisSerializer를 사용하여 JSON 형식으로 저장하는 것이 권장됩니다.
                // 특히, 다른 시스템과의 호환성과 가독성을 위해 값의 직렬화 방식은 JSON으로 변경하는 것을 권장 합니다.
                .serializeValuesWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(
                                new GenericJackson2JsonRedisSerializer(objectMapper)
                        )
                )
                .enableTimeToIdle() // TTI 활성화  Requires Redis 6.2.0 or newer.
                ;

        /* .withInitialCacheConfigurations(Map.of(
                "books", RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(10)), // "books" 캐시는 10분
                "users", RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(1))      // "users" 캐시는 1시간
        ))*/

        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(defaultConfig)
                // Spring의 트랜잭션이 커밋될 때까지 Redis 캐시가 실제로 적용되지 않습니다.
                // @Transactional >> 트랜잭션 동기화
                // 캐시 저장은 트랜잭션 commit 후에 실행되므로 DB와 캐시의 정합성이 일치함

                // TransactionAwareCacheDecorator class내에 정의됨.
                // 트랜잭션이 활성화된 경우
                //   put(저장), evict(제거), clear(전체 삭제) 연산이 즉시 실행되지 않고 대기한다.
                //   트랜잭션이 성공적으로 커밋(commit)된 후, after-commit 단계에서 연산이 수행된다.
                //   즉, 트랜잭션이 롤백(rollback)되면 캐시 작업도 실행되지 않는다.
                .transactionAware()
                .withInitialCacheConfigurations(Collections.singletonMap("predefined",
                        RedisCacheConfiguration.defaultCacheConfig().disableCachingNullValues()))
                .build();
//        return RedisCacheManager.create(connectionFactory);
    }
}
