package com.seis635.group.tictactoe.logic;

import com.seis635.group.tictactoe.TicTacToe;
import com.seis635.group.tictactoe.database.Database;
import com.seis635.group.tictactoe.view.Board;
import com.seis635.group.tictactoe.view.Cell;
import com.seis635.group.tictactoe.panel.EndOptionPanel;
import com.seis635.group.tictactoe.panel.PlayerInfoPanel;
import com.seis635.group.tictactoe.player.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LocalGame implements ActionListener, Runnable{
	private static final Logger LOGGER = LogManager.getLogger(LocalGame.class);


	private Board board;
	private EndOptionPanel endOptionPanel;
	private Player player;
	private ComputerAI ai;
	private Judger judger;
	private Database db;
	private int rowSelected;
	private int columnSelected;
	private boolean myTurn = false;
	private boolean waiting = true;
	private int userID;
	
	public LocalGame(int userID, String name){
		this.userID = userID;
		board = new Board();
		endOptionPanel = new EndOptionPanel();
		player = new Player();	
		ai = new ComputerAI();
		judger = new Judger();
		db = new Database();
		
		db.connect();
		player.setName(name);
		board.setMyName(name);

		board.setOpponentName("AI");
		for (int i=0;i<3;i++){   //add listener to cells
			for (int j=0;j<3;j++){
				board.getCell()[i][j].addActionListener(this);
			}
		}
		
		board.getPlayerNameButton().addActionListener( new ActionListener() { //click for player info
			@Override
			public void actionPerformed(ActionEvent e) {
				if(board.getPlayerNameButton().getText().equals("")==false){
					PlayerInfoPanel pi = new PlayerInfoPanel();
					pi.addName(player.getName());
					pi.addWins(db.getWins(userID));
					pi.addLoses(db.getLoses(userID));
					pi.addTies(db.getTies(userID));
				}
			}
		});
		board.getOpponentNameButton().addActionListener( new ActionListener() { //click for player info
			@Override
			public void actionPerformed(ActionEvent e) {
				if(board.getPlayerNameButton().getText().equals("")==false){
					PlayerInfoPanel pi = new PlayerInfoPanel();
					pi.addName("AI");
					pi.addWins(db.getWins(1));
					pi.addLoses(db.getLoses(1));
					pi.addTies(db.getTies(1));
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
		while (true){
			try {
				if (time != 0) {				//rematch
					waitForPlayerAction();
					gameReset();
				}
				LOGGER.info("start to connect");
				playerChoose();
				LOGGER.info(player.getRole());
				
				board.setMyMarker(player.getRole()+"");
				board.setOpponentMarker(ai.getRole()+"");
				
				while (true){
					if (player.getRole() == 'x'){     //game flow
						waitForPlayerAction();
						if (judger.getWinner()!=' ')
							break;
						aiTurn();
						judger.Judge(board.getStatus());
						if (judger.getWinner()!=' ')
							break;
					}else if (player.getRole() == 'o'){
						aiTurn();
						judger.Judge(board.getStatus());
						if (judger.getWinner()!=' ')
							break;
						waitForPlayerAction();
						if (judger.getWinner()!=' ')
							break;
					}
				}
				if (judger.getWinner() == player.getRole()){
					endOptionPanel.setResult("You win!");
					db.updateWins(userID);
					db.updateLoses(1);
				}
				else if (judger.getWinner() == 't'){
					endOptionPanel.setResult("It's a tie!");
					db.updateTies(userID);
					db.updateTies(1);
				}else {
					endOptionPanel.setResult("You lose!");
					db.updateLoses(userID);
					db.updateWins(1);
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
			LOGGER.info("cell"+ "("+cellClicked.getRow()+","+cellClicked.getColumn()+")"+ "clicked");
			waiting = false;
			myTurn = false;
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
			ai.setRole('o');
		}else {
			player.setRole('o');
			myTurn = false;
			ai.setRole('x');
		}
		LOGGER.info(e);
	}
	public void aiTurn(){   
		ai.putMarker(board.getCell(), board.getStatus());
		myTurn = true;
		waiting=true;
	}
}