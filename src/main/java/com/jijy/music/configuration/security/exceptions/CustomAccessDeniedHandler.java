package com.jijy.music.configuration.security.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jijy.music.presentation.dto.ErrorDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;
import java.util.List;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

        ObjectMapper objectMapper = new ObjectMapper();
        ErrorDto errorDto = new ErrorDto(null, List.of("Usuario no authorizado"), false);

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(errorDto));
    }
}
