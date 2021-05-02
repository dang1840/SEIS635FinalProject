package com.seis635.group.tictactoe.music;

import com.seis635.group.tictactoe.TicTacToe;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class BackgroundMusic {
    private static final Logger LOGGER = LogManager.getLogger(BackgroundMusic.class);

    void playMusic(String musicLocation){
        try {
            File musicPath = new File(musicLocation);

            if(musicPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
            else{
                LOGGER.debug("Can't find file");
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
}