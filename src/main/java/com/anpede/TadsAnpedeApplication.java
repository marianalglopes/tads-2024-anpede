package com.anpede;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableWebMvc //Aqui está aceitando as portas diferentes.
public class TadsAnpedeApplication implements WebMvcConfigurer { //Define métodos customizados para o callback. Estamos trabalhando com requisições.
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
		//Adiciona o(s) mapeamento(s) que pode ser acessado. No caso, estamos escolhendo os métodos que podem ser acessados.
		//Embora os métodos estejam "liberados", são liberados apenas a certos usuários.
	}
	
	public static void main(String[] args) {
		SpringApplication.run(TadsAnpedeApplication.class, args);
	}

}
