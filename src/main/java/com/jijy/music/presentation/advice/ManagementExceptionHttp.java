package com.jijy.music.presentation.advice;

import com.jijy.music.presentation.dto.ErrorDto;
import com.jijy.music.services.exceptions.BadCredentials;
import com.jijy.music.services.exceptions.BadRequestFailed;
import com.jijy.music.services.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
@ControllerAdvice
public class ManagementExceptionHttp {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ErrorDto> exception(MaxUploadSizeExceededException exception) {
        return new ResponseEntity<>(
                new ErrorDto(LocalDateTime.now(), List.of("Excede el maximo de tamaño"), false),
                HttpStatus.PAYLOAD_TOO_LARGE
        );
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorDto> notFoundException(NotFoundException exception) {
        return new ResponseEntity<>(
                new ErrorDto(LocalDateTime.now(), List.of(exception.getMessage()), false),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(BadCredentials.class)
    public ResponseEntity<ErrorDto> badCredentialException(BadCredentials exception) {
        return new ResponseEntity<>(
                new ErrorDto(LocalDateTime.now(), List.of(exception.getMessage()), false),
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(BadRequestFailed.class)
    public ResponseEntity<ErrorDto> badRequestFailedException(BadRequestFailed exception) {
        return new ResponseEntity<>(
                new ErrorDto(LocalDateTime.now(), List.of(exception.getMessage()), false),
                HttpStatus.BAD_REQUEST
        );
    }
}
