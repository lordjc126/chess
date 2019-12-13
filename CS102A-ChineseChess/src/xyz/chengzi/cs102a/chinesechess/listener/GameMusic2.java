package xyz.chengzi.cs102a.chinesechess.listener;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class GameMusic2 implements Runnable{

        private Thread t;
    FileInputStream bgm2 = new FileInputStream(System.getProperty("user.dir") + "\\Danny.wav");

    public AudioStream getBGM2() {
        return BGM2;
    }

    AudioStream BGM2 = new AudioStream(bgm2);

    public GameMusic2() throws IOException {
    }


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

            AudioPlayer.player.start(BGM2);
        }
        public void stop() {
            AudioPlayer.player.stop(BGM2);
        }

    }

