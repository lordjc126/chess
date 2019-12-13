package xyz.chengzi.cs102a.chinesechess.listener;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class GameMusic1 implements Runnable{
    private Thread t;
    private FileInputStream bgm1 = new FileInputStream(System.getProperty("user.dir") + "\\Haggstrom.wav");
    private AudioStream BGM1 = new AudioStream(bgm1);

    public AudioStream getBGM1() {
        return BGM1;
    }

    public GameMusic1() throws IOException {
    }


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
        AudioPlayer.player.start(BGM1);
    }
    public void stop(){
        AudioPlayer.player.stop(BGM1);
    }


}
