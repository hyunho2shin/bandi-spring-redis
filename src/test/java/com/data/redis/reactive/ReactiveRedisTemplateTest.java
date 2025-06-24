package com.data.redis.reactive;

import com.data.redis.dto.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import reactor.core.publisher.Mono;

@SpringBootTest
public class ReactiveRedisTemplateTest {

    @Autowired
    ReactiveRedisTemplate<String, Object> reactiveRedisTemplate;

    @Test
    @DisplayName("ReactiveRedisTemplate으로 redis 저장 하기")
    void reactiveRedisTemplateSetTest() {
        String redisKey = "TEST:user";

        User user = User.builder()
                .id("redis-command")
                .name("홍길동")
                .build();

        Mono<Boolean> setFlag = reactiveRedisTemplate.opsForValue().set(redisKey, user);
        setFlag.subscribe();
    }
//    @Test
    @DisplayName("ReactiveRedisTemplate으로 redis 조회하기")
    void reactiveRedisTemplateGetTest() {
        String redisKey = "TEST:user";

        Mono<Object> userMono = reactiveRedisTemplate.opsForValue().get(redisKey);

        Object userObj = userMono.block();

//        Mono<User> user = userObject.cast(User.class);

//                Mono<User> user = reactiveRedisTemplate.opsForValue().get(redisKey)
//                 .cast(User.class);

//         System.out.println("user = " + user.block());
         System.out.println("user = " + userObj);

    }
}
