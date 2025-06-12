package com.jijy.music.persistence.repository;

import com.jijy.music.persistence.model.ReproductionList;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReproductionListRepository extends MongoRepository<ReproductionList, String> {
    List<ReproductionList> findByAuthor(String author);
}
