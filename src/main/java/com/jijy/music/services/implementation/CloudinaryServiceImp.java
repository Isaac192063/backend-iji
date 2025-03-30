package com.jijy.music.services.implementation;

import com.cloudinary.Cloudinary;
import com.cloudinary.api.ApiResponse;
import com.cloudinary.utils.ObjectUtils;
import com.jijy.music.presentation.dto.MusicDto;
import com.jijy.music.services.interfaces.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryServiceImp implements CloudinaryService {

    private final Cloudinary cloudinary;

    public String uploadSong(MultipartFile file) throws IOException {


        Map uploadResult =  cloudinary.uploader().upload(file.getBytes(),
                ObjectUtils.asMap(
                        "resource_type", "auto",
                        "folder", "canciones"
                )
        );

        return uploadResult.get("url").toString();
    }

    public List<String> getAllSongs() throws Exception {
        List<String> urls = new ArrayList<>();
        ApiResponse apiResponse = cloudinary.search()
                .expression("resource_type:video AND folder:" + "canciones")
                .maxResults(5)
                .execute();

        List<Map<String, Object>> resources = (List<Map<String, Object>>) apiResponse.get("resources");

        for (Map<String, Object> resource : resources) {
            urls.add((String) resource.get("secure_url")); // Obtiene la URL segura
        }

        return urls;
    }

    @Override
    public boolean deleteSong(String id) throws Exception {
        try {
            Map result = cloudinary.uploader().destroy(id, ObjectUtils.asMap("resource_type", "video"));
            System.out.println(result.toString());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}
