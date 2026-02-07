package com.empleos.electrohome.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        String uploadDir;

        if (System.getenv("RENDER") != null) {
            uploadDir = "/tmp/uploads/";
        } else {
            uploadDir = Paths.get("uploads").toAbsolutePath().toString() + "/";
        }

        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + uploadDir)
                .setCachePeriod(0);

        System.out.println("RUTA UPLOADS: " + uploadDir);
    }

}