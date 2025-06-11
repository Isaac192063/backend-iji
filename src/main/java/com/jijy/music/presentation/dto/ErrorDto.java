package com.jijy.music.presentation.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorDto(
        LocalDateTime date,
        List<String> message,
        boolean success
) {
}
