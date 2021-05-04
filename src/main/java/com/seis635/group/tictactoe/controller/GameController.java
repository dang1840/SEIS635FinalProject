package com.seis635.group.tictactoe.controller;

import com.seis635.group.tictactoe.database.Database;
import com.seis635.group.tictactoe.logic.Judger;
import com.seis635.group.tictactoe.panel.EndOptionPanel;
import com.seis635.group.tictactoe.panel.PlayerInfoPanel;
import com.seis635.group.tictactoe.player.Player;
import com.seis635.group.tictactoe.view.Board;
import com.seis635.group.tictactoe.view.Cell;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class GameController implements ActionListener, Runnable {

    private static final Logger LOGGER = LogManager.getLogger(GameController.class);

    private Board board;
    private EndOptionPanel endOptionPanel;
    private Player player;
    private Judger judger;
    private Database db;
    ServerSocket server;
    private DataInputStream datain;
    private DataOutputStream dataout;
    private int rowSelected;
    private int columnSelected;
    private boolean isServer = false; //whether to start as server
    private boolean myTurn = false;
    private boolean waiting = true;
    private char otherplayer;
    private char winner = ' ';
    private int userID;


    public GameController(int userID, String name) {
        this.userID = userID;
        board = new Board();
        endOptionPanel = new EndOptionPanel();
        player = new Player();
        db = new Database();
        db.connect();
        player.setName(name);
        board.setMyName(name);

        for (int i = 0; i < 3; i++) {   //add listener to cells
            for (int j = 0; j < 3; j++) {
                board.getCell()[i][j].addActionListener(this);
            }
        }

        board.getPlayerNameButton().addActionListener(new ActionListener() { //click for player info
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                if (board.getPlayerNameButton().getText().equals("") == false) {
                    PlayerInfoPanel pi = new PlayerInfoPanel();
                    pi.addName(player.getName());
                    pi.addWins(db.getWins(userID));
                    pi.addLoses(db.getLoses(userID));
                    pi.addTies(db.getTies(userID));
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

        connectToServer();// try to start as client
        if (isServer) {    //failed to start as client, start as server
            connectToClient();
            judger = new Judger();
        }

        Thread thread = new Thread(this);
        thread.start();

    }

    @Override
    public void run() {
        // TODO Auto-generated method stub

        int time = 0;
        if (isServer) {
            while (true) {
                try {
                    if (time == 0) {

                        dataout.writeInt(player.getName().length());
                        dataout.writeChars(player.getName());
                        String opponentname = "";
                        int length = datain.readInt();
                        for (int i = 0; i < length; i++) {
                            opponentname = opponentname + datain.readChar();
                        }
                        System.out.print(opponentname);
                        board.setOpponentName(opponentname);
                    }
                    if (time != 0) {                //rematch
                        waitForPlayerAction();
                        gameReset();
                    }
                    LOGGER.info("start to connect");
                    playerChoose();
                    LOGGER.info(player.getRole());
                    dataout.writeChar(otherplayer);

                    board.setMyMarker(player.getRole() + "");
                    board.setOpponentMarker(otherplayer + "");

                    while (true) {
                        if (player.getRole() == 'x') {
                            waitForPlayerAction();
                            sendMove(rowSelected, columnSelected);
                            sendWinner();
                            if (judger.getWinner() != ' ')
                                break;
                            recieveMove();
                            sendWinner();
                            if (judger.getWinner() != ' ')
                                break;
                        } else if (player.getRole() == 'o') {
                            recieveMove();
                            sendWinner();
                            if (judger.getWinner() != ' ')
                                break;
                            waitForPlayerAction();
                            sendMove(rowSelected, columnSelected);
                            sendWinner();
                            if (judger.getWinner() != ' ')
                                break;
                        }
                    }
                    if (judger.getWinner() == player.getRole()) {
                        endOptionPanel.setResult("You win!");
                        db.updateWins(userID);
                    } else if (judger.getWinner() == 't') {
                        endOptionPanel.setResult("It's a tie!");
                        db.updateTies(userID);
                    } else {
                        endOptionPanel.setResult("You lose!");
                        db.updateLoses(userID);
                    }
                    waiting = true;
                    myTurn = false;
                    time++;
                    endOptionPanel.setVisible(true);

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                     JOptionPane.showMessageDialog(null,"Custom Displayed Error Message");
LOGGER.error("Internal Error", e);                    JOptionPane.showMessageDialog(null, "Connection Lost, wait for another player!");
                    gameReset();
                    board.getOpponentNameButton().setText("");
                    time = 0;
                    waintingforClient();
                }
            }
        } else if (!isServer) {
            while (true) {

                try {
                    if (time == 0) {
                        String opponentname = "";
                        int length = datain.readInt();
                        for (int i = 0; i < length; i++) {
                            opponentname = opponentname + datain.readChar();
                        }
                        System.out.print(opponentname);
                        board.setOpponentName(opponentname);
                        dataout.writeInt(player.getName().length());
                        dataout.writeChars(player.getName());


                    }
                    if (time != 0) {                //rematch
                        waitForPlayerAction();
                        gameReset();
                    }
                    char playerChoose = datain.readChar();
                    LOGGER.info(playerChoose);

                    if (playerChoose == 'x') {
                        player.setRole('x');
                        otherplayer = 'o';
                        myTurn = true;
                    }
                    if (playerChoose == 'o') {
                        player.setRole('o');
                        otherplayer = 'x';
                        myTurn = false;
                    }
                    board.setMyMarker(player.getRole() + "");
                    board.setOpponentMarker(otherplayer + "");
                    while (true) {
                        if (player.getRole() == 'x') {
                            waitForPlayerAction();
                            sendMove(rowSelected, columnSelected);
                            recieveWinner();
                            if (winner != ' ')
                                break;
                            recieveMove();
                            recieveWinner();
                            if (winner != ' ')
                                break;
                        } else if (player.getRole() == 'o') {
                            recieveMove();
                            recieveWinner();
                            if (winner != ' ')
                                break;
                            waitForPlayerAction();
                            sendMove(rowSelected, columnSelected);
                            recieveWinner();
                            if (winner != ' ')
                                break;
                        }
                    }
                    if (winner == player.getRole()) {
                        endOptionPanel.setResult("You win!");
                        db.updateWins(userID);
                    } else if (winner == 't') {
                        endOptionPanel.setResult("It's a tie!");
                        db.updateTies(userID);
                    } else {
                        endOptionPanel.setResult("You lose!");
                        db.updateLoses(userID);
                    }
                    waiting = true;
                    myTurn = false;
                    time++;
                    endOptionPanel.setVisible(true);

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                     JOptionPane.showMessageDialog(null,"Custom Displayed Error Message");
LOGGER.error("Internal Error", e);                    JOptionPane.showMessageDialog(null, "Connection Lost!");
                    System.exit(2);
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if (myTurn == true) {
            Cell cellClicked = (Cell) e.getSource();
            player.putMarker(cellClicked);
            rowSelected = cellClicked.getRow();
            columnSelected = cellClicked.getColumn();
            myTurn = false;
//			if (isServer){
//				judger.judge(board.getStatus());
//			}
            board.getStatus()[rowSelected][columnSelected] = cellClicked.getToken();
            LOGGER.info("cell" + "(" + cellClicked.getRow() + "," + cellClicked.getColumn() + ")" + "clicked");
            waiting = false;
        }
    }

    public void gameReset() {
        board.setMyMarker("Waiting");
        board.setOpponentMarker("Waiting");
        winner = ' ';
        if (isServer) {
            judger.setWinner(' ');
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board.getCell()[i][j].setToken(' ');
                board.getCell()[i][j].setIcon(null);
                board.getCell()[i][j].setEnabled(true);
                board.getStatus()[i][j] = ' ';
            }
        }
    }

    private void sendMove(int row, int column) throws IOException {
        dataout.writeInt(row);
        dataout.writeInt(column);
    }

    private void recieveMove() throws IOException {
        int row = datain.readInt();
        int column = datain.readInt();
        board.getCell()[row][column].setToken(otherplayer);
        board.getStatus()[row][column] = otherplayer;
        myTurn = true;
        if (isServer) {
            judger.Judge(board.getStatus());
        }
    }

    private void sendWinner() throws IOException {
        judger.Judge(board.getStatus());
        dataout.writeChar(judger.getWinner());
    }

    private void recieveWinner() throws IOException {
        winner = datain.readChar();
        LOGGER.info("winner: " + winner);
    }

    private void waitForPlayerAction() throws InterruptedException {
        while (waiting) {
            Thread.sleep(100);
        }
        waiting = true;
    }

    private void playerChoose() {
        double e = Math.random();

        if (e < 0.5) {
            player.setRole('x');
            myTurn = true;
            otherplayer = 'o';
        } else {
            player.setRole('o');
            myTurn = false;
            otherplayer = 'x';
        }
        LOGGER.info(e);
    }

    private void connectToClient() {
        try {
            server = new ServerSocket(3306);
            Socket socket = server.accept();
            datain = new DataInputStream(socket.getInputStream());
            dataout = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            // TODO Auto-generated catch block
             JOptionPane.showMessageDialog(null,"Custom Displayed Error Message");
LOGGER.error("Internal Error", e);            JOptionPane.showMessageDialog(null, "Socket is in use!");
            System.exit(1);
        }
    }

    private void connectToServer() {
        try {
            Socket socket = new Socket(InetAddress.getLocalHost(), 3306);
            datain = new DataInputStream(socket.getInputStream());
            dataout = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.err.print(e);
            LOGGER.info("try to start as server");
            isServer = true;
        }
    }

    public void waintingforClient() {

        try {
            Socket socket = server.accept();
            datain = new DataInputStream(socket.getInputStream());
            dataout = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            // TODO Auto-generated catch block
             JOptionPane.showMessageDialog(null,"Custom Displayed Error Message");
LOGGER.error("Internal Error", e);        }
    }

    public Board getBoard() {
        return this.board;
    }

    public Player getPlayer() {
        return this.player;
    }
}