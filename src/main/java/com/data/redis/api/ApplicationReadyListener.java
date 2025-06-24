package com.data.redis.api;

import com.data.redis.dto.User;
import com.data.redis.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ApplicationReadyListener {

    private final UserRepository userRepository;

    private final RedisTemplate redisTemplate;
    /**
     * 애플리케이션 시작 시 실행되는 초기화 작업
     * @param event ApplicationReadyEvent
     */
//    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady(ApplicationReadyEvent event) {
        String id = "hong3438";

        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            System.out.println("user = " + user);
            System.out.println("user.getId() = " + user.getId());
        }
    }
}

