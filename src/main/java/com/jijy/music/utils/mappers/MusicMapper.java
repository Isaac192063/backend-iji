package com.jijy.music.utils.mappers;

import com.jijy.music.persistence.model.Genres;
import com.jijy.music.persistence.model.Music;
import com.jijy.music.presentation.dto.GenresDto;
import com.jijy.music.presentation.dto.MusicDto;
import com.jijy.music.presentation.dto.MusicRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MusicMapper {

    MusicMapper INSTANCE = Mappers.getMapper(MusicMapper.class);

    MusicDto musicToMusicDto(Music music);

    @Mapping(target = "genres", ignore = true)
    MusicDto musicToMusicDtoIgnoreGenres(Music music);

    Music musicDtoToMusic(MusicDto musicDto);

    @Mapping(target = "id", ignore = true)
    Music requestMusicDTOToMusic(MusicRequestDTO music);

    GenresDto genresToGenresDto(Genres genres);

}
