package com.jijy.music.persistence.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document("messages")
public class MessageEntity {
    @Id
    private String id;
    private String message;
    private String author;
    private LocalDateTime date;
    @Indexed
    private String idCommunity;
}
