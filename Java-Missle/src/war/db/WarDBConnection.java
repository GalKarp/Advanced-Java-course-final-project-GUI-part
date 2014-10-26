package war.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class WarDBConnection {
	private static Connection connection;
	private static String dbUrl;
	private static ResultSet rs;
	private static PreparedStatement statement;
	private static Statement state;

	static {
		dbUrl = "jdbc:mysql://localhost/";

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connection = DriverManager.getConnection(dbUrl, "root", "");
			// Empty all tables when the program starts
			try {
				String sql = "CREATE DATABASE IF NOT EXISTS war";
				state = connection.createStatement();
				state.executeUpdate(sql);
				dbUrl = dbUrl + "war";
				connection = DriverManager.getConnection(dbUrl, "root", "");
				String destructorSQL =

				"CREATE TABLE IF NOT EXISTS `destructors` "
						+ "( `id` VARCHAR(10) NOT NULL, "
						+ "`type` VARCHAR(40) NULL, " + "PRIMARY KEY (`id`)) ";
				state = connection.createStatement();
				state.executeUpdate(destructorSQL);
				String missileSQL =

				"CREATE TABLE IF NOT EXISTS `missile` "
						+ "(`id` VARCHAR(10) NOT NULL, "
						+ "`date` TIME NULL, "
						+ "`destination` VARCHAR(40) NOT NULL, "
						+ "`damage` INT NOT NULL, "
						+ "`flytime` INT NOT NULL, "
						+ "`status` VARCHAR(40) NOT NULL, "
						+ "`destructors_id` VARCHAR(10) NULL, "
						+ " PRIMARY KEY (`id`)) ";
				state = connection.createStatement();
				state.executeUpdate(missileSQL);

				String launcherSQL = "CREATE TABLE IF NOT EXISTS `launchers` "
						+ "(`id` VARCHAR(30) NOT NULL, "
						+ "`isHidden` TINYINT(1) NULL,"
						+ "`status` VARCHAR(40) NULL, "
						+ "PRIMARY KEY (`id`)) ";
				state = connection.createStatement();
				state.executeUpdate(launcherSQL);
			} catch (SQLException sqlException) {
				if (sqlException.getErrorCode() == 1007) {
					// Database already exists error
					System.out.println(sqlException.getMessage());
				} else {
					// Some other problems, e.g. Server down, no permission, etc
					sqlException.printStackTrace();
				}
			}

		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			while (e != null) {
				System.out.println(e.getMessage());
				e = e.getNextException();
			}
		}
	}

	public static void addNewMissile(String id, String destination, int damage,
			int flyTime, String status) {
		try {
			 connection.createStatement();
			statement = (PreparedStatement) connection
					.prepareStatement("INSERT INTO war.missile (id, date,destination,damage,flyTime,status) VALUES (?, now(), ?, ?, ?, ?)");
			statement.setString(1, id);
			// statement.setString(1, "now()");
			statement.setString(2, destination);
			statement.setInt(3, damage);
			statement.setInt(4, flyTime);
			statement.setString(5, status);

			// statement.executeUpdate(); - IMPORTANTE! This is a must to update
			// the database
			statement.executeUpdate();
		} catch (SQLException e) {
			while (e != null) {
//				System.out.println(e.getMessage());
				e = e.getNextException();
			}
		}
	}

	public static void updateMissileStatus(String id, String status) {
		try {
			statement = (PreparedStatement) connection.prepareStatement("UPDATE war.missile SET `status` = ? WHERE missile.id = ?; ");
			statement.setString(1, status);
			statement.setString(2, id);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void updateMissileStatusAndDestructor(String id,
			String destructors_id) {
		try {
			statement = (PreparedStatement) connection.prepareStatement("UPDATE war.missile SET `destructors_id` = ?  WHERE missile.id = ?; ");
			statement.setString(1, destructors_id);
			statement.setString(2, id);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void addNewLauncher(String launcherId, int isHidden) {
		try {
			 connection.createStatement();
			statement = (PreparedStatement) connection
					.prepareStatement("INSERT INTO war.launchers (id, isHidden) VALUES (?, ?)");
			statement.setString(1, launcherId);
			statement.setInt(2, isHidden);
			statement.executeUpdate();
		} catch (SQLException e) {
			while (e != null) {
				e = e.getNextException();
			}
		}
	}

	public static void addNewDestructor(String launcherId, String type) {
		try {
			 connection.createStatement();
			statement = (PreparedStatement) connection
					.prepareStatement("INSERT INTO war.destructors (id, type) VALUES (?, ?)");
			statement.setString(1, launcherId);
			statement.setString(2, type);
			statement.executeUpdate();
		} catch (SQLException e) {
			while (e != null) {
				e = e.getNextException();
			}
		}
	}

	public static void updateLauncherStatus(String id, String status) {
		try {
			statement = (PreparedStatement) connection.prepareStatement("UPDATE war.launchers SET `status` = ? WHERE launchers.id = ?; ");
			statement.setString(1, status);
			statement.setString(2, id);
			statement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void clearWarDataBase() {
		try {
			statement = (PreparedStatement) connection
					.prepareStatement("TRUNCATE TABLE `launchers`");
			statement.execute();
			statement = (PreparedStatement) connection
					.prepareStatement("TRUNCATE TABLE `missile`");
			statement.execute();
			statement = (PreparedStatement) connection
					.prepareStatement("TRUNCATE TABLE `destructors`");
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static List<String> getAllTablesNames() {
		List<String> allTables = new ArrayList<String>();
		try {
			DatabaseMetaData md = connection.getMetaData();
			ResultSet rs = md.getTables(null, null, "%", null);

			while (rs.next()) {
				allTables.add(rs.getString(3));
			}
		} catch (SQLException e) {
			while (e != null) {
				e.printStackTrace();
				e = e.getNextException();
			}
		}
		return allTables;
	}

	public static Vector<String[]> getQueryData(String tableName,
			Vector<String> headers) {
		Vector<String[]> rowsData = new Vector<String[]>();
		try {
			statement = (PreparedStatement) connection
					.prepareStatement("SELECT * FROM " + tableName);
			rs = statement.executeQuery();
			ResultSetMetaData meta = rs.getMetaData();
			int numOfCols = meta.getColumnCount();

			// rebuild the headers array with the new column names
			headers.clear();
			for (int h = 1; h <= numOfCols; h++) {
				headers.add(meta.getColumnName(h));
			}

			while (rs.next()) {
				String[] record = new String[numOfCols];
				for (int i = 0; i < numOfCols; i++) {
					record[i] = rs.getString(i + 1);
				}
				rowsData.addElement(record);
			}
		} catch (SQLException e) {
			while (e != null) {
				e.printStackTrace();
				e = e.getNextException();
			}
		}
		return rowsData;
	}

	public static void closeDB() {
		try {
			if (connection != null) {
				connection.close();
			}
			if (statement != null) {
				statement.close();
			}
			if (rs != null) {
				rs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
