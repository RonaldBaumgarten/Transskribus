# Transskribus
A backend for transcribing speech to text. Powered by Spring Boot and OpenAI Whisper/Whisper.cpp

## How to run
This project uses java version 25

If you start with having neither whisper nor a model installed, here is how to do that:

In the folder whisper.cpp run:
```bash
cmake -B build && cmake --build build --config Release
bash ./models/download-ggml-model.sh <large-v3-turbo|small|...>
```

Run the spring boot backend with navigating to the transskribus-folder and run
```bash
./mvnw spring-boot:run
```

To test the application, run the following command from the projects root-folder:
```bash
curl -X POST http://localhost:8080/api/transcribe -F "file=@whisper.cpp/samples/jfk.mp3"
```

If you want to record your own small snippet for testing, if you are on linux with alsa installed you can use
```bash
arecord -d 3 -f cd test.wav && curl -X POST http://localhost:8080/api/transcribe-and-refine -F "file=@test.wav"
```
