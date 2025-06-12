package com.jijy.music.presentation.controller;

import com.jijy.music.persistence.model.CommunityEntity;
import com.jijy.music.services.interfaces.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/community")
@RequiredArgsConstructor
public class ComunityController {

    private final CommunityService communityService;

    @PostMapping
    public ResponseEntity<CommunityEntity> createCommunity(@RequestBody CommunityEntity community) {
        System.out.println(community);
        return new ResponseEntity<>(communityService.addCommunity(community), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CommunityEntity>> getAllCommunities() {
        return ResponseEntity.ok(communityService.getAllCommunities());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommunityEntity> getCommunityById(@PathVariable String id) {
        return ResponseEntity.ok(communityService.getCommunityById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommunityEntity> updateCommunity(@PathVariable String id, @RequestBody CommunityEntity updated) {
        updated.setId(id);
        CommunityEntity result = communityService.updateCommunity(updated);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCommunity(@PathVariable String id) {
        communityService.deleteCommunity(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<CommunityEntity> getCommunityByName(@PathVariable String name) {
        CommunityEntity result = communityService.getCommunityByName(name);
        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/author/{name}")
    public ResponseEntity<List<CommunityEntity>> getAllByAuthor(@PathVariable String name) {
        return ResponseEntity.ok(communityService.getAllCommunitiesByAuthor(name));
    }
}