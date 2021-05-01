package com.seis635.group.tictactoe;

import com.seis635.group.tictactoe.music.BackgroundMusic;
import com.seis635.group.tictactoe.view.UserLogin;

import java.awt.*;

public class TicTacToe {

    /**
     * Launch the application.
     */
    public static void main(String[] args) {

        String filePath = "bensound-happyrock.wav";
        BackgroundMusic background = new BackgroundMusic();

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UserLogin frame = new UserLogin();
                    frame.setVisible(true);
                    //background.playMusic(filePath);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
