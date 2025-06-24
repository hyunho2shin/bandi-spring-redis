package com.data.redis.repository;

import com.data.redis.dto.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

//    @Test
    @DisplayName("Repository를 이용하여 User 저장하기")
    void userRepositorySetTest() {
        User user = User.builder()
                .id("hong3438")
                .name("홍길동")
                .expiration(-1l)
                .dept("영업부")
                .build();
        userRepository.save(user);

        /*user = User.builder()
                .id("zang4938")
                .name("장보고")
//                .dept("영업부")
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
    @DisplayName("@Indexed 보조 인덱스로 name으로 조회하기")
    void userRepositoryFindByNameTest() {
        String name = "홍길동";

        List<User> userList = userRepository.findByName(name);

        if (!CollectionUtils.isEmpty(userList)) {
            System.out.println("userList = " + userList);
        }
    }

//    @Test
    @DisplayName("@Indexed 보조 인덱스로 name과 dept로 and 조건 조회하기")
    void userRepositoryfindByNameAndAlias() {
        String name = "홍길동";
        String dept = "기획부";

        List<User> userList = userRepository.findByNameAndDept(name, dept);

        if (!CollectionUtils.isEmpty(userList)) {
            System.out.println("userList = " + userList);
        }
    }

//    @Test
    @DisplayName("조회시 정렬 기능 이용하기")
    void userRepositorySorting() {
        String dept = "영업부";

        Sort sort = Sort.by("name").ascending();

//        List<User> userList = userRepository.findByDeptOrderByNameDesc(dept);
//        List<User> userList = userRepository.findByDeptOrderByNameAsc(dept);
        List<User> userList = userRepository.findByDept(dept, sort);

        if (!CollectionUtils.isEmpty(userList)) {
            System.out.println("userList = " + userList);
        }
    }
}
