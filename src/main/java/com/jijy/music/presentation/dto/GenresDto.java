package com.jijy.music.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class GenresDto {
    private String id;
    @NotBlank(message = "El nombre del género no puede estar vacío.") // El nombre es obligatorio
    @Size(min = 2, max = 50, message = "El nombre del género debe tener entre 2 y 50 caracteres.") // Longitud del nombre
    private String name;

    @NotBlank(message = "La descripción del género no puede estar vacía.") // La descripción es obligatoria
    @Size(min = 10, max = 255, message = "La descripción del género debe tener entre 10 y 255 caracteres.") // Longitud de la descripción
    private String description;
}
