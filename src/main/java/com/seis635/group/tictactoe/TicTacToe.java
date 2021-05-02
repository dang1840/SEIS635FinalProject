package com.seis635.group.tictactoe;

import com.seis635.group.tictactoe.view.UserLogin;
import com.seis635.group.tictactoe.music.BackgroundMusic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;

public class TicTacToe {

    private static final Logger LOGGER = LogManager.getLogger(TicTacToe.class);

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UserLogin frame = new UserLogin();
                    frame.setVisible(true);


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
