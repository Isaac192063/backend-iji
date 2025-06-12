package com.jijy.music.presentation.dto;

import com.jijy.music.persistence.model.Genres;
import jakarta.validation.constraints.NotBlank; // Para String no vacíos
import jakarta.validation.constraints.NotNull;  // Para objetos no nulos
import jakarta.validation.constraints.DecimalMin; // Para Double con valor mínimo
import jakarta.validation.constraints.DecimalMax; // Para Double con valor máximo

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class MusicRequestDTO {

    @NotNull(message = "Se requiere un archivo de canción.")
    private MultipartFile song;
    @NotBlank(message = "El artista no puede estar vacío.")
    private String artist;
    private String album; // Considerado opcional, sin @NotBlank
    @NotBlank(message = "El título no puede estar vacío.")
    private String title;
    private Double duration;
    @NotNull(message = "Debe seleccionar al menos un género.")
    private List<Genres> genres;
}
