package com.jijy.music.presentation.dto;

import com.jijy.music.persistence.model.ROLE;
import lombok.Data;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

@Data
public class UserDto {
    private String id;

    @NotBlank(message = "El nombre no puede estar vacío.") // El campo no debe ser nulo y debe contener al menos un carácter no espacio en blanco.
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres.") // Longitud mínima y máxima.
    private String name;
    @NotBlank(message = "El email no puede estar vacío.")
    @Email(message = "El formato del email no es válido.") // Valida el formato del correo electrónico.
    @Size(max = 100, message = "El email no puede exceder los 100 caracteres.") // Límite de tamaño para el email.
    private String email;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,30}$",
            message = "La contraseña debe contener al menos 8 caracteres, incluyendo una mayúscula, una minúscula, un número y un carácter especial.")
    private String password;

    @Pattern(regexp = "^\\+?[0-9\\s()-]{7,11}$", message = "El formato del teléfono no es válido.") // Valida el formato del número de teléfono.
    @Size(max = 20, message = "El teléfono no puede exceder los 20 caracteres.")
    private String phone;

    @NotBlank(message = "El nombre de usuario no puede estar vacío.")
    @Size(min = 3, max = 30, message = "El nombre de usuario debe tener entre 3 y 30 caracteres.")
    private String username;

    private ROLE role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
