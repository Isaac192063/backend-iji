package com.jijy.music.persistence.repository;

import com.jijy.music.persistence.model.CommunityEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityRepository extends MongoRepository<CommunityEntity, String> {
}
