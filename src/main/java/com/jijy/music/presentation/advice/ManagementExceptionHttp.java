package com.jijy.music.presentation.advice;

import com.jijy.music.presentation.dto.ErrorDto;
import com.jijy.music.services.exceptions.BadCredentials;
import com.jijy.music.services.exceptions.BadRequestFailed;
import com.jijy.music.services.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException; // ¡Importa esta clase!
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.validation.FieldError; // Importa FieldError para acceder a los detalles del campo

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors; // Necesario para usar .stream().map().collect(Collectors.toList())

@RestControllerAdvice
// @ControllerAdvice // Esta anotación es redundante cuando ya tienes @RestControllerAdvice
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

    // ¡excepcion para validacion!
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> handleValidationExceptions(MethodArgumentNotValidException ex) {
        // Recoge todos los errores de campo y sus mensajes
        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> {
                    // Formatea el mensaje como "nombreCampo: mensajeDeError"
                    return error.getField() + ": " + error.getDefaultMessage();
                })
                .collect(Collectors.toList());

        return new ResponseEntity<>(
                new ErrorDto(LocalDateTime.now(), errors, false), // `false` asumiendo que indica un fallo
                HttpStatus.BAD_REQUEST
        );
    }
}