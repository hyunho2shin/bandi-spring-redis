package com.data.redis.connfactory;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisConnectionUtils;

@SpringBootTest
public class RedisConnectionTest {

    @Autowired
    RedisConnectionFactory redisConnectionFactory;

    @Test
    @DisplayName("RedisConnection으로 redis 조회하기")
    void redisConnectionTest() {
        RedisConnection connection = RedisConnectionUtils.getConnection(redisConnectionFactory);

        String redisKey = "userToken:2139583";
        String value = "465afe70-ea78-4766-ae25-4aa6dbe0a70e";
        connection.stringCommands().set(redisKey.getBytes(), value.getBytes());

        byte[] byteData = connection.stringCommands().get(redisKey.getBytes());
        String data = new String(byteData);

        System.out.println(data);

        // redis 연결이 명시적으로 즉시 종료
        // pool에서 관리중인 연결이 강제로 닫혀 재사용 불가.
        // RedisConnectionUtils에 의해 관리되는 ThreadLocal 연결이 깨짐
//        connection.close();

        // spring에서 제공하는 유틸리티를 이용하여 반환.
        // Redis 연결을 닫지 않고, 안전하게 pool로 반환하여 재사용 또는 트랜잭션 공유가 가능하게 함
        // 특정 작업에서 임시로 가져왔다가 (getConnection()), 작업이 끝나면 다시 컨텍스트에 반환해야 (releaseConnection())
        // 다음 작업에서도 사용할 수 있습니다.
        // Spring은 ThreadLocal을 통해 Redis 연결을 스레드 단위로 저장하고 관리합니다.
        // releaseConnection()을 쓰면 이 컨텍스트가 유지되며, 필요 시 연결을 재사용할 수 있습니다.
        RedisConnectionUtils.releaseConnection(connection, redisConnectionFactory);

    }
}
