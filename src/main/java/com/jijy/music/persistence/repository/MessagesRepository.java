package com.jijy.music.persistence.repository;

import com.jijy.music.persistence.model.MessageEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessagesRepository extends MongoRepository<MessageEntity, String> {
}
