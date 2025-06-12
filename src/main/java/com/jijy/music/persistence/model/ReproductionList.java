package com.jijy.music.persistence.model;

import com.jijy.music.presentation.dto.ScoreModel;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document
@Data
public class ReproductionList {
    @Id
    private String id;
    private String title;
    private String description;
    private LocalDateTime creationDate;
    private String author;
    private int numSong;
    private List<ScoreModel> score = new ArrayList<>();
    private List<String> genres = new ArrayList<>();
    private List<String> likedBy = new ArrayList<>();
    private List<Music> music = new ArrayList<>();
}
