# Transskribus
A backend for transcribing speach to text. Powered by Spring Boot and OpenAI Whisper/Whisper.cpp

## How to run
Navigat to the transskribus-folder and run
```bash
./mvnw spring-boot:run
```

To test the application, run the following command from the projects root-folder:
```bash
curl -X POST http://localhost:8080/api/transcribe -F "file=@whisper.cpp/samples/jfk.wav"
```

If you want to record your own small snippet for testing, if you are on linux with alsa installed you can use
```bash
arecord -d 3 -f cd test.wav && curl -X POST http://localhost:8080/api/transcribe-and-refine -F "file=@test.wav"
```
