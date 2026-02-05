package com.empleos.electrohome.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
       String rootPath = Paths.get("uploadss").toAbsolutePath().toString();
       registry.addResourceHandler("/uploadss/**")
               .addResourceLocations(rootPath);
    }
}
