package com.data.redis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@RedisHash(value = "user", timeToLive = 3600L)
@RedisHash(value = "{user}")
public class User {
    @Id
    private String id;

//    @Indexed
    private String name;

    @TimeToLive
    @Builder.Default
    private Long expiration = 3600l;

    @Indexed
    private String dept;
}
