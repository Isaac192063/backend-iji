package com.jijy.music.presentation.dto;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class AuthorPlaylistCount {

    @Field("_id")
    private String author;
    private int totalPlaylists;
}
