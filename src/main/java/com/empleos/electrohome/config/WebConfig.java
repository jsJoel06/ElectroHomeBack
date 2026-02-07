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
        // Esto obtiene la ruta de la raíz de tu proyecto (donde está el pom.xml y la carpeta uploads)
        String rootPath = System.getProperty("user.dir");

        // Construimos la ruta: file:/opt/render/project/src/uploads/
        String uploadPath = "file:" + rootPath + "/uploads/";

        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(uploadPath)
                .setCachePeriod(0);

        System.out.println("--- CONFIGURACIÓN NUBE ---");
        System.out.println("RAÍZ DEL PROYECTO: " + rootPath);
        System.out.println("BUSCANDO FOTOS EN: " + uploadPath);
    }
}