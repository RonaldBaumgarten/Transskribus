package com.baumgarten.transskribus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.baumgarten.transskribus.config.WhisperProperties;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class TranscriptionController {

    private final TranscriptionService transcriptionService;
    private final WhisperProperties props;

    public TranscriptionController(TranscriptionService transcriptionService, WhisperProperties props) {
        this.transcriptionService = transcriptionService;
        this.props = props;
    }

    @PostMapping("/transcribe")
    public ResponseEntity<Map<String, String>> transcribe(@RequestParam("file") MultipartFile file) {
        return doTranscribe(file, props.getLanguage());
    }
    
    @PostMapping("/transcribe/{language}")
    public ResponseEntity<Map<String, String>> transcribeWithLanguage(
            @RequestParam("file") MultipartFile file,
            @PathVariable String language) {
        return doTranscribe(file, language);
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