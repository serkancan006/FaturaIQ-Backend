package com.example.FaturaIQ.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173/","http://localhost:8081/","http://192.168.1.36:8081")  // React uygulamamızın URL'si
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
