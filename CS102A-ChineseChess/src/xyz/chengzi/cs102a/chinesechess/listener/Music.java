package xyz.chengzi.cs102a.chinesechess.listener;

import java.io.FileInputStream;
import java.io.IOException;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class Music implements Runnable {

    private Thread t;


    @Override
    public void run() {
        try {
            this.playBgm1();
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

    public void playBgm1() throws IOException {
        FileInputStream bgm1 = new FileInputStream(System.getProperty("user.dir") + "\\Sweden.wav");
        AudioStream as = new AudioStream(bgm1);
        AudioPlayer.player.start(as);
    }


}


//System.getProperty("user.dir")

