package com.jijy.music.services.interfaces;

import com.jijy.music.presentation.dto.GenresDto;

import java.util.List;

public interface GenresService {
    List<GenresDto> getGenres();
    GenresDto getGenreById(String id);
}
