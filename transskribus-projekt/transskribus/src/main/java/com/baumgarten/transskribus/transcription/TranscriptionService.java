package com.baumgarten.transskribus.transcription;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.baumgarten.transskribus.refinement.OllamaService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
public class TranscriptionService {

    private final WhisperProperties props;
    private final OllamaService ollamaService;

    public TranscriptionService(WhisperProperties props, OllamaService ollama) {
        this.props = props;
        this.ollamaService = ollama;
    }

    public String transcribe(MultipartFile audioFile, String language, String model) throws IOException, InterruptedException {

		Path modelFile = resolveModel(model);
		
        // Temporäre Dateien anlegen
        File tempInput = File.createTempFile("audio-input-", getExtension(audioFile.getOriginalFilename()));
        File tempWav = File.createTempFile("audio-wav-", ".wav");

        try {
            audioFile.transferTo(tempInput);

            // Schritt 1: ffmpeg - speichern als 16kHz WAV
            runProcess(List.of(
                props.getFfmpegPath(),
                "-y",
                "-i", tempInput.getAbsolutePath(),
                "-ar", "16000",
                "-ac", "1",
                "-c:a", "pcm_s16le",
                tempWav.getAbsolutePath()
            ));

            // Schritt 2: whisper-cli  - WAV-file transkribieren
            String output = runProcessAndCapture(List.of(
                props.getBinaryPath(),
                "-m", modelFile.toString(),
                "-f", tempWav.getAbsolutePath(),
                "-l", language,
                "-np",  // no progress
                "-nt"   // no timestamps
            ));

            return output.trim();
           

        } finally {
            tempInput.delete();
            tempWav.delete();
        }
    }

    private void runProcess(List<String> command) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder(command);
        pb.redirectErrorStream(true);
        pb.environment().put("LD_LIBRARY_PATH", props.getLibPath());

        Process process = pb.start();
        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new RuntimeException("Prozess fehlgeschlagen mit Exit-Code: " + exitCode);
        }
    }

    private String runProcessAndCapture(List<String> command) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder(command);
        pb.redirectErrorStream(true);
        pb.environment().put("LD_LIBRARY_PATH", props.getLibPath());

        Process process = pb.start();
        String output = new String(process.getInputStream().readAllBytes());
        process.waitFor();
        return output;
    }

    private String getExtension(String filename) {
        if (filename == null) return ".tmp";
        int dot = filename.lastIndexOf('.');
        return dot >= 0 ? filename.substring(dot) : ".tmp";
    }

	private Path resolveModel(String model) {
		String name = (model == null || model.isBlank()) ? props.getDefaultModel() : model;

		// Schutz vor Path Traversal (z.B. "../../etc/passwd")
		if (!name.matches("[A-Za-z0-9._-]+")) {
			throw new IllegalArgumentException("Ungültiger Modellname: " + name);
		}

		Path file = Path.of(props.getModelsDir()).resolve("ggml-" + name + ".bin");
		if (!Files.exists(file)) {
    		throw new IllegalArgumentException("Modell nicht gefunden: " + file.toAbsolutePath());
		}
		return file;
	}
}
