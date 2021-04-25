import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
	//	private static ResultSet resultSet;
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


	public ResultSet getRecode(String name) {
		try {
			String query = "Select * from playerinfo where name =" + "'" + name + "'";
			statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			if (resultSet.next() == false) {
				System.out.println("Record does not exist,create new player info for " + name);
				String update = "Insert into playerinfo values (" + "'" + name + "'" + ",0,0,0)";
				statement.executeUpdate(update);
				resultSet = statement.executeQuery(query);
				resultSet.next();
			}

			return resultSet;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.print("failed to query");
		}
		return null;
	}

	public String getWins(String name) {
		try {
			return getRecode(name).getString("win");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "XX";
	}

	public String getLoses(String name) {
		try {
			return getRecode(name).getString("lose");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "XX";
	}

	public String getTies(String name) {
		try {
			return getRecode(name).getString("tie");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "XX";
	}

	public void updateWins(String name) {
		int num = Integer.parseInt(getWins(name));
		num++;
		String update = "update playerinfo set win = " + num + " where name = " + "'" + name + "'";
		try {
			statement.executeUpdate(update);
			System.out.println("Record updated!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	;

	public void updateLoses(String name) {
		int num = Integer.parseInt(getLoses(name));
		num++;
		String update = "update playerinfo set lose = " + num + " where name = " + "'" + name + "'";
		try {
			statement.executeUpdate(update);
			System.out.println("Record updated!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	;

	public void updateTies(String name) {
		int num = Integer.parseInt(getTies(name));
		num++;
		String update = "update playerinfo set tie = " + num + " where name = " + "'" + name + "'";
		try {
			statement.executeUpdate(update);
			System.out.println("Record updated!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	;

	public static void main(String arg[]) {
		Database db = new Database();
		db.connect();
		db.updateWins("test");
		db.updateLoses("test");
		db.updateTies("test");
		System.out.println(db.getWins("test"));
		System.out.println(db.getLoses("test"));
		System.out.println(db.getTies("test"));
	}

}