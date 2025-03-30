package com.jijy.music.presentation.controller;

import com.jijy.music.presentation.dto.GenresDto;
import com.jijy.music.services.interfaces.GenresService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${path.genres}")
@CrossOrigin(originPatterns = "http://localhost:5173/")
public class GenresController {

    private final GenresService genresService;

    @GetMapping("")
    public List<GenresDto> getAllGenres() {
        return genresService.getGenres();
    }

    @GetMapping("{id}")
    public GenresDto getGenreById(@PathVariable String id) {
        return genresService.getGenreById(id);
    }
}
