package com.backend.pelicula;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class PeliculaApplication {

    public static void main(String[] args) {
        SpringApplication.run(PeliculaApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Permite todas las rutas
                        .allowedOrigins("http://localhost:5173") // Cambia al dominio de tu frontend
                        .allowedMethods("*") // Permite todos los métodos HTTP (GET, POST, etc.)
                        .allowedHeaders("*") // Permite todos los encabezados
                        .allowCredentials(true); // Permite cookies y credenciales
            }
        };
    }

}
