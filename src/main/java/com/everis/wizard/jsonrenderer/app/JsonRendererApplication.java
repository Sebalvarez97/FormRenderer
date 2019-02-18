package com.everis.wizard.jsonrenderer.app;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JsonRendererApplication {
	private static final Logger logger= LogManager.getLogger(JsonRendererApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(JsonRendererApplication.class, args);
		logger.info("mensaje de INFORMACION");
		logger.error("mensaje de ERROR");
		logger.debug("Mensaje de debug");
	}

}

