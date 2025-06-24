package com.data.redis.repository;

import com.data.redis.dto.Book;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

@Repository
public class BookRepository {

    // CSV 데이터를 로드하여 담아둘 리스트
    private List<Book> bookList = Collections.emptyList();

    /**
     * @PostConstruct: 의존성 주입이 완료된 후 초기화를 위해 실행되는 메서드.
     * 애플리케이션 시작 시 한 번만 CSV 파일을 읽어 메모리에 로드합니다.
     */
    @PostConstruct
    public void init() {
        try {
            // CsvMapper: CSV 데이터를 객체로 변환해주는 Jackson의 핵심 클래스
            CsvMapper csvMapper = new CsvMapper();
            // CsvSchema: CSV 파일의 헤더를 사용하여 객체 필드와 매핑하도록 설정
            CsvSchema schema = CsvSchema.emptySchema().withHeader();

            // src/main/resources 경로의 파일을 읽어옵니다.
            ClassPathResource resource = new ClassPathResource("csv/book.csv");
            InputStream inputStream = resource.getInputStream();

            // CSV 파일을 읽어서 Course 객체의 리스트로 변환
            MappingIterator<Book> it = csvMapper.readerFor(Book.class)
                    .with(schema)
                    .readValues(inputStream);

            this.bookList = it.readAll();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Book findById(String id) {
        return bookList.stream()
                .filter(book -> book.getId().equals(id))
                .findFirst()
                .orElse(new Book());
    }
}