package com.jijy.music.configuration;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Value("${cloudinary.url}")
    private String urlCloudinary;

    @Bean
    public Cloudinary getCloudinary() {
        return new Cloudinary(urlCloudinary);
    }
}
