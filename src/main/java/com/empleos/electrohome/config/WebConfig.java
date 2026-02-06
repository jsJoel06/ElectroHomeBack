package com.empleos.electrohome.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Esto obtiene la ruta real de la carpeta raíz de tu proyecto
        String userDir = System.getProperty("user.dir");
        String uploadsPath = "file:" + userDir + "/uploads/";

        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(uploadsPath);

        // Log para que veas en la consola dónde está buscando realmente
        System.out.println("Buscando fotos en: " + uploadsPath);
    }
}
