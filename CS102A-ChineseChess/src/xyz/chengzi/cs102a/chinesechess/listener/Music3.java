package xyz.chengzi.cs102a.chinesechess.listener;
import java.io.FileInputStream;
import java.io.IOException;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
public class Music3 implements Runnable{
    private Thread t;


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
        FileInputStream bgm3 = new FileInputStream(System.getProperty("user.dir") + "\\Bad Apple.wav");
        AudioStream as = new AudioStream(bgm3);
        AudioPlayer.player.start(as);
    }
}
