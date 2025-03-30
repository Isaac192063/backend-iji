package com.jijy.music.presentation.dto;

import com.jijy.music.persistence.model.Genres;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MusicDto {
    private String id;
    private String title;
    private String url;
    private String artist;
    private String album;
    private List<Genres> genres;
}
