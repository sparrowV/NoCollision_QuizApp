package database.dao;

import database.DBContract;
import database.DBInfo;
import database.bean.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
	private DataSource pool;

	public UserDAO(DataSource pool) {
		this.pool = pool;
	}

	/**
	 * Returns a list of users from database.
	 *
	 * @return list of products.
	 */
	public List<User> getUsers() {
		// Create a new empty list.
		List<User> users = new ArrayList<>();

		Connection connection = null;
		try {
			// Get the connection from the pool.
			connection = pool.getConnection();

			Statement statement = connection.createStatement();
			statement.executeQuery("USE " + DBInfo.MYSQL_DATABASE_NAME);

			// Prepare and execute 'SELECT' query.
			String query = "SELECT * FROM " + DBContract.UserTable.TABLE_NAME + ";";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();

			// Iterate over result set and add products to the list.
			while (resultSet.next()) {
				users.add(fetchUser(resultSet));
			}

			// Close statement and result set.
			resultSet.close();
			preparedStatement.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) try {
				// Returns the connection to the pool.
				connection.close();
			} catch (Exception ignored) {
			}
		}

		return users;
	}


	/**
	 * Adds given user to the database
	 *
	 * @param user
	 * @throws SQLException
	 */
	public void addUser(User user) throws SQLException {
		Connection connection = null;
		try {
			connection = pool.getConnection();

			Statement statement = connection.createStatement();
			statement.executeQuery("USE " + DBInfo.MYSQL_DATABASE_NAME);

			// query inserting into users table
			String query = "INSERT INTO " + DBContract.UserTable.TABLE_NAME + " " + "(" +
					DBContract.UserTable.COLUMN_NAME_FIRST_NAME + ", " +
					DBContract.UserTable.COLUMN_NAME_LAST_NAME + ", " +
					DBContract.UserTable.COLUMN_NAME_USERNAME + ", " +
					DBContract.UserTable.COLUMN_NAME_PASSWORD + ") " +
					"VALUES (?,?,?,?);";

			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, user.getFirstName());
			preparedStatement.setString(2, user.getLastName());
			preparedStatement.setString(3, user.getUsername());
			preparedStatement.setString(4, user.getPassword());

			preparedStatement.executeUpdate();

			preparedStatement.close();
			statement.close();
		} catch (SQLException e) {
			e.getStackTrace();
		} finally {
			if (connection != null) try {
				// Returns the connection to the pool.
				connection.close();
			} catch (Exception ignored) {
			}
		}
	}

	/**
	 * Returns given username's id from database or -1
	 * if there's no such username.
	 *
	 * @param username
	 * @return id
	 */
	public int getUserId(String username) {
		int userId = -1;
		Connection connection;

		try {
			connection = pool.getConnection();

			Statement statement = connection.createStatement();
			statement.executeQuery("USE " + DBInfo.MYSQL_DATABASE_NAME);

			String query = "SELECT " + DBContract.UserTable.COLUMN_NAME_ID + " FROM " +
					DBContract.UserTable.TABLE_NAME + " WHERE " +
					DBContract.UserTable.COLUMN_NAME_USERNAME + " = \"" + username + "\";";

			ResultSet resultSet = statement.executeQuery(query);
			resultSet.next();

			userId = resultSet.getInt(DBContract.UserTable.COLUMN_NAME_ID);

			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return userId;
	}


	public boolean usernameExists(String username) throws SQLException {
		Connection connection;
		try {
			connection = pool.getConnection();

			Statement statement = connection.createStatement();
			statement.executeQuery("USE " + DBInfo.MYSQL_DATABASE_NAME);

			String query = "SELECT COUNT(*) FROM " + DBContract.UserTable.TABLE_NAME +
					" WHERE " + DBContract.UserTable.COLUMN_NAME_USERNAME + "=?";

			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, username);

			ResultSet resultset = preparedStatement.executeQuery();
			resultset.next();

			int count = resultset.getInt(1);

			statement.close();
			preparedStatement.close();
			connection.close();

			// returns true if query found given username
			return (count > 0);


		} catch (SQLException e) {
			System.out.println("Something wrong in select from users table");
		}

		return false;
	}

	public boolean userExists(String username, String password) {
		Connection connection;
		try {
			connection = pool.getConnection();

			Statement statement = connection.createStatement();
			statement.executeQuery("USE " + DBInfo.MYSQL_DATABASE_NAME);

			String query = "SELECT COUNT(*) FROM " + DBContract.UserTable.TABLE_NAME +
					" WHERE " + DBContract.UserTable.COLUMN_NAME_USERNAME + "=?" + " AND " +
					DBContract.UserTable.COLUMN_NAME_PASSWORD + "=?";

			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);

			ResultSet resultset = preparedStatement.executeQuery();
			resultset.next();

			int count = resultset.getInt(1);

			statement.close();
			preparedStatement.close();
			connection.close();

			// returns true if query found given username
			return (count > 0);


		} catch (SQLException e) {
			System.out.println("Something wrong in select from users table");
		}

		return false;
	}


	/**
	 * Creates and returns user from result set.
	 *
	 * @param resultSet
	 * @return user
	 * @throws SQLException
	 */
	private User fetchUser(ResultSet resultSet) throws SQLException {
		User user = new User();

		// Set values.
		user.setFirstName(resultSet.getString(DBContract.UserTable.COLUMN_NAME_FIRST_NAME));
		user.setLastName(resultSet.getString(DBContract.UserTable.COLUMN_NAME_LAST_NAME));
		user.setUsername(resultSet.getString(DBContract.UserTable.COLUMN_NAME_USERNAME));
		user.setPassword(resultSet.getString(DBContract.UserTable.COLUMN_NAME_PASSWORD));

		return user;
	}
}
