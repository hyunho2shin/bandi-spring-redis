package com.redis.command.reactive;

import com.redis.command.dto.UserDto;
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
        String redisKey = "TEST:userDto";

        UserDto userDto = UserDto.builder()
                .userId("redis-command")
                .userName("홍길동")
                .build();

        Mono<Boolean> setFlag = reactiveRedisTemplate.opsForValue().set(redisKey, userDto);
        setFlag.subscribe();
    }
//    @Test
    @DisplayName("ReactiveRedisTemplate으로 redis 조회하기")
    void reactiveRedisTemplateGetTest() {
        String redisKey = "TEST:userDto";

        Mono<Object> userMono = reactiveRedisTemplate.opsForValue().get(redisKey);

        Object userObj = userMono.block();

//        Mono<UserDto> user = userObject.cast(UserDto.class);

//                Mono<UserDto> user = reactiveRedisTemplate.opsForValue().get(redisKey)
//                 .cast(UserDto.class);

//         System.out.println("user = " + user.block());
         System.out.println("user = " + userObj);

    }
}
