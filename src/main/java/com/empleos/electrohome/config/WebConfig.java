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
        // Detectamos la ruta base del proyecto
        String userDir = System.getProperty("user.dir").replace("\\", "/");

        // Ajuste para Linux/Render: debe empezar con /
        String formattedPath = userDir.startsWith("/") ? userDir : "/" + userDir;

        // Ruta final para el manejador
        String uploadPath = "file:" + formattedPath + "/uploads/";

        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(uploadPath)
                .setCachePeriod(0);

        System.out.println("--- VERIFICACIÓN DE DESPLIEGUE ---");
        System.out.println("SISTEMA OPERATIVO: " + System.getProperty("os.name"));
        System.out.println("RUTA DE IMÁGENES: " + uploadPath);
    }
}