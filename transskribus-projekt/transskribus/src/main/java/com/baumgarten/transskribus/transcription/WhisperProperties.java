package com.baumgarten.transskribus.transcription;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "whisper")
public class WhisperProperties {

    private String binaryPath;
    private String modelPath;
    private String language;
    private String ffmpegPath;
    private String libPath;

    // Getter & Setter
    public String getBinaryPath() { return binaryPath; }
    public void setBinaryPath(String binaryPath) { this.binaryPath = binaryPath; }

    public String getModelPath() { return modelPath; }
    public void setModelPath(String modelPath) { this.modelPath = modelPath; }

    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }

    public String getFfmpegPath() { return ffmpegPath; }
    public void setFfmpegPath(String ffmpegPath) { this.ffmpegPath = ffmpegPath; }
    
    public String getLibPath() { return libPath; }
    public void setLibPath(String libPath) { this.libPath = libPath; }
}
