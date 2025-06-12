package com.jijy.music.services.implementation;

import com.jijy.music.persistence.model.CommunityEntity;
import com.jijy.music.persistence.repository.CommunityRepository;
import com.jijy.music.services.exceptions.NotFoundException;
import com.jijy.music.services.interfaces.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommunityServiceImp implements CommunityService {


    private final CommunityRepository communityRepository;

    @Override
    public CommunityEntity addCommunity(CommunityEntity community) {
        return communityRepository.save(community);
    }

    @Override
    public List<CommunityEntity> getAllCommunities() {
        return communityRepository.findAll();
    }

    @Override
    public CommunityEntity getCommunityById(String id) {
        Optional<CommunityEntity> communityEntity = communityRepository.findById(id);
        if(communityEntity.isEmpty()){
            throw new NotFoundException("No se encontro la comunidad");
        }
        return communityEntity.get();
    }

    @Override
    public CommunityEntity updateCommunity(CommunityEntity community) {
        System.out.println("Intentando actualizar comunidad con ID: " + community.getId());
        Optional<CommunityEntity> existingOpt = communityRepository.findById(community.getId());

        if (existingOpt.isEmpty()) {
            System.out.println("No se encontró la comunidad.");
            throw new NotFoundException("No se encontró la comunidad con ID: " + community.getId());
        }

        CommunityEntity existing = existingOpt.get();

        // Actualizamos solo los campos que el usuario puede modificar
        existing.setName(community.getName());
        existing.setDescription(community.getDescription());
        existing.setPrivateCommunity(community.isPrivateCommunity());
        existing.setTags(community.getTags());
        existing.setMaxMembers(community.getMaxMembers());
        existing.setRules(community.getRules());


        CommunityEntity saved = communityRepository.save(existing);
        System.out.println("Comunidad actualizada: " + saved);
        return saved;
    }


    @Override
    public void deleteCommunity(String id) {
        CommunityEntity community = getCommunityById(id);
        communityRepository.delete(community);
    }

    @Override
    public CommunityEntity getCommunityByName(String name) {
        return null;
    }

    @Override
    public List<CommunityEntity> getAllCommunitiesByAuthor(String name) {
        return List.of();
    }
}
