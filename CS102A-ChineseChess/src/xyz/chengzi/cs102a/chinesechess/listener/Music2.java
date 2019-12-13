package xyz.chengzi.cs102a.chinesechess.listener;

import java.io.FileInputStream;
import java.io.IOException;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class Music2 implements Runnable{
    private Thread t;


    @Override
    public void run() {
        try {
            this.playBgm2();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    public void start() {
        if (t == null) {
            t = new Thread(this, "BGM");
            t.start();

        }
    }

    public void playBgm2() throws IOException {
        FileInputStream bgm2 = new FileInputStream(System.getProperty("user.dir") + "\\Haggstrom.wav");
        AudioStream as = new AudioStream(bgm2);
        AudioPlayer.player.start(as);
    }


}
