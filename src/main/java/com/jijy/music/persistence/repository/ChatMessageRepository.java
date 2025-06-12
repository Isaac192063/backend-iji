package com.jijy.music.persistence.repository;

import com.jijy.music.presentation.dto.TextMessageDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface ChatMessageRepository extends MongoRepository<TextMessageDTO, String> {
    List<TextMessageDTO> findByComunityIdOrderByTimestampAsc(String comunityId);
}
