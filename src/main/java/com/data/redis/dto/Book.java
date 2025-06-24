package com.data.redis.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
// CSV 파일의 컬럼 순서와 자바 객체의 필드 순서를 일치시켜주는 어노테이션
@JsonPropertyOrder({"id", "bookName", "writer"}) // CSV 컬럼 순서를 명시
public class Book {
    private String id;

    private String bookName;

    private String writer;

//    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String nullField;
}
