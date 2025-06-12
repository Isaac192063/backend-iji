package com.jijy.music.presentation.controller;

import com.jijy.music.presentation.dto.AuthorPlaylistCount;
import com.jijy.music.services.implementation.ReproductionListAggregationService;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/aggregations")
@RequiredArgsConstructor
public class ReproductionListAggregationController {
    private final ReproductionListAggregationService aggregationService;


    // 3. Listas ordenadas por likes
    @GetMapping("/sort-by-likes")
    public ResponseEntity<List<Document>> sortByLikes() {
        return ResponseEntity.ok(aggregationService.sortByLikes());
    }

    // 4. Filtrar listas por género "Rock"
    @GetMapping("/filter-by-genre")
    public ResponseEntity<List<Document>> filterByGenre(@RequestParam(defaultValue = "Rock") String genre) {
        return ResponseEntity.ok(aggregationService.filterByGenreRock(genre));
    }

    // 5. Top 5 listas populares
    @GetMapping("/top-5-popular")
    public ResponseEntity<List<Document>> top5PopularLists() {
        return ResponseEntity.ok(aggregationService.top5PopularLists());
    }

    // 6. Paginación (5 por página)
    @GetMapping("/paginate")
    public ResponseEntity<List<Document>> paginate(@RequestParam(defaultValue = "1") int page) {
        return ResponseEntity.ok(aggregationService.paginateLists(page));
    }

    // 7. Unwind de canciones
    @GetMapping("/unwind-songs")
    public ResponseEntity<List<Document>> unwindSongs() {
        return ResponseEntity.ok(aggregationService.unwindSongs());
    }

    // 8. Lookup de géneros desde canciones
    @GetMapping("/lookup-genres")
    public ResponseEntity<List<Document>> lookupGenresFromSongs() {
        return ResponseEntity.ok(aggregationService.lookupGenresFromSongs());
    }
}
