package com.seis635.group.tictactoe.controller;

import com.seis635.group.tictactoe.database.Database;
import com.seis635.group.tictactoe.logic.Judger;
import com.seis635.group.tictactoe.panel.EndOptionPanel;
import com.seis635.group.tictactoe.panel.PlayerInfoPanel;
import com.seis635.group.tictactoe.player.Player;
import com.seis635.group.tictactoe.player.Player2;
import com.seis635.group.tictactoe.view.Board;
import com.seis635.group.tictactoe.view.Cell;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameController implements ActionListener, Runnable{
    private Board board;
    private EndOptionPanel endOptionPanel;
    private Player player;
    private Player2 player2;
    private Judger judger;
    private Database db;
    private int rowSelected;
    private int columnSelected;
    private boolean myTurn = false;
    private boolean waiting = true;


    public GameController(){
        board = new Board();
        endOptionPanel = new EndOptionPanel();
        player = new Player();
        player2 = new Player2();
        judger = new Judger();
        db = new Database();

        db.connect();
        player.setName("Player1");
        board.setMyName("Player1");

        player2.setName("Player2");
        board.setOpponentName("Player2");
        for (int i=0;i<3;i++){   //add listener to cells
            for (int j=0;j<3;j++){
                board.getCell()[i][j].addActionListener(this);
            }
        }

        board.getPlayerNameButton().addActionListener( new ActionListener() { //click for player info
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                if(board.getPlayerNameButton().getText().equals("")==false){
                    PlayerInfoPanel pi = new PlayerInfoPanel();
                    pi.addName("Player1");
                    pi.addWins(db.getWins(3));
                    pi.addLoses(db.getLoses(3));
                    pi.addTies(db.getTies(3));
                }
            }
        });
        board.getOpponentNameButton().addActionListener( new ActionListener() { //click for player info
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                if(board.getPlayerNameButton().getText().equals("")==false){
                    PlayerInfoPanel pi = new PlayerInfoPanel();
                    pi.addName("Player2");
                    pi.addWins(db.getWins(4));
                    pi.addLoses(db.getLoses(4));
                    pi.addTies(db.getTies(4));
                }
            }
        });

        endOptionPanel.rematchBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                waiting = false;
                endOptionPanel.setVisible(false);
            }
        });

        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        int time = 0;
        Database data;
        while (true){
            try {
                if (time != 0) {				//rematch
                    waitForPlayerAction();
                    gameReset();
                }
                System.out.println("start to connect");
                playerChoose();
                System.out.println(player.getRole());

                board.setMyMarker(player.getRole()+"");
                board.setOpponentMarker(player2.getRole()+"");

                while (true){
                    if (player.getRole() == 'x'){     //game flow
                        waitForPlayerAction();
                        if (judger.getWinner()!=' ')
                            break;
                        waitForPlayerAction();
                        if (judger.getWinner()!=' ')
                            break;
                    }else if (player.getRole() == 'o'){
                        waitForPlayerAction();
                        if (judger.getWinner()!=' ')
                            break;
                        waitForPlayerAction();
                        if (judger.getWinner()!=' ')
                            break;
                    }
                }
                if (judger.getWinner() == player.getRole()){
                    endOptionPanel.setResult("P1 win!");
                    db.updateWins(3);
                    db.updateLoses(4);
                }
                else if (judger.getWinner() == 't'){
                    endOptionPanel.setResult("It's a tie!");
                    db.updateTies(3);
                    db.updateTies(4);
                }else {
                    endOptionPanel.setResult("P2 win!");
                    db.updateLoses(3);
                    db.updateWins(4);
                }
                waiting = true;
                myTurn = false;
                time++;
                endOptionPanel.setVisible(true);

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Connection Lost!");
                System.exit(2);
            }
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {


        // TODO Auto-generated method stub
        if (myTurn == true){
            Cell cellClicked = (Cell) e.getSource();
            player.putMarker(cellClicked);
            rowSelected = cellClicked.getRow();
            columnSelected = cellClicked.getColumn();
            board.getStatus()[rowSelected][columnSelected] = cellClicked.getToken();
            judger.Judge(board.getStatus());
            System.out.println("cell"+ "("+cellClicked.getRow()+","+cellClicked.getColumn()+")"+ "clicked");
            waiting = false;
            myTurn = false;
        }
        else{
            Cell cellClicked = (Cell) e.getSource();
            player2.putMarker(cellClicked);
            rowSelected = cellClicked.getRow();
            columnSelected = cellClicked.getColumn();
            board.getStatus()[rowSelected][columnSelected] = cellClicked.getToken();
            judger.Judge(board.getStatus());
            System.out.println("cell"+ "("+cellClicked.getRow()+","+cellClicked.getColumn()+")"+ "clicked");
            waiting = false;
            myTurn = true;
        }

    }

    private void waitForPlayerAction() throws InterruptedException {
        while (waiting) {
            Thread.sleep(100);
        }
        waiting = true;
    }

    public void gameReset(){
        board.setMyMarker("Waiting");
        board.setOpponentMarker("Waiting");
        judger.setWinner(' ');

        for (int i=0;i<3;i++){
            for (int j=0;j<3;j++){
                board.getCell()[i][j].setToken(' ');
                board.getCell()[i][j].setIcon(null);
                board.getCell()[i][j].setEnabled(true);
                board.getStatus()[i][j] = ' ';
            }
        }
    }

    private void playerChoose(){
        double e = Math.random();

        if (e<0.5){
            player.setRole('x');
            myTurn = true;
            player2.setRole('o');
        }else {
            player.setRole('o');
            myTurn = false;
            player2.setRole('x');
        }

    }

}