package com.jijy.music.services.interfaces;

import com.jijy.music.persistence.model.Music;
import com.jijy.music.persistence.model.ReproductionList;
import com.jijy.music.presentation.dto.ScoreReproductionListDTO;

import java.util.List;

public interface ReproductionListService {
    List<ReproductionList> getAllReproductionLists();
    ReproductionList getReproductionListById(String id);
    List<ReproductionList> getReproductionListByMe();
    ReproductionList updateReproductionList(String id, ReproductionList reproductionList);
    ReproductionList deleteReproductionList(String id);
    ReproductionList createReproductionList(ReproductionList reproductionList);
    List<Music> getListMusicByReproductionList(String reproductionListId);
    List<ReproductionList> getListReproductionListByGenre(String genre);
    ReproductionList likeToReproductionList(String reproductionListId);
    ReproductionList scoreToReproductionList(String reproductionListId, ScoreReproductionListDTO scoreReproductionListDTO);
}
