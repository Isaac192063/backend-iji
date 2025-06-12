package com.jijy.music.presentation.controller;



import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Modelo que representa un mensaje de chat en el sistema WebSocket
 * Compatible con el formato JSON enviado desde el cliente JavaScript
 */
public class ChatMessage {

    private String id;
    private String message;
    private String sender;
    private String senderId;
    private MessageType type;
    private String timestamp;

    @JsonProperty("comunityId") // Mapea el campo del JSON que viene del cliente
    private String communityId;

    // Enum para los tipos de mensaje
    public enum MessageType {
        CHAT,   // Mensaje normal de chat
        JOIN,   // Usuario se une al chat
        LEAVE   // Usuario abandona el chat
    }

    // Constructor vacío (requerido para deserialización JSON)
    public ChatMessage() {
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    // Constructor con parámetros básicos
    public ChatMessage(String message, String sender, MessageType type) {
        this();
        this.message = message;
        this.sender = sender;
        this.type = type;
        this.id = generateId();
    }

    // Constructor completo
    public ChatMessage(String id, String message, String sender, String senderId,
                       MessageType type, String communityId) {
        this();
        this.id = id != null ? id : generateId();
        this.message = message;
        this.sender = sender;
        this.senderId = senderId;
        this.type = type;
        this.communityId = communityId;
    }

    // Método para generar ID único
    private String generateId() {
        return String.valueOf(System.currentTimeMillis());
    }

    // Getters y Setters
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

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    // Getter adicional para compatibilidad con el JSON del cliente (comunityId)
    @JsonProperty("comunityId")
    public String getComunityId() {
        return this.communityId;
    }

    // Setter adicional para compatibilidad con el JSON del cliente (comunityId)
    @JsonProperty("comunityId")
    public void setComunityId(String comunityId) {
        this.communityId = comunityId;
    }

    // Métodos de utilidad
    public boolean isSystemMessage() {
        return type == MessageType.JOIN || type == MessageType.LEAVE;
    }

    public boolean isChatMessage() {
        return type == MessageType.CHAT;
    }

    // Método para validar si el mensaje es válido
    public boolean isValid() {
        return message != null && !message.trim().isEmpty()
                && sender != null && !sender.trim().isEmpty()
                && type != null;
    }

    // Método para crear un mensaje de JOIN
    public static ChatMessage createJoinMessage(String username, String communityId) {
        ChatMessage message = new ChatMessage();
        message.setSender(username);
        message.setType(MessageType.JOIN);
        message.setMessage(username + " se ha unido al chat");
        message.setCommunityId(communityId);
        message.setId(message.generateId());
        return message;
    }

    // Método para crear un mensaje de LEAVE
    public static ChatMessage createLeaveMessage(String username, String communityId) {
        ChatMessage message = new ChatMessage();
        message.setSender(username);
        message.setType(MessageType.LEAVE);
        message.setMessage(username + " ha abandonado el chat");
        message.setCommunityId(communityId);
        message.setId(message.generateId());
        return message;
    }

    // Método para crear un mensaje de chat normal
    public static ChatMessage createChatMessage(String messageText, String username,
                                                String userId, String communityId) {
        ChatMessage message = new ChatMessage();
        message.setMessage(messageText);
        message.setSender(username);
        message.setSenderId(userId);
        message.setType(MessageType.CHAT);
        message.setCommunityId(communityId);
        message.setId(message.generateId());
        return message;
    }

    // Métodos equals y hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatMessage that = (ChatMessage) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(message, that.message) &&
                Objects.equals(sender, that.sender) &&
                Objects.equals(senderId, that.senderId) &&
                type == that.type &&
                Objects.equals(timestamp, that.timestamp) &&
                Objects.equals(communityId, that.communityId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, message, sender, senderId, type, timestamp, communityId);
    }

    // Método toString para logging y debugging
    @Override
    public String toString() {
        return "ChatMessage{" +
                "id='" + id + '\'' +
                ", message='" + message + '\'' +
                ", sender='" + sender + '\'' +
                ", senderId='" + senderId + '\'' +
                ", type=" + type +
                ", timestamp='" + timestamp + '\'' +
                ", communityId='" + communityId + '\'' +
                '}';
    }

    // Método para obtener una representación JSON-like del objeto
    public String toJsonString() {
        return String.format(
                "{\"id\":\"%s\",\"message\":\"%s\",\"sender\":\"%s\",\"senderId\":\"%s\"," +
                        "\"type\":\"%s\",\"timestamp\":\"%s\",\"comunityId\":\"%s\"}",
                id, message, sender, senderId, type, timestamp, communityId
        );
    }
}



