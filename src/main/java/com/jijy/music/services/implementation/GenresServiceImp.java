package com.jijy.music.services.implementation;

import com.jijy.music.persistence.model.Genres;
import com.jijy.music.persistence.repository.GenresRepository;
import com.jijy.music.presentation.dto.GenresDto;
import com.jijy.music.services.exceptions.NotFoundException;
import com.jijy.music.services.interfaces.GenresService;
import com.jijy.music.utils.mappers.MusicMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenresServiceImp implements GenresService {

    private final GenresRepository genresRepository;

    @Override
    public List<GenresDto> getGenres() {
        return genresRepository.findAll().stream().map(MusicMapper.INSTANCE::genresToGenresDto).toList();
    }

    @Override
    public GenresDto getGenreById(String id) {

        Optional<Genres> genres = genresRepository.findById(id);

        if(genres.isEmpty()){
            throw new NotFoundException("No se encontro el genero");
        }
        return MusicMapper.INSTANCE.genresToGenresDto(genres.get());
    }
}
