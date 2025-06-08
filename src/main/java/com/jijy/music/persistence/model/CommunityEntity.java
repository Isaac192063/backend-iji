package com.jijy.music.persistence.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document("community")
public class CommunityEntity {
    @Id
    private String id;
    private int maxMembers;
    private String rules;
    private List<String> tags;
    private boolean privateCommunity;
    private String name;
    private String description;
}
