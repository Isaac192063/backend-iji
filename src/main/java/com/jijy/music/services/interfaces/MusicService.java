package com.jijy.music.services.interfaces;

import com.jijy.music.presentation.dto.GenresDto;
import com.jijy.music.presentation.dto.MusicDto;
import com.jijy.music.presentation.dto.MusicRequestDTO;

import java.io.IOException;
import java.util.List;

public interface MusicService {
    MusicDto addMusic(MusicRequestDTO music) throws IOException;
    MusicDto getMusicId(String id);
    MusicDto updateMusic(String id, MusicDto music) ;
    List<MusicDto> getAllMusic();

    List<MusicDto> getMusicByFeature(String feature);
    MusicDto deleteMusic(String id) throws Exception;
    List<GenresDto> getAllGenres();
}
