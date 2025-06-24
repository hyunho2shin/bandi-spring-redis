package com.data.redis.redistemplate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
public class StringRedisTemplateTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

//    @Test
    @DisplayName("StringRedisTemplate으로 redis 저장하기")
    void stringRedisTemplateSetTest() {
        String redisKey = "userToken:2394913";
        String value = "465afe70-ea78-4766-ae25-4aa6dbe0a70e";
        stringRedisTemplate.opsForValue().set(redisKey, value);
    }

    @Test
    @DisplayName("StringRedisTemplate으로 redis 조회하기")
    void stringRedisTemplateGetTest() {
        String redisKey = "userToken:2394913";

        String userToken = stringRedisTemplate.opsForValue().get(redisKey);

        System.out.println(userToken);
    }
}
