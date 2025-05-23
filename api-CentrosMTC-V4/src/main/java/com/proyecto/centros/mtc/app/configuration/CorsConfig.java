package com.proyecto.centros.mtc.app.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/api/**") // Aplica CORS a todas las rutas bajo /api/
						.allowedOrigins("http://localhost:4200") // Permitir Angular
						.allowedMethods("GET", "POST", "PUT", "DELETE") // Métodos permitidos
						.allowedHeaders("*"); // Permitir todos los headers
			}
		};
	}

}
