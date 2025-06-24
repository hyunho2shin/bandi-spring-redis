package com.data.redis.reactive;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.data.redis.dto.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.ReactiveRedisConnection;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.util.ByteUtils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

@SpringBootTest
public class ReactiveRedisConnectionTest {

    @Autowired
    LettuceConnectionFactory connectionFactory;

    @Autowired
    ObjectMapper objectMapper;
    @Test
    @DisplayName("ReactiveRedisConnection을 이용하여 dto class정보를 redis json data에 추가하지 않고 직접 변환하기")
    void reactiveRedisConnectionGetTest() {
        String redisKey = "TEST:user";

        ReactiveRedisConnection reactiveRedisConnection = connectionFactory.getReactiveConnection();
        ByteBuffer keyBuffer = ByteBuffer.wrap(redisKey.getBytes(StandardCharsets.UTF_8));

        User user = reactiveRedisConnection.stringCommands().get(keyBuffer)
                .map(ByteUtils::getBytes)
                .map(bytes -> {
                    try {
                        return objectMapper.readValue(bytes, User.class);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .block();
        ;

        reactiveRedisConnection.closeLater();

        System.out.println("user = " + user);
    }
}
