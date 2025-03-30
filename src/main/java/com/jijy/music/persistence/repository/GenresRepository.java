package com.jijy.music.persistence.repository;

import com.jijy.music.persistence.model.Genres;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenresRepository extends MongoRepository<Genres, String> {
}
