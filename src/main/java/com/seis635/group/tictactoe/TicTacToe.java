package com.seis635.group.tictactoe;

import com.seis635.group.tictactoe.view.UserLogin;
import com.seis635.group.tictactoe.music.BackgroundMusic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;

public class TicTacToe {

    private static final Logger LOGGER = LogManager.getLogger(TicTacToe.class);

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
                     JOptionPane.showMessageDialog(null,"Custom Displayed Error Message");
LOGGER.error("Internal Error", e);                }

            }
        });
    }
}
