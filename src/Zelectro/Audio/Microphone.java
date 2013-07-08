package Zelectro.Audio;

import javax.sound.sampled.*;
import java.io.File;

public class Microphone {
    private TargetDataLine targetDataLine;

    public enum CaptureState {
        PROCESSING_AUDIO, STARTING_CAPTURE, CLOSED
    }

    private Thread _thread = null;
    CaptureState state;
    private AudioFileFormat.Type fileType;
    private File audioFile;
    public CaptureState getState() {
        return state;
    }

    private void setState(CaptureState state) {
        this.state = state;
    }

    public File getAudioFile() {
        return audioFile;
    }

    public void setAudioFile(File audioFile) {
        this.audioFile = audioFile;
    }

    public AudioFileFormat.Type getFileType() {
        return fileType;
    }

    public void setFileType(AudioFileFormat.Type fileType) {
        this.fileType = fileType;
    }

    public TargetDataLine getTargetDataLine() {
        return targetDataLine;
    }

    public void setTargetDataLine(TargetDataLine targetDataLine) {
        this.targetDataLine = targetDataLine;
    }

    public Microphone(AudioFileFormat.Type fileType) {
        setState(CaptureState.CLOSED);
        setFileType(fileType);
    }

    public void captureAudioToFile(File audioFile) throws Exception {
        setState(CaptureState.STARTING_CAPTURE);
        setAudioFile(audioFile);
        DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, getAudioFormat());
        setTargetDataLine((TargetDataLine) AudioSystem.getLine(dataLineInfo));
        _thread = new Thread(new CaptureThread());
        _thread.start();

    }

    public void captureAudioToFile(String audioFile) throws Exception {
        setState(CaptureState.STARTING_CAPTURE);
        File file = new File(audioFile);
        setAudioFile(file);
        DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, getAudioFormat());
        setTargetDataLine((TargetDataLine) AudioSystem.getLine(dataLineInfo));
        _thread = new Thread(new CaptureThread());
        _thread.start();
    }

    private AudioFormat getAudioFormat() {
        float sampleRate = 8000.0F;
        //8000,11025,16000,22050,44100
        int sampleSizeInBits = 16;
        //8,16
        int channels = 1;
        //1,2
        boolean signed = true;
        boolean bigEndian = false;
        return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
    }

    public void close() {
        if (getState() != CaptureState.CLOSED) {
            getTargetDataLine().stop();
            getTargetDataLine().close();
            if(_thread != null)
                if(_thread.isAlive() && !_thread.isInterrupted())
                    _thread.interrupt();
        }
    }

    private class CaptureThread implements Runnable {
        public void run() {
            try {
                setState(CaptureState.PROCESSING_AUDIO);
                AudioFileFormat.Type fileType = getFileType();
                File audioFile = getAudioFile();
                getTargetDataLine().open(getAudioFormat());
                getTargetDataLine().start();
                AudioSystem.write(new AudioInputStream(getTargetDataLine()), fileType, audioFile);
                setState(CaptureState.CLOSED);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

}
