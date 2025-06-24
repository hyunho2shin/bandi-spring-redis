package com.data.redis.connfactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.data.redis.dto.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisConnectionUtils;

import java.io.IOException;

@SpringBootTest
public class RedisConnectionFactoryTest {

    @Autowired
    RedisConnectionFactory connectionFactory;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    @DisplayName("RedisConnection으로 dto data 조회 하기")
    void redisConnectionDtoGetTest() throws IOException {
        String redisKey = "user:jackson:hong";

        User user = getRedisDto(redisKey, User.class);

        System.out.println(user);
    }

    private <T> T getRedisDto(String redisKey, Class<T> clazz) throws IOException {
        RedisConnection redisConnection = RedisConnectionUtils.getConnection(connectionFactory);

        byte[] redisDataByte = redisConnection.stringCommands().get(redisKey.getBytes());

        RedisConnectionUtils.releaseConnection(redisConnection, connectionFactory);

        if (redisDataByte != null) {
            return mapper.readValue(redisDataByte, clazz);
        }
        return null;
    }
}