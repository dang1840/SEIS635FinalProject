package com.seis635.group.tictactoe.panel;

import javax.swing.*;
import java.awt.*;


public class NameInputPanel extends JFrame{
	private JLabel gameTitle;
	private JLabel toolTip;
	private JTextField getNameField;
	
	private Font titleFont = new Font("Calibri", Font.BOLD, 50);
	private Font tipFont = new Font("Calibri", Font.PLAIN, 20);
	
	public NameInputPanel (){
		
		gameTitle = new JLabel("Tic Tac Toe");
		toolTip = new JLabel("Please enter your name",10);
		getNameField = new JTextField();
		
		setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(450, 200);
		setResizable(false);
		setLocationRelativeTo(null);
		
		gameTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(Box.createRigidArea(new Dimension(0, 5)));
		add(gameTitle);
		add(Box.createRigidArea(new Dimension(0, 10)));
		
		toolTip.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(toolTip);
		add(Box.createRigidArea(new Dimension(0, 10)));
		getNameField.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(getNameField);
		add(Box.createRigidArea(new Dimension(0, 20)));
		
		gameTitle.setFont(titleFont);
		toolTip.setFont(tipFont);
		getNameField.setFont(tipFont);
		
		
		setVisible(true);
	}
	
	public JTextField getNameField(){
		return this.getNameField;
	}
	public static void main(String[] args){
		new NameInputPanel();
	}
}
