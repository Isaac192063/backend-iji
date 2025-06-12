package com.jijy.music.presentation.dto;

import com.jijy.music.persistence.model.Genres;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MusicDto {
    private String id;
    @NotNull(message = "Se requiere un archivo de canción.")
    private MultipartFile song;
    private String url;
    @NotBlank(message = "El artista no puede estar vacío.")
    private String artist;
    private String album; // Considerado opcional, sin @NotBlank
    @NotBlank(message = "El título no puede estar vacío.")
    private String title;
    private Double duration;
    private List<Genres> genres;
}
