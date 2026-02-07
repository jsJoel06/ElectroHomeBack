package com.empleos.electrohome.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String rootPath = System.getProperty("user.dir").replace("\\", "/");
        String uploadPath;

        // Si la ruta NO empieza con C: (o sea, estamos en Render/Linux)
        if (!rootPath.startsWith("C:")) {
            // En Linux/Render la ruta debe ser absoluta desde la raíz
            uploadPath = "file:" + (rootPath.startsWith("/") ? "" : "/") + rootPath + "/uploads/";
        } else {
            // Estás en tu computadora local
            uploadPath = "file:/" + rootPath + "/uploads/";
        }

        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(uploadPath)
                .setCachePeriod(0);

        System.out.println("SISTEMA OPERATIVO DETECTADO: " + System.getProperty("os.name"));
        System.out.println("RUTA CONFIGURADA: " + uploadPath);
    }
}