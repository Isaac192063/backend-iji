package com.jijy.music.presentation.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ReproductionDto {
    private String title;
    private String description;
    private LocalDateTime creationDate;
    private String idUser;
    private int numSong;
    private List<ScoreModel> score;
    private List<String> genres;
    private List<String> likedBy;
    private List<MusicDto> musicDtos;
    private UserDto userInfo;
}
