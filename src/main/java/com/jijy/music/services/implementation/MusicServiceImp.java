package com.jijy.music.services.implementation;

import com.jijy.music.persistence.model.Genres;
import com.jijy.music.persistence.model.Music;
import com.jijy.music.persistence.repository.GenresRepository;
import com.jijy.music.persistence.repository.MusicRepository;
import com.jijy.music.presentation.dto.GenresDto;
import com.jijy.music.presentation.dto.MusicDto;
import com.jijy.music.presentation.dto.MusicRequestDTO;
import com.jijy.music.services.exceptions.NotFoundException;
import com.jijy.music.services.interfaces.CloudinaryService;
import com.jijy.music.services.interfaces.MusicService;
import com.jijy.music.utils.mappers.MusicMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MusicServiceImp implements MusicService {

    private final MusicRepository musicRepository;
    private final GenresRepository genresRepository;
    private final CloudinaryService cloudinaryService;

    @Override
    public MusicDto addMusic(MusicRequestDTO musicRequestDTO) throws IOException {
        Music music = MusicMapper.INSTANCE.requestMusicDTOToMusic(musicRequestDTO);
        String url = "";
        if(!musicRequestDTO.getSong().isEmpty()){
            url = cloudinaryService.uploadSong(musicRequestDTO.getSong());
        }
        music.setUrl(url);
        Music musicSave = musicRepository.save(music);
        return MusicMapper.INSTANCE.musicToMusicDto(musicSave);
    }

    @Override
    public MusicDto getMusicId(String id) {
        Optional<Music> optionalMusic = musicRepository.findById(id);
        if (optionalMusic.isEmpty()) {
            throw new NotFoundException("Music not found");
        }
        return MusicMapper.INSTANCE.musicToMusicDto(optionalMusic.get());
    }

    @Override
    public MusicDto updateMusic(String id, MusicDto music) {
        MusicDto musicDto = getMusicId(id);

        Music musicUpdate = MusicMapper.INSTANCE.musicDtoToMusic(musicDto);

        musicUpdate.setAlbum(music.getAlbum());
        musicUpdate.setTitle(music.getTitle());
        musicUpdate.setArtist(music.getArtist());
        musicUpdate.setGenres(music.getGenres());

        Music musicSave = musicRepository.save(musicUpdate);

        return MusicMapper.INSTANCE.musicToMusicDto(musicSave);
    }

    @Override
    public List<MusicDto> getAllMusic() {
        return musicRepository.findAll().stream().map(MusicMapper.INSTANCE::musicToMusicDto).toList();
    }

    @Override
    public List<MusicDto> getMusicByFeature(String feature) {
        return List.of();
    }

    @Override
    public MusicDto deleteMusic(String id) throws Exception {
        MusicDto musicDto = getMusicId(id);

        int cancion = musicDto.getUrl().indexOf("canciones");

        if (cancion != -1) {
            String idUrl = musicDto.getUrl().substring(cancion).split("\\.")[0];
            cloudinaryService.deleteSong(idUrl);
        }

        musicRepository.deleteById(id);

        return musicDto;
    }

    @Override
    public List<GenresDto> getAllGenres() {
        return genresRepository.findAll().stream().map(MusicMapper.INSTANCE::genresToGenresDto).toList();
    }
}
