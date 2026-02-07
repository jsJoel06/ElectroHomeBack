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
        // Esta línea es la clave: obtenemos la ruta absoluta del sistema
        String rootPath = new File("uploads").getAbsolutePath();

        // Construimos la URL asegurándonos de que tenga el formato file:/ruta/en/linux
        String uploadPath = "file:" + (rootPath.startsWith("/") ? "" : "/") + rootPath + "/";

        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(uploadPath)
                .setCachePeriod(0);

        System.out.println("SISTEMA OPERATIVO DETECTADO: Linux (Render)");
        System.out.println("RUTA REAL APLICADA: " + uploadPath);
    }
}