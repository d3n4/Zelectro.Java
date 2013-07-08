package Zelectro.Audio.Recognizer;

public class GoogleResponse {
    private String response;
    private String confidence;
    public GoogleResponse() { }
    public String getResponse() {
        return response;
    }
    protected void setResponse(String response) {
        this.response = response;
    }
    public String getConfidence() {
        return confidence;
    }
    protected void setConfidence(String confidence) {
        this.confidence = confidence;
    }
}
