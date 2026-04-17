package com.baumgarten.transskribus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(WhisperProperties.class)
public class TransskribusApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransskribusApplication.class, args);
	}

}
