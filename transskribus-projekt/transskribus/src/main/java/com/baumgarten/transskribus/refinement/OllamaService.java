package com.baumgarten.transskribus.refinement;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Service
public class OllamaService {

    private final RestClient restClient;
    private final OllamaProperties props;

    public OllamaService(OllamaProperties props) {
        this.props = props;
        this.restClient = RestClient.builder()
                .baseUrl(props.getBaseUrl())
                .build();
    }

    public String refine(String rawText) {
        Map<String, Object> request = Map.of(
                "model", props.getModel(),
                "prompt", props.getPrompt() + "\n\n" + rawText,
                "stream", false
        );

        Map<String, Object> response = restClient.post()
                .uri("/api/generate")
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});

        return (String) response.get("response");
    }
}