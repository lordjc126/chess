package xyz.chengzi.cs102a.chinesechess.listener;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class MainframeMusic implements Runnable {

    public AudioStream getBackgroundMusic() {
        return BackgroundMusic;
    }

    public AudioStream getSound() {
        return Sound;
    }
    private Thread t;
    private FileInputStream bgm1 = new FileInputStream(System.getProperty("user.dir") + "\\Sweden.wav");
    private FileInputStream sound = new FileInputStream(System.getProperty("user.dir") + "\\菜单.wav");
    private AudioStream BackgroundMusic = new AudioStream(bgm1);
    private AudioStream Sound = new AudioStream(sound);

    public MainframeMusic() throws IOException {
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
        AudioPlayer.player.start(BackgroundMusic);
    }
    public void playSound() throws IOException {
        AudioPlayer.player.start(Sound);
    }
    public void stop(){
        AudioPlayer.player.stop(BackgroundMusic);
    }

}

