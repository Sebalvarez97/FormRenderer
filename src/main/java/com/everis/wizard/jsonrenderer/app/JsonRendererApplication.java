package com.everis.wizard.jsonrenderer.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.Unirest;

@SpringBootApplication
public class JsonRendererApplication {

	public static void main(String[] args) {
		SpringApplication.run(JsonRendererApplication.class, args);
		
	
	}

}

