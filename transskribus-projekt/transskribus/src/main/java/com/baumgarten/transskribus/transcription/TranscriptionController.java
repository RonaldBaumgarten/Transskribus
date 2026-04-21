package com.baumgarten.transskribus.transcription;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.baumgarten.transskribus.refinement.OllamaService;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class TranscriptionController {

    private final TranscriptionService transcriptionService;
    private final WhisperProperties props;
    private final OllamaService ollamaService;

    public TranscriptionController(TranscriptionService transcriptionService, WhisperProperties props, OllamaService ollama) {
        this.transcriptionService = transcriptionService;
        this.props = props;
        this.ollamaService = ollama;
    }

    @PostMapping("/transcribe")
    public ResponseEntity<Map<String, String>> transcribe(@RequestParam("file") MultipartFile file) {
        return doTranscribe(file, props.getLanguage());
    }
    
    @PostMapping("/transcribe-and-refine")
    public ResponseEntity<Map<String, String>> transcribeAndRefine(@RequestParam("file") MultipartFile file) {
        try {
            String raw = transcriptionService.transcribe(file, props.getLanguage());
            String refined = ollamaService.refine(raw);
            return ResponseEntity.ok(Map.of(
            		"original", raw,
            		"refined", refined
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", e.getMessage()));
        }
    }
    
    @PostMapping("/transcribe/{language}")
    public ResponseEntity<Map<String, String>> transcribeWithLanguage(@RequestParam("file") MultipartFile file, @PathVariable String language) {
        return doTranscribe(file, language);
    }
    
    @PostMapping("/transcribe-and-refine/{language}")
    public ResponseEntity<Map<String, String>> transcribeAndRefine(@RequestParam("file") MultipartFile file, @PathVariable String language) {
        try {
            String raw = transcriptionService.transcribe(file, language);
            String refined = ollamaService.refine(raw);
            return ResponseEntity.ok(Map.of(
            		"original", raw,
            		"refined", refined
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", e.getMessage()));
        }
    }
    
    private ResponseEntity<Map<String, String>> doTranscribe(MultipartFile file, String language) {
        try {
            String text = transcriptionService.transcribe(file, language);
            return ResponseEntity.ok(Map.of("text", text));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", e.getMessage()));
        }
    }
}