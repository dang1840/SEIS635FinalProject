package com.seis635.group.tictactoe;

import com.seis635.group.tictactoe.view.UserLogin;

import java.awt.*;

public class TicTacToe {

    // TODO: > Compile error comment out for now.
//    private static final Logger LOGGER = LogManager.getLogger(TicTacToe.class);

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
