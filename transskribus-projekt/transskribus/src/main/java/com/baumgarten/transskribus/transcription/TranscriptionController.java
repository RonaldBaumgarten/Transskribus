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

    public TranscriptionController(TranscriptionService transcriptionService,
                                   WhisperProperties props,
                                   OllamaService ollamaService) {
        this.transcriptionService = transcriptionService;
        this.props = props;
        this.ollamaService = ollamaService;
    }

    @PostMapping("/transcribe")
    public ResponseEntity<Map<String, String>> transcribe(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "model", required = false) String model) {
        return doTranscribe(file, props.getLanguage(), model);
    }

    @PostMapping("/transcribe/{language}")
    public ResponseEntity<Map<String, String>> transcribeWithLanguage(
            @RequestParam("file") MultipartFile file,
            @PathVariable String language,
            @RequestParam(value = "model", required = false) String model) {
        return doTranscribe(file, language, model);
    }

    @PostMapping("/transcribe-and-refine")
    public ResponseEntity<Map<String, String>> transcribeAndRefine(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "model", required = false) String model) {
        return doTranscribeAndRefine(file, props.getLanguage(), model);
    }

    @PostMapping("/transcribe-and-refine/{language}")
    public ResponseEntity<Map<String, String>> transcribeAndRefineWithLanguage(
            @RequestParam("file") MultipartFile file,
            @PathVariable String language,
            @RequestParam(value = "model", required = false) String model) {
        return doTranscribeAndRefine(file, language, model);
    }

    private ResponseEntity<Map<String, String>> doTranscribe(
            MultipartFile file, String language, String model) {
        try {
            String text = transcriptionService.transcribe(file, language, model);
            return ResponseEntity.ok(Map.of("text", text));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", String.valueOf(e.getMessage())));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", String.valueOf(e)));
        }
    }

    private ResponseEntity<Map<String, String>> doTranscribeAndRefine(
            MultipartFile file, String language, String model) {
        try {
            String raw = transcriptionService.transcribe(file, language, model);
            String refined = ollamaService.refine(raw);
            return ResponseEntity.ok(Map.of(
                    "original", raw,
                    "refined", refined
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", String.valueOf(e.getMessage())));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", String.valueOf(e)));
        }
    }
}
