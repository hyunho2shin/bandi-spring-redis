package com.data.redis.cacheable;

import com.data.redis.dto.Book;
import com.data.redis.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookCacheService {

    private final BookRepository bookRepository;

    @Cacheable(
            value = "book",
            key = "#id",
            // 결과값을 기준으로 캐시에 저장하지 않을 조건 (SpEL). 조건이 true일 경우 캐시에 저장 안 함
            unless = "#result == null"

//            condition: 캐시에 저장할지 여부를 판단하는 조건식 (SpEL). 조건이 true일 때만 캐시 저장
//            condition = "#id > 10"
//            condition = "#id == 'ISBN20392910'"

            // 이 속성이 true로 설정되면, 여러 스레드가 동일한 키로 메서드를 호출할 때, 하나의 스레드만 메서드를 실행하고 나머지 스레드는 대기합니다.
            // 캐시 구현체가 스레드 안전하지 않은 경우, sync=true 설정을 통해 데이터 일관성을 유지할 수 있습니다.
            // 성능 영향:
            //   sync = true를 사용하면 특정 시점에 여러 스레드가 동시에 메서드를 호출할 때 성능 저하가 발생할 수 있습니다.
            //   하지만, 캐시 데이터의 일관성을 유지하는 것이 더 중요할 때 사용됩니다.
            // 활용 예시:
            //     데이터베이스에서 데이터를 조회하는 메서드에 @Cacheable을 적용하고 sync=true를 설정하여,
            //     여러 사용자가 동시에 같은 데이터를 요청할 때 데이터베이스에 대한 부하를 줄이고 일관성을 유지할 수 있습니다.
            // sync = false 기본은 false
    )
    // 캐시가 있는경우 메서드 실행 안됨
    public Book getBook(String id) {
        log.info("getBook 직접 조회 id:{}", id);

        return bookRepository.findById(id);
    }

    @CacheEvict(
            value = "book",
            key = "#id"
    )
    // 메서드가 항상실행 됨
    public void deleteBook(String id) {
        System.out.println("deleteBook 메서드 실행 ");
    }

    // allEntries = true일 경우 book 캐시의 모든 엔트리가 삭제됩니다.
    @CacheEvict(value = "book", allEntries = true)
    public void deleteAllBook() {
        System.out.println("전체 캐시 제거");
    }

    // @Cacheable과 달리, 항상 메서드를 실행하고 캐시를 갱신합니다.
    @CachePut(value = "book", key = "#book.id")
    public Book updateBook(Book book) {
        System.out.println(book.getId() + " 캐시 갱신 : " + book);
        return book;
    }
}
