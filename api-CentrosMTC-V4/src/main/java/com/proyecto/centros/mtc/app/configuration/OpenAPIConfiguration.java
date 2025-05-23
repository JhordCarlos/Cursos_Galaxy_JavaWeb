package com.proyecto.centros.mtc.app.configuration;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenAPIConfiguration {

	 @Bean
	   public OpenAPI defineOpenApi() {
	       Server server = new Server();
	       server.setUrl("http://localhost:8082");
	       server.setDescription("Centros Médicos MTC");

	       Contact myContact = new Contact();
	       myContact.setName("Jhord Carlos Panta");
	       myContact.setEmail("jpantai2024@gmail.com");

	       Info information = new Info()
	               .title("Centros Médicos MTC API")
	               .version("1.0")
	               .description("La API expone endpoints para la administración de Centros Médicos MTC.")
	               .contact(myContact);
	       return new OpenAPI().info(information).servers(List.of(server));
	   }
	
}
