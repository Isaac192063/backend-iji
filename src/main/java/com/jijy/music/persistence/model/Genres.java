package com.jijy.music.persistence.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Document
@Data
@TypeAlias("Genre")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Genres {
    @Id
    private String id;
    private String name;
    private String description;
    @DBRef
    private List<Music> songs;
}
