package com.data.redis.repository;

import com.data.redis.dto.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Repository를 이용하여 User 저장하기")
    void userRepositorySetTest() {
        User user = User.builder()
                .id("hong3438")
                .name("홍길동")
//                .expiration(60l)
                .dept("영업부")
                .build();
        userRepository.save(user);

        /*user = User.builder()
                .id("zang4938")
                .name("장보고")
                .dept("영업부")
                .build();
        userRepository.save(user);*/

        /*
        user = User.builder()
                .id("moon4958")
                .name("문무왕")
                .dept("영업부")
                .build();
        userRepository.save(user);*/
    }

//    @Test
    @DisplayName("Repository를 이용하여 User 조회하기")
    void userRepositoryGetTest() {
        String id = "hong3438";

//        boolean existsFlag = userRepository2.existsById(id);

        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            System.out.println("user = " + user);
        }

//        System.out.println("existsFlag = " + existsFlag);
    }

//    @Test
    @DisplayName("Repository를 이용하여 User 수정하기")
    void userRepositoryUpdateTest() {
        String id = "hong3438";

        Optional<User> optionalUser = userRepository.findById(id);

        if (!optionalUser.isEmpty()) {
            User user = optionalUser.get();

            user.setName("장보고");

            userRepository.save(user);
            System.out.println("user = " + user);
        }
    }

//    @Test
    @DisplayName("Repository를 이용하여 User 삭제하기")
    void userRepositoryDelTest() {
        String id = "hong3438";

        userRepository.deleteById(id);
    }
}
