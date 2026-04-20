package com.baumgarten.transskribus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class RefinementController {

    private final OllamaService ollamaService;

    public RefinementController(OllamaService ollamaService) {
        this.ollamaService = ollamaService;
    }

    @PostMapping("/refine")
    public ResponseEntity<Map<String, String>> refine(@RequestBody Map<String, String> body) {
        String rawText = body.get("text");

        if (rawText == null || rawText.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        String refined = ollamaService.refine(rawText);
        return ResponseEntity.ok(Map.of(
                "original", rawText,
                "refined", refined
        ));
    }
}