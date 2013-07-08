package Zelectro.Audio.Synthesiser;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class Synthesiser {
    public String lang = "ru"; // en
    private final static String GOOGLE_SYNTHESISER_URL = "http://translate.google.com/translate_tts?tl=";
    public Synthesiser() {}

    public void getMP3File(String synthText, String filename) throws Exception {
        InputStream stream = getMP3Stream(synthText);
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(filename);
            byte[] buffer = new byte[256];
            while ((stream.read(buffer, 0, 256)) != -1)
                outputStream.write(buffer, 0, 256);
        } finally {
            if (stream != null)
                stream.close();
            if (outputStream != null)
                outputStream.close();
        }
    }

    public InputStream getMP3Stream(String synthText) throws Exception {
        String encoded = URLEncoder.encode(synthText, "UTF-8");
        URL url = new URL(GOOGLE_SYNTHESISER_URL + lang + "&q=" + encoded);
        URLConnection urlConn = url.openConnection();
        urlConn.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:2.0) Gecko/20100101 Firefox/4.0");
        return urlConn.getInputStream();
    }

}
