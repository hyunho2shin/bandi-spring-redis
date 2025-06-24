package com.data.redis.repository;

import com.data.redis.dto.User;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.util.CollectionUtils;

import java.util.List;

@SpringBootTest
public class UserRepositorySortTest {
    @Autowired
    private UserRepository userRepository;

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
