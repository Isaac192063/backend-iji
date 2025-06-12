package com.jijy.music.presentation.dto;

import com.jijy.music.persistence.model.Genres;

import java.util.List;

public class MusicDtoWithGenres {
    private String id;
    private String title;
    private String url;
    private String artist;
    private String album;
    private List<Genres> genres;
}
