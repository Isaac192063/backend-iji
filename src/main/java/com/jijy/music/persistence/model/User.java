package com.jijy.music.persistence.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "Users")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
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
