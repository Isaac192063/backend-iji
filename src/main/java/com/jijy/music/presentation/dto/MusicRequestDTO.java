package com.jijy.music.presentation.dto;

import com.jijy.music.persistence.model.Genres;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class MusicRequestDTO {
    private MultipartFile song;
    private String artist;
    private String album;
    private String title;
    private List<Genres> genres;
}
