package com.baumgarten.transskribus.transcription;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "whisper")
public class WhisperProperties {

    private String binaryPath;
    private String modelsDir;
    private String defaultModel;
    private String language;
    private String ffmpegPath;
    private String libPath;

    // Getter & Setter
    public String getBinaryPath() { return binaryPath; }
    public void setBinaryPath(String binaryPath) { this.binaryPath = binaryPath; }

    public String getModelsDir() { return modelsDir; }
    public void setModelsDir(String modelsDir) { this.modelsDir = modelsDir; }

    public String getDefaultModel() { return defaultModel; }
    public void setDefaultModel(String defaultModel) { this.defaultModel = defaultModel; }

    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }

    public String getFfmpegPath() { return ffmpegPath; }
    public void setFfmpegPath(String ffmpegPath) { this.ffmpegPath = ffmpegPath; }
    
    public String getLibPath() { return libPath; }
    public void setLibPath(String libPath) { this.libPath = libPath; }
}
