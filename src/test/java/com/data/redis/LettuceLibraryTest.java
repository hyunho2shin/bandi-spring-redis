package com.data.redis;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


/*
 * Lettuce library를 직접 사용하여 redis 연동하기
 */
@SpringBootTest
public class LettuceLibraryTest {

    @Test
    @DisplayName("Lettuce library를 직접 사용하하고 close도 수동으로 닫아주기")
    void lettuceRedisClient() {
        RedisClient redisClient = RedisClient.create("redis://localhost:6379/0");

        // 싱글 노드 연결.
        StatefulRedisConnection<String, String> connection = redisClient.connect();

        // 동기 방식 Redis 명령어 실행 API 얻기
        RedisCommands<String, String> redisCommand = connection.sync();

        System.out.println("Connected to Redis");

        String redisKey = "TEST:name";
        String value = "testValue";
//        redisCommand.set(redisKey, value);

        String data = redisCommand.get(redisKey);
        System.out.println(data);

        // 연결 객체 종료 (실제 Redis 연결 해제 (소켓/풀 반납))
        // StatefulRedisConnection 객체는 Redis 서버와의 실제 네트워크 연결(socket)을 유지하고 있습니다.
        // close()를 호출하면 이 연결을 닫고 풀(pool)에서 반환하거나 자원을 해제합니다.
        // 자원을 명시적으로 반납하지 않으면 연결이 계속 유지되어 메모리 낭비 또는 소켓 누수가 발생합니다.
        // 특히 연결 수 제한이 있는 Redis 서버에서는 사용한 연결을 닫지 않으면 다음 연결 요청이 실패할 수 있습니다.
        // connection.close()를 호출하지 않으면 GC(Garbage Collector)가 수거할 때까지 커넥션이 유지됨.
        connection.close();

        // 클라이언트 자원 해제 (내부 리소스(Netty, 스레드) 종료)
        // RedisClient는 내부적으로 여러 스레드와 이벤트 루프(Netty 기반)를 생성해서 Redis 명령을 처리합니다.
        // shutdown()은 이 백그라운드 리소스(thread, executor 등)를 안전하게 종료시킵니다.
        // 호출하지 않으면, 프로그램이 종료되지 않거나 (특히 Java 애플리케이션 종료 안 됨)
        // Netty 이벤트 루프가 계속 살아 있으면서 메모리 누수 및 스레드 리소스 낭비 발생.
        // 예를 들어 테스트 코드를 반복 실행할 때, shutdown 없이 계속 RedisClient를 만들면 리소스가 누적되어 성능 저하 발생 가능.
        // 특히 독립 실행형 애플리케이션 또는 단위 테스트에서 꼭 해주는 것이 안전.
        redisClient.shutdown();

        // RedisClient는 재사용하고, connection만 풀링하여 관리하는 것이 일반적인 패턴이에요.
    }

//    @Test
    @DisplayName("try-with-resources 패턴으로 조회하기" +
            "AutoCloseable를 implements하고 있으므로 close()함수가 자동으로 호출됨"
            )
    void lettuceRedisClient2() {
        RedisClient redisClient = RedisClient.create("redis://localhost:6379/0");

        // Lettuce의 StatefulRedisConnection은 AutoCloseable이므로 다음과 같이 사용하면 안전합니다.
        // try-with-resources 패턴
        try (StatefulRedisConnection<String, String> connection = redisClient.connect()) {
            RedisCommands<String, String> redisCommand = connection.sync();

            String redisKey = "TEST:name";
            String value = "testValue";

            String data = redisCommand.get(redisKey);
            System.out.println(data);
        }
        redisClient.shutdown();
    }
}
