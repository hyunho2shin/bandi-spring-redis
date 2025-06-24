package com.data.redis.cacheable;

import com.data.redis.dto.Book;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CacheableTest {

    @Autowired
    BookCacheService bookCacheService;

//    @Test
    @DisplayName("CacheManger를 이용한 Cache 조회하기")
    void BookCacheableTest() {
        String id = "ISBN20392910";
        Book book = bookCacheService.getBook(id);
        System.out.println("book = " + book);

        id = "ISBN20392911";
        book = bookCacheService.getBook(id);
        System.out.println("book = " + book);
    }

//    @Test
    @DisplayName("CacheManger를 이용하여 Cache 삭제하기")
    void BookCacheDeleteTest() {
        String id = "ISBN20392910";

        bookCacheService.deleteBook(id);
        System.out.println("삭제 id = " + id);
    }

//    @Test
    @DisplayName("CacheManger를 이용하여 book Cache 전체 삭제하기")
    void BookCacheAllDeleteTest() {
        bookCacheService.deleteAllBook();
    }

    @Test
    @DisplayName("CacheManger를 이용하여 book Cache 갱신하기")
    void BookCacheUpdateTest() {
        String id = "ISBN20392911";
        Book book = bookCacheService.getBook(id);

        book.setBookName("안나 카레니나"); // 내용 변경.
        bookCacheService.updateBook(book);
    }
}
