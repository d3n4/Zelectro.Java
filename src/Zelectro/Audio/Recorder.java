package Zelectro.Audio;

import javax.sound.sampled.AudioFileFormat;

public class Recorder implements Runnable {
    private static Thread _thread = null;
    private static String _file = "record.wav";
    private static Microphone microphone = null;
    public static void record(String file){
        _file = file;
        if(microphone == null)
            microphone = new Microphone(AudioFileFormat.Type.WAVE);
        if(_thread == null)
            _thread = new Thread(new Recorder());
        _thread.start();
    }

    public static void stop(){
        microphone.close();
        while(microphone.getState() != Microphone.CaptureState.CLOSED);
        if(_thread != null)
            if(!_thread.isInterrupted())
                _thread.interrupt();
    }

    public void run(){
        try {
            microphone.captureAudioToFile(_file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
