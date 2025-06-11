package com.jijy.music.services.interfaces;

import com.jijy.music.persistence.model.CommunityEntity;

import java.util.List;

public interface CommunityService {
    CommunityEntity addCommunity(CommunityEntity community);
    List<CommunityEntity> getAllCommunities();
    CommunityEntity getCommunityById(String id);
    CommunityEntity updateCommunity(CommunityEntity community);
    void deleteCommunity(String id);
    CommunityEntity getCommunityByName(String name);
    List<CommunityEntity> getAllCommunitiesByAuthor(String name);
}
