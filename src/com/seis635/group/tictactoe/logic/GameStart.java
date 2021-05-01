package com.seis635.group.tictactoe.logic;

import com.seis635.group.tictactoe.database.Database;
import com.seis635.group.tictactoe.panel.ModeChoosePanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GameStart implements Runnable {

    private static final int PVP_MODE = 1;
    private static final int PVE_MODE = 2;

    private ModeChoosePanel modeChoosePanel;

    private Database db;

    private boolean waiting = true;
    private int mode;
    private String playername;
    private int userID;

    public GameStart(String userName, String password) throws SQLException {

        modeChoosePanel = new ModeChoosePanel();
        db = new Database();
        db.connect();

        userID = db.getUserID(userName, password);
        ResultSet rs = db.getRecord(userID);
        playername = rs.getString("name");


        modeChoosePanel.addName(playername);
        modeChoosePanel.addWins(db.getWins(userID));
        modeChoosePanel.addLoses(db.getLoses(userID));
        modeChoosePanel.addTies(db.getTies(userID));
        modeChoosePanel.setVisible(true);

        ActionListener modeClicked = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                if (e.getSource() == modeChoosePanel.pvpBtn) {
                    mode = PVP_MODE;
                } else if (e.getSource() == modeChoosePanel.pveBtn) {
                    mode = PVE_MODE;
                }

                modeChoosePanel.setVisible(false);
                waiting = false;    //start game!
            }
        };
        modeChoosePanel.pveBtn.addActionListener(modeClicked);
        modeChoosePanel.pvpBtn.addActionListener(modeClicked);
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        try {
            waitForPlayerAction();
            if (mode == PVP_MODE) {
            } else if (mode == PVE_MODE) {
                new LocalGame(userID, playername);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void waitForPlayerAction() throws InterruptedException {
        while (waiting) {
            Thread.sleep(100);
        }
        waiting = true;
    }
}