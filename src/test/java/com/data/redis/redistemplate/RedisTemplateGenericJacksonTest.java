package com.data.redis.redistemplate;

import com.data.redis.dto.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTemplateGenericJacksonTest {

    @Qualifier("genericJackson")
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

//    @Test
    @DisplayName("RedisTemplate으로 string data set/get 하기")
    void redisTemplateStringTest() {
        String redisKey = "string:hong";
        String value = "홍길동";
        redisTemplate.opsForValue().set(redisKey, value);

        Object data = redisTemplate.opsForValue().get(redisKey);

        System.out.println(data);

        // RedisTemplate내에서 직렬화(RedisSerializer) 자동 처리
        // 커넥션 연결 처리 : 내부적으로 자동 연결/해제
    }

//    @Test
    @DisplayName("RedisTemplate GenericJackson으로 dto data 저장 하기")
    void redisTemplateDtoSetTest() {
        String redisKey = "user:genericJackson:hong";

        User user = User.builder()
                .id("redis-command")
                .name("홍길동")
                .build();

        redisTemplate.opsForValue().set(redisKey, user);
    }

    @Test
    @DisplayName("RedisTemplate GenericJackson으로 dto data 조회 하기")
    void redisTemplateDtoGetTest() {
        String redisKey = "user:genericJackson:hong";

        Object data = redisTemplate.opsForValue().get(redisKey);

        User castDto = (User) data;

        System.out.println(data);
        System.out.println(castDto);

        // RedisTemplate내에서 직렬화(RedisSerializer) 자동 처리
        // 커넥션 연결 처리 : 내부적으로 자동 연결/해제
    }
}