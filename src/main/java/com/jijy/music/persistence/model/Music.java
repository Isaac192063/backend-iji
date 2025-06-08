package com.jijy.music.persistence.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Data
public class Music {
    @Id
    private String id;
    private String url;
    private String title;
    private String artist;
    private String album;
    private Double duration;
    @DBRef
    private List<Genres> genres;

}
