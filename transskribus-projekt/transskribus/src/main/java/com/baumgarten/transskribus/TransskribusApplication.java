package com.baumgarten.transskribus;

import org.springframework.boot.SpringApplication; 
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.baumgarten.transskribus.config.WhisperProperties;
import com.baumgarten.transskribus.config.OllamaProperties;

@SpringBootApplication
@EnableConfigurationProperties({WhisperProperties.class, OllamaProperties.class})
public class TransskribusApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransskribusApplication.class, args);
	}

}
