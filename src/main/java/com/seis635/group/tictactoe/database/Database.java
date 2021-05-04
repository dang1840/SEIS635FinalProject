package com.seis635.group.tictactoe.database;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.sql.*;

public class Database {
	private static final Logger LOGGER = LogManager.getLogger(Database.class);

	private static Statement statement;
	private static Connection conn;

	public void connect() {
		String driver = "com.mysql.cj.jdbc.Driver";

		try {
			Class.forName(driver);
			conn = DriverManager
					.getConnection("jdbc:mysql://database-1-group-8.civvwd6ongdl.us-east-1.rds.amazonaws.com:3306/tic-tac-toe?"
							+ "user=admin&password=group8final");
			LOGGER.info("Succeeded connecting to the Database!");
		} catch (Exception e) {
			LOGGER.info("Failed to the Database!");
			JOptionPane.showMessageDialog(null, e.getCause());		}

	}

    public int getUserID(String userName, String password){
		int id;

		try {
			String query = "Select id from infotable where username =" + "'" + userName + "'" + "and password =" + "'" + password + "'";
			statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			if (resultSet.next() == false) {
				LOGGER.info("Record does not exist");
			}
			id = resultSet.getInt("id");
			return id;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			 JOptionPane.showMessageDialog(null,"Custom Displayed Error Message");
LOGGER.error("Internal Error", e);			LOGGER.info("failed to query");
		}
		return 0;
	}

	public ResultSet getRecord(int id) {
		try {
			String query = "Select name,win,lose,tie from infotable where id =" + id;
			statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			if (resultSet.next() == false) {
				LOGGER.info("Record does not exist");
				//String update = "Insert into playerinfo (name, win, lose, tie) values (" + "'" + name + "'" + ",0,0,0)";
				//statement.executeUpdate(update);
				//resultSet = statement.executeQuery(query);
				//resultSet.next();
			}

			return resultSet;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			 JOptionPane.showMessageDialog(null,"Custom Displayed Error Message");
LOGGER.error("Internal Error", e);			LOGGER.info("failed to query");
		}
		return null;
	}

	public String getWins(int id) {
		try {
			return getRecord(id).getString("win");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			 JOptionPane.showMessageDialog(null,"Custom Displayed Error Message");
LOGGER.error("Internal Error", e);		}
		return "XX";
	}

	public String getLoses(int id) {
		try {
			return getRecord(id).getString("lose");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			 JOptionPane.showMessageDialog(null,"Custom Displayed Error Message");
LOGGER.error("Internal Error", e);		}
		return "XX";
	}

	public String getTies(int id) {
		try {
			return getRecord(id).getString("tie");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			 JOptionPane.showMessageDialog(null,"Custom Displayed Error Message");
LOGGER.error("Internal Error", e);		}
		return "XX";
	}

	public void updateWins(int id) {
		int num = Integer.parseInt(getWins(id));
		num++;
		String update = "update infotable set win = " + num + " where id = " + id;
		try {
			statement.executeUpdate(update);
			LOGGER.info("Record updated!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			 JOptionPane.showMessageDialog(null,"Custom Displayed Error Message");
LOGGER.error("Internal Error", e);		}
	}

	public void updateLoses(int id) {
		int num = Integer.parseInt(getLoses(id));
		num++;
		String update = "update infotable set lose = " + num + " where id= " + id;
		try {
			statement.executeUpdate(update);
			LOGGER.info("Record updated!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			 JOptionPane.showMessageDialog(null,"Custom Displayed Error Message");
LOGGER.error("Internal Error", e);		}
	}

	public void updateTies(int id) {
		int num = Integer.parseInt(getTies(id));
		num++;
		String update = "update infotable set tie = " + num + " where id =" + id;
		try {
			statement.executeUpdate(update);
			LOGGER.info("Record updated!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			 JOptionPane.showMessageDialog(null,"Custom Displayed Error Message");
LOGGER.error("Internal Error", e);		}
	}
}