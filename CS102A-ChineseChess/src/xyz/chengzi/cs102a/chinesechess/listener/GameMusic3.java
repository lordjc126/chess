package xyz.chengzi.cs102a.chinesechess.listener;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class GameMusic3 implements Runnable{
    private Thread t;
    FileInputStream bgm3 = new FileInputStream(System.getProperty("user.dir") + "\\Minecraft.wav");

    public AudioStream getBGM3() {
        return BGM3;
    }

    AudioStream BGM3 = new AudioStream(bgm3);

    public GameMusic3() throws IOException {
    }


    @Override
    public void run() {
        try {
            this.playBgm3();
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

    public void playBgm3() throws IOException {
        AudioPlayer.player.start(BGM3);
    }
    public void stop(){
        AudioPlayer.player.stop(BGM3);
    }


}
