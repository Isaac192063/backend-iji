package com.jijy.music.services.interfaces;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CloudinaryService {
    String uploadSong(MultipartFile file) throws IOException;
    List<String> getAllSongs() throws Exception;
    boolean deleteSong(String id) throws Exception;
}
