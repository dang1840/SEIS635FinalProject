package com.seis635.group.tictactoe.music;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class BackgroundMusic {

    private File musicPath = new File("251461__joshuaempyre__arcade-music-loop.wav");
    private Clip clip;
    private int lastFrame;



    void playMusic(){
        try {
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
            clip = AudioSystem.getClip();
            clip.open(audioInput);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);


        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public void pause() {

        if (clip != null && clip.isRunning()) {
            lastFrame = clip.getFramePosition();
            clip.stop();
        }

    }

    public void resume() {

        if (clip != null && !clip.isRunning()) {
            // Make sure we haven't passed the end of the file...
            if (lastFrame < clip.getFrameLength()) {
                clip.setFramePosition(lastFrame);
            } else {
                clip.setFramePosition(0);
            }
            clip.start();
        }

    }
}

