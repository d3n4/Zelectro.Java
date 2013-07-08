import Zelectro.Audio.Recognizer.*;
import Zelectro.Audio.Recorder;
import Zelectro.Audio.Synthesiser.Synthesiser;

public class Main {

    public static void main(String[] args) throws Exception {

        System.in.read();
        System.out.println("Слушаю ;з");
        Recorder.record("rec.wav");
        System.in.read();
        Recorder.stop();
        System.out.println("Распознаю");

        Recognizer recognizer = new Recognizer();
        GoogleResponse resp = recognizer.getRecognizedDataForWave("rec.wav");
        System.out.println("Результат: "+resp.getResponse());

        Synthesiser synthesiser = new Synthesiser();
        synthesiser.getMP3File("Привет мир", "record.mp3");
        System.in.read();
        //Clip clip = AudioSystem.getClip();
        //clip.open(AudioSystem.getAudioInputStream(inputStream));
        //clip.start();
        /*System.out.println("Press enter to continue");
        System.in.read();
        System.out.println("Listening");
        Zelectro.Audio.Recorder.record("rec.wav");
        System.in.read();
        Zelectro.Audio.Recorder.stop();
        System.out.println("Recognizing");

        Recognizer recognizer = new Recognizer();
        GoogleResponse resp = recognizer.getRecognizedDataForWave("rec.wav");
        System.out.println(resp.getResponse()); */
    }
}
