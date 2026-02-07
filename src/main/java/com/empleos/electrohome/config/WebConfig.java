package com.empleos.electrohome.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // En Render, lo más seguro es usar la ruta relativa directa
        // o construirla con el directorio del usuario.
        String rootPath = System.getProperty("user.dir").replace("\\", "/");
        String uploadPath = "file:" + rootPath + "/uploads/";

        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(uploadPath)
                .setCachePeriod(0);

        System.out.println("SISTEMA OPERATIVO DETECTADO: Linux (Render)");
        System.out.println("RUTA CORREGIDA: " + uploadPath);
    }
}