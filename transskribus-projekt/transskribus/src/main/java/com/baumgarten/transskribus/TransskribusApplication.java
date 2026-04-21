package com.baumgarten.transskribus;

import org.springframework.boot.SpringApplication; 
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.baumgarten.transskribus.refinement.OllamaProperties;
import com.baumgarten.transskribus.transcription.WhisperProperties;

@SpringBootApplication
@EnableConfigurationProperties({WhisperProperties.class, OllamaProperties.class})
public class TransskribusApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransskribusApplication.class, args);
	}

}
