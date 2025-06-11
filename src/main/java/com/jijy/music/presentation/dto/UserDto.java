package com.jijy.music.presentation.dto;

import com.jijy.music.persistence.model.ROLE;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDto {
    private String id;
    private String name;
    private String email;
    private String password;
    private String phone;
    private String username;
    private ROLE role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
