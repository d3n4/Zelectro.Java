package Zelectro.Audio.Recognizer;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;


// TODO: Отправку голоса стримом

public class Recognizer {

    public String lang = "ru-RU"; // en-US
    private static final String GOOGLE_RECOGNIZER_URL_NO_LANG = "https://www.google.com/speech-api/v1/recognize?xjerr=1&client=chromium&lang=";
    public Recognizer() {}

    public GoogleResponse getRecognizedDataForWave(File waveFile, String language) throws Exception {
        FlacEncoder flacEncoder = new FlacEncoder();
        File flacFile = new File(waveFile + ".flac");
        flacEncoder.convertWaveToFlac(waveFile, flacFile);
        String response = rawRequest(flacFile, language);
        flacFile.delete();
        String[] parsedResponse = parseResponse(response);
        GoogleResponse googleResponse = new GoogleResponse();
        if (parsedResponse != null) {
            googleResponse.setResponse(parsedResponse[0]);
            googleResponse.setConfidence(parsedResponse[1]);
        } else {
            googleResponse.setResponse(null);
            googleResponse.setConfidence(null);
        }
        return googleResponse;
    }

    public GoogleResponse getRecognizedDataForWave(String waveFile, String language) throws Exception {
        return getRecognizedDataForWave(new File(waveFile), language);
    }

    public GoogleResponse getRecognizedDataForFlac(File flacFile, String language) throws Exception {
        String response = rawRequest(flacFile, language);
        String[] parsedResponse = parseResponse(response);
        GoogleResponse googleResponse = new GoogleResponse();
        if (parsedResponse != null) {
            googleResponse.setResponse(parsedResponse[0]);
            googleResponse.setConfidence(parsedResponse[1]);
        } else {
            googleResponse.setResponse(null);
            googleResponse.setConfidence(null);
        }
        return googleResponse;
    }

    public GoogleResponse getRecognizedDataForFlac(String flacFile, String language) throws Exception {
        return getRecognizedDataForFlac(new File(flacFile), language);
    }

    public GoogleResponse getRecognizedDataForWave(File waveFile) throws Exception {
        return getRecognizedDataForWave(waveFile, lang);
    }

    public GoogleResponse getRecognizedDataForWave(String waveFile) throws Exception {
        return getRecognizedDataForWave(waveFile, lang);
    }

    public GoogleResponse getRecognizedDataForFlac(File flacFile) throws Exception {
        return getRecognizedDataForFlac(flacFile, lang);
    }

    public GoogleResponse getRecognizedDataForFlac(String flacFile) throws Exception {
        return getRecognizedDataForFlac(flacFile, lang);
    }

    private String[] parseResponse(String rawResponse) {
        if (!rawResponse.contains("utterance"))
            return null;
        String[] parsedResponse = new String[2];
        String[] strings = rawResponse.split(":");
        parsedResponse[0] = strings[4].split("\"")[1];
        parsedResponse[1] = strings[5].replace("}]}", "");
        return parsedResponse;
    }

    private String rawRequest(File inputFile, String language) throws Exception {
        URL url;
        URLConnection urlConn;
        OutputStream outputStream;
        BufferedReader br;
        url = new URL(GOOGLE_RECOGNIZER_URL_NO_LANG + language);
        urlConn = url.openConnection();
        urlConn.setDoOutput(true);
        urlConn.setUseCaches(false);
        urlConn.setRequestProperty("Content-Type", "audio/x-flac; rate=8000");
        outputStream = urlConn.getOutputStream();
        FileInputStream fileInputStream = new FileInputStream(inputFile);
        byte[] buffer = new byte[256];
        while ((fileInputStream.read(buffer, 0, 256)) != -1)
            outputStream.write(buffer, 0, 256);
        fileInputStream.close();
        outputStream.close();
        br = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
        String response = br.readLine();
        br.close();
        return response;

    }
}
