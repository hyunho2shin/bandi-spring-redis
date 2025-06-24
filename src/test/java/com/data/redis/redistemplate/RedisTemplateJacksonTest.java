package com.redis.command.redistemplate;

import com.redis.command.dto.UserDto;
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
    RedisTemplate<String, UserDto> redisTemplate;

//    @Test
    @DisplayName("RedisTemplate Jackson으로 dto data 저장 하기")
    void redisTemplateDtoSetTest() {
        String redisKey = "userDto:jackson:hong";

        UserDto userDto = UserDto.builder()
                .userId("redis-command")
                .userName("홍길동")
                .build();

        redisTemplate.opsForValue().set(redisKey, userDto);
    }

    @Test
    @DisplayName("RedisTemplate Jackson으로 dto data 조회 하기")
    void redisTemplateDtoGetTest() {
        String redisKey = "userDto:jackson:hong";

        UserDto userDto = redisTemplate.opsForValue().get(redisKey);

        System.out.println(userDto);
    }
}