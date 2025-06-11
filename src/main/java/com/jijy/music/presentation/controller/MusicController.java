package com.jijy.music.presentation.controller;

import com.jijy.music.presentation.dto.GenresDto;
import com.jijy.music.presentation.dto.MusicDto;
import com.jijy.music.presentation.dto.MusicRequestDTO;
import com.jijy.music.services.interfaces.MusicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("${path.music}")
@CrossOrigin(originPatterns = "http://localhost:5173/")
public class MusicController {

    private final MusicService musicService;

    @PostMapping()
    public MusicDto addMusic(@ModelAttribute MusicRequestDTO musicRequestDTO) throws IOException {
        return musicService.addMusic(musicRequestDTO);
    }

    @GetMapping("{id}")
    public MusicDto getMusicById(@PathVariable String id) {
        return musicService.getMusicId(id);
    }

    @PutMapping("{id}")
    public MusicDto getMusicById(@PathVariable String id, @RequestBody MusicDto musicDto) {
        return musicService.updateMusic(id, musicDto);
    }

    @GetMapping()
    public List<MusicDto> getAllMusic() {
        return musicService.getAllMusic();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<MusicDto> deleteMusic(@PathVariable String id) throws Exception {
        return ResponseEntity.ok(musicService.deleteMusic(id));
    }




}
