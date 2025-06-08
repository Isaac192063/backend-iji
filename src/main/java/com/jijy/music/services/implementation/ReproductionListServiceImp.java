package com.jijy.music.services.implementation;

import com.jijy.music.persistence.model.Music;
import com.jijy.music.persistence.model.ReproductionList;
import com.jijy.music.persistence.repository.MusicRepository;
import com.jijy.music.persistence.repository.ReproductionListRepository;
import com.jijy.music.presentation.dto.ScoreModel;
import com.jijy.music.presentation.dto.ScoreReproductionListDTO;
import com.jijy.music.presentation.dto.UserDto;
import com.jijy.music.services.exceptions.NotFoundException;
import com.jijy.music.services.interfaces.ReproductionListService;
import com.jijy.music.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class ReproductionListServiceImp implements ReproductionListService {

    private final ReproductionListRepository reproductionListRepository;
    private final MusicRepository musicRepository;
    private final UserService userService;
    private final MongoTemplate mongoTemplate;

    @Override
    public List<ReproductionList> getAllReproductionLists() {
        return reproductionListRepository.findAll();
    }

    @Override
    public ReproductionList getReproductionListById(String id) {
        Optional<ReproductionList> reproductionListOptional = reproductionListRepository.findById(id);
        if (reproductionListOptional.isEmpty()) {
            throw new NotFoundException("No se encontro la lista de reproduccion");
        }
        return reproductionListOptional.get();
    }

    @Override
    public List<ReproductionList> getReproductionListByMe() {
        String id = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        UserDto userDto = userService.getUserById(id);
        System.out.println(userDto.getUsername());
        return reproductionListRepository.findByAuthor(userDto.getUsername());
    }

    @Override
    public ReproductionList updateReproductionList(String id, ReproductionList reproductionList) {
        ReproductionList reproductionListOptional = this.getReproductionListById(id);

        reproductionListOptional.setTitle(reproductionList.getTitle());
        reproductionListOptional.setDescription(reproductionList.getDescription());

        return reproductionListRepository.save(reproductionListOptional);
    }

    @Override
    public ReproductionList deleteReproductionList(String id) {
        ReproductionList reproductionListOptional = this.getReproductionListById(id);
        reproductionListRepository.delete(reproductionListOptional);
        return reproductionListOptional;
    }

    @Override
    public ReproductionList createReproductionList(ReproductionList reproductionList) {

        String idUsername = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();

        reproductionList.setAuthor(userService.getUserById(idUsername).getUsername());
        reproductionList.setNumSong(reproductionList.getNumSong() + 1);
        reproductionList.setScore(List.of());
        reproductionList.setCreationDate(LocalDateTime.now());

        return reproductionListRepository.save(reproductionList);
    }

    @Override
    public List<Music> getListMusicByReproductionList(String reproductionListId) {
        return List.of();
    }

    @Override
    public List<ReproductionList> getListReproductionListByGenre(String genre) {

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("genres").in(genre))
        );

        List<ReproductionList> result = mongoTemplate.aggregate(aggregation, "reproductionList", ReproductionList.class).getMappedResults();

        return result;
    }

    @Override
    public ReproductionList likeToReproductionList(String reproductionListId) {
        ReproductionList reproductionList = this.getReproductionListById(reproductionListId);
        String idUsername = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        if (reproductionList.getLikedBy().contains(idUsername)) {
            reproductionList.getLikedBy().remove(idUsername);
        } else {
            reproductionList.getLikedBy().add(idUsername);
        }
        return reproductionListRepository.save(reproductionList);
    }

    @Override
    public ReproductionList scoreToReproductionList(String reproductionListId, ScoreReproductionListDTO scoreReproductionListDTO) {
        ReproductionList reproductionList = this.getReproductionListById(reproductionListId);
        String idUsername = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();

        List<ScoreModel> scoreModels = reproductionList.getScore();
        Optional<ScoreModel> existingScore = scoreModels.stream()
                .filter(scoreModel -> scoreModel.getUserId().equals(idUsername))
                .findFirst();

        if (existingScore.isEmpty()) {
            ScoreModel scoreModel = new ScoreModel(scoreReproductionListDTO.score(), idUsername);
            scoreModels.add(scoreModel);
        } else {
            if (scoreReproductionListDTO.score() == null) {
                scoreModels.remove(existingScore.get());
            } else {
                existingScore.get().setScore(scoreReproductionListDTO.score());
            }
        }

        return reproductionListRepository.save(reproductionList);
    }
}
