package com.seis635.group.tictactoe.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

	private static Statement statement;
	private static Connection conn;

	public void connect() {
		String driver = "com.mysql.cj.jdbc.Driver";

		try {
			Class.forName(driver);
			conn = DriverManager
					.getConnection("jdbc:mysql://database-1-group-8.civvwd6ongdl.us-east-1.rds.amazonaws.com:3306/tic-tac-toe?"
							+ "user=admin&password=group8final");
			System.out.println("Succeeded connecting to the Database!");
		} catch (Exception e) {
			System.out.println("Failed to the Database!");
			e.printStackTrace();
		}

	}

    public int getUserID(String userName, String password){
		int id;

		try {
			String query = "Select id from infotable where username =" + "'" + userName + "'" + "and password =" + "'" + password + "'";
			statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			if (resultSet.next() == false) {
				System.out.println("Record does not exist");
			}
			id = resultSet.getInt("id");
			return id;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.print("failed to query");
		}
		return 0;
	}

	public ResultSet getRecord(int id) {
		try {
			String query = "Select name,win,lose,tie from infotable where id =" + id;
			statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			if (resultSet.next() == false) {
				System.out.println("Record does not exist");
				//String update = "Insert into playerinfo (name, win, lose, tie) values (" + "'" + name + "'" + ",0,0,0)";
				//statement.executeUpdate(update);
				//resultSet = statement.executeQuery(query);
				//resultSet.next();
			}

			return resultSet;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.print("failed to query");
		}
		return null;
	}

	public String getWins(int id) {
		try {
			return getRecord(id).getString("win");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "XX";
	}

	public String getLoses(int id) {
		try {
			return getRecord(id).getString("lose");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "XX";
	}

	public String getTies(int id) {
		try {
			return getRecord(id).getString("tie");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "XX";
	}

	public void updateWins(int id) {
		int num = Integer.parseInt(getWins(id));
		num++;
		String update = "update infotable set win = " + num + " where id = " + id;
		try {
			statement.executeUpdate(update);
			System.out.println("Record updated!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void updateLoses(int id) {
		int num = Integer.parseInt(getLoses(id));
		num++;
		String update = "update infotable set lose = " + num + " where id= " + id;
		try {
			statement.executeUpdate(update);
			System.out.println("Record updated!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void updateTies(int id) {
		int num = Integer.parseInt(getTies(id));
		num++;
		String update = "update infotable set tie = " + num + " where id =" + id;
		try {
			statement.executeUpdate(update);
			System.out.println("Record updated!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void resetScore (int id) {
		String update = "update infotable set win = 0, lose = 0, tie = 0 where id = " + id;
		try {
			statement.executeUpdate(update);
			System.out.println("Record updated!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}