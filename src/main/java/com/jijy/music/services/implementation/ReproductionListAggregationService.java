package com.jijy.music.services.implementation;


import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReproductionListAggregationService {

    private final MongoTemplate mongoTemplate;

    // 1. Agrupar por autor y contar listas
    public List<Document> countPlaylistsByAuthor() {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.group("author").count().as("totalPlaylists")
        );
        return mongoTemplate.aggregate(aggregation, "reproductionList", Document.class).getMappedResults();
    }

    // 2. Mostrar solo título y número de canciones
    public List<Document> getTitlesAndSongCounts() {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.project("title", "numSong")
        );
        return mongoTemplate.aggregate(aggregation, "reproductionList", Document.class).getMappedResults();
    }

    // 3. Ordenar por cantidad de likes (likedBy.length)
    public List<Document> sortByLikes() {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.project("title", "likedBy")
                        .and("likedBy").size().as("likeCount"),
                Aggregation.sort(Sort.by(Sort.Direction.DESC, "likeCount"))
        );
        return mongoTemplate.aggregate(aggregation, "reproductionList", Document.class).getMappedResults();
    }

    // 4. Filtrar listas que contengan el género "Rock"
    public List<Document> filterByGenreRock() {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("genres").in("Rock"))
        );
        return mongoTemplate.aggregate(aggregation, "reproductionList", Document.class).getMappedResults();
    }

    // 5. Mostrar solo las 5 listas más populares (por likes)
    public List<Document> top5PopularLists() {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.project("title", "likedBy")
                        .and("likedBy").size().as("likeCount"),
                Aggregation.sort(Sort.by(Sort.Direction.DESC, "likeCount")),
                Aggregation.limit(5)
        );
        return mongoTemplate.aggregate(aggregation, "reproductionList", Document.class).getMappedResults();
    }

    // 6. Paginación: omitir las primeras N y mostrar 5
    public List<Document> paginateLists(int page) {
        long skip = (long) (page - 1) * 5;
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.skip(skip),
                Aggregation.limit(5)
        );
        return mongoTemplate.aggregate(aggregation, "reproductionList", Document.class).getMappedResults();
    }

    // 7. Unwind de canciones (music)
    public List<Document> unwindSongs() {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.unwind("music")
        );
        return mongoTemplate.aggregate(aggregation, "reproductionList", Document.class).getMappedResults();
    }

    // 8. Lookup de géneros de canciones (si están en otra colección)
    public List<Document> lookupGenresFromSongs() {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.unwind("music"),
                Aggregation.lookup("genres", "music.genres", "_id", "music.fullGenres")
        );
        return mongoTemplate.aggregate(aggregation, "reproductionList", Document.class).getMappedResults();
    }
}