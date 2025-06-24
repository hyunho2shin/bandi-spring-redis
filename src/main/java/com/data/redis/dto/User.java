package com.data.redis.dto;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@RedisHash("userDto")
public class UserDto {
    @Id
    private String userId;

    private String userName;
}
