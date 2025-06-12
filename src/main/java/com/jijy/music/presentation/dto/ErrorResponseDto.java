// src/main/java/com/jijy/music/presentation/dto/ErrorResponseDto.java
package com.jijy.music.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponseDto {
    private int status;
    private String error;
    private String message;
    private Map<String, String> fieldErrors; // Para errores de validación de campos
}