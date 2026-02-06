package com.empleos.electrohome.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Esta ruta funciona en local y en la nube
        // Spring buscará la carpeta 'uploads' que está al lado de tu pom.xml o jar
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:uploads/");

        // Log para confirmar la ruta en los logs de Render
        String absolutePath = new java.io.File("uploads").getAbsolutePath();
        System.out.println("SISTEMA ACTIVO: Servidor buscando fotos en: " + absolutePath);
    }
}
