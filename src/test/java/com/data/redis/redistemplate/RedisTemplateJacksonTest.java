package com.data.redis.redistemplate;

import com.data.redis.dto.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTemplateJacksonTest {

    @Qualifier("jackson")
    @Autowired
    private RedisTemplate<String, User> redisTemplate;

//    @Test
    @DisplayName("RedisTemplate Jackson으로 dto data 저장 하기")
    void redisTemplateDtoSetTest() {
        String redisKey = "user:jackson:hong";

        User user = User.builder()
                .id("redis-command")
                .name("홍길동")
                .build();

        redisTemplate.opsForValue().set(redisKey, user);
    }

    @Test
    @DisplayName("RedisTemplate Jackson으로 dto data 조회 하기")
    void redisTemplateDtoGetTest() {
        String redisKey = "user:jackson:hong";

        User user = redisTemplate.opsForValue().get(redisKey);

        System.out.println(user);
    }
}