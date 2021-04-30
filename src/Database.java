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
					.getConnection("jdbc:mysql://localhost/tic-tac-toe?"
							+ "user=root&password=password");
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

	;

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

	;

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

	;

	public static void main(String arg[]) {
		Database db = new Database();
		db.connect();
		db.updateWins(2);
		db.updateLoses(2);
		db.updateTies(2);
		System.out.println(db.getWins(2));
		System.out.println(db.getLoses(2));
		System.out.println(db.getTies(2));
	}

}