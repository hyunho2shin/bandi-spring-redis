package com.data.redis.repository;

import com.data.redis.dto.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;

import java.util.List;

@SpringBootTest
public class UserRepositoryIndexedTest {
    @Autowired
    private UserRepository userRepository;

//    @Test
    @DisplayName("@Indexed 보조 인덱스로 dept로 조회하기")
    void userRepositoryFindByDeptTest() {
        String dept = "영업부";

        List<User> userList = userRepository.findByDept(dept);

//        if (!CollectionUtils.isEmpty(userList)) {
            System.out.println("userList.size() = " + userList.size());
            System.out.println("userList = " + userList);
//        }
    }

    @Test
    @DisplayName("@Indexed 보조 인덱스로 name과 dept로 and 조건 조회하기")
    void userRepositoryfindByNameAndAlias() {
        String name = "홍길동";
        String dept = "영업부";

        List<User> userList = userRepository.findByNameAndDept(name, dept);

        System.out.println("userList = " + userList);
    }
}
