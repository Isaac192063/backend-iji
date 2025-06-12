package com.jijy.music.presentation.controller;

import com.jijy.music.persistence.model.Music;
import com.jijy.music.persistence.model.ReproductionList;
import com.jijy.music.presentation.dto.AuthorPlaylistCount;
import com.jijy.music.presentation.dto.ScoreReproductionListDTO;
import com.jijy.music.services.implementation.ReproductionListAggregationService;
import com.jijy.music.services.interfaces.ReproductionListService;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/reproduction-list")
@RequiredArgsConstructor
public class ReproductionListController {

    private final ReproductionListService reproductionListService;
    private final ReproductionListAggregationService aggregationService;

    @GetMapping("top-5-popular")
    public ResponseEntity<List<Document>> top5PopularLists() {
        return ResponseEntity.ok(aggregationService.top5PopularLists());
    }

    @GetMapping("count-by-author")
    public ResponseEntity<List<AuthorPlaylistCount>> countByAuthor() {
        return ResponseEntity.ok(aggregationService.countPlaylistsByAuthor());
    }

    @GetMapping("paginate")
    public ResponseEntity<List<Document>> paginate(@RequestParam(defaultValue = "1") int page) {
        return ResponseEntity.ok(aggregationService.paginateLists(page));
    }

    @GetMapping("")
    public ResponseEntity<List<ReproductionList>> getAllLists() {
        return ResponseEntity.ok(reproductionListService.getAllReproductionLists());
    }

    @GetMapping("me")
    public ResponseEntity<List<ReproductionList>> getListByMe() {
        return ResponseEntity.ok(reproductionListService.getReproductionListByMe());
    }

    @GetMapping("{id}")
    public ResponseEntity<ReproductionList> getListById(@PathVariable String id) {
        return ResponseEntity.ok(reproductionListService.getReproductionListById(id));
    }

    @PostMapping
    public ResponseEntity<ReproductionList> createList(@RequestBody ReproductionList list) {
        return ResponseEntity.ok(reproductionListService.createReproductionList(list));
    }

    @PutMapping("{id}")
    public ResponseEntity<ReproductionList> updateList(@PathVariable String id, @RequestBody ReproductionList list) {
        return ResponseEntity.ok(reproductionListService.updateReproductionList(id, list));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ReproductionList> deleteList(@PathVariable String id) {
        return ResponseEntity.ok(reproductionListService.deleteReproductionList(id));
    }

    @GetMapping("{id}/music")
    public ResponseEntity<List<Music>> getMusicByList(@PathVariable String id) {
        return ResponseEntity.ok(reproductionListService.getListMusicByReproductionList(id));
    }

    @GetMapping("genres/{id}")
    public ResponseEntity<List<ReproductionList>> getMusicByName(@PathVariable String id) {
        return ResponseEntity.ok(reproductionListService.getListReproductionListByGenre(id));
    }

    @PostMapping("{id}/like")
    public ResponseEntity<ReproductionList> toLike(@PathVariable String id) {
        return ResponseEntity.ok(reproductionListService.likeToReproductionList(id));
    }

    @PostMapping("{id}/score")
    public ResponseEntity<ReproductionList> toScore(@PathVariable String id, @RequestBody ScoreReproductionListDTO scoreReproductionListDTO) {
        return ResponseEntity.ok(reproductionListService.scoreToReproductionList(id, scoreReproductionListDTO));
    }

}
