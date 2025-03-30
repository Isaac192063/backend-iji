package com.jijy.music.presentation.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorDto(
        LocalDateTime date,
        List<String> message,
        boolean success
) {
}
