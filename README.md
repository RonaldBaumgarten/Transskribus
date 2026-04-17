# Transskribus
A backend for transcribing (english) speach to text

## How to run
Navigat to the transskribus-folder and run
```bash
./mvnw spring-boot:run
```

To test the application, run the following command from the projects root-folder:
```bash
curl -X POST http://localhost:8080/api/transcribe -F "file=@whisper.cpp/samples/jfk.wav"
```
