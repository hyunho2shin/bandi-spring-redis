package com.data.redis.repository;

import com.data.redis.dto.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, String> {
//public interface UserRepository extends ListCrudRepository<User, String> {
    List<User> findByDept(String dept);
    List<User> findByNameAndDept(String name, String dept); // sInter (교집합)
    List<User> findByNameOrDept(String name, String dept); // sUnion (합집합)

    // Sorting(정렬)
    // 1. Static sorting derived from method name. -- 메서드이름으로 명시적 정렬
    List<User> findByDeptOrderByNameDesc(String dept);
    List<User> findByDeptOrderByNameAsc(String dept);

    // 2. Dynamic sorting using a method argument. -- argument 기반 동적 정렬.
    List<User> findByDept(String dept, Sort sort);

}