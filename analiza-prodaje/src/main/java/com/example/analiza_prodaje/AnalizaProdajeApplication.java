package com.example.analiza_prodaje;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class AnalizaProdajeApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnalizaProdajeApplication.class, args);
	}
//redis://redisdb:6379
}
