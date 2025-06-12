package com.jijy.music.presentation.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "chat_messages")
public class TextMessageDTO {

    @Id
    private String id;
    private String message;
    private int senderId;
    private String comunityId;
    private LocalDateTime timestamp;


    public TextMessageDTO() {
    }


    public TextMessageDTO(String message, int senderId, String comunityId, LocalDateTime timestamp) {
        this.message = message;
        this.senderId = senderId;
        this.comunityId = comunityId;
        this.timestamp = timestamp;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public String getComunityId() {
        return comunityId;
    }

    public void setComunityId(String comunityId) {
        this.comunityId = comunityId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "TextMessageDTO{" +
                "id='" + id + '\'' +
                ", message='" + message + '\'' +
                ", senderId=" + senderId +
                ", comunityId='" + comunityId + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}