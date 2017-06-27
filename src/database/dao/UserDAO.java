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
	 * @return list of users.
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
	public int addUser(User user) throws SQLException {
		Connection connection = null;
		int id = 0;
		try {
			connection = pool.getConnection();

			Statement statement = connection.createStatement();
			statement.executeQuery("USE " + DBInfo.MYSQL_DATABASE_NAME);

			// query inserting into users table
			String query = "INSERT INTO " + DBContract.UserTable.TABLE_NAME + " " + "(" +
					DBContract.UserTable.COLUMN_NAME_FIRST_NAME + ", " +
					DBContract.UserTable.COLUMN_NAME_LAST_NAME + ", " +
					DBContract.UserTable.COLUMN_NAME_USERNAME + ", " +
					DBContract.UserTable.COLUMN_NAME_PASSWORD + ", " +
					DBContract.UserTable.COLUMN_NAME_GENDER + ", " +
					DBContract.UserTable.COLUMN_NAME_COUNTRY + ", " +
					DBContract.UserTable.COLUMN_NAME_PICTURE + ", " +
					DBContract.UserTable.COLUMN_NAME_DATE_OF_BIRTH + ") " +
					"VALUES (?,?,?,?,?,?,?,?);";

			PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, user.getFirstName());
			preparedStatement.setString(2, user.getLastName());
			preparedStatement.setString(3, user.getUsername());
			preparedStatement.setString(4, user.getPassword());
			preparedStatement.setString(5, user.getGender());
			preparedStatement.setString(6, user.getCountry());
			preparedStatement.setString(7, user.getPicture());
			preparedStatement.setDate(8, new java.sql.Date(user.getDateOfBirth().getTime()));

			preparedStatement.executeUpdate();

			ResultSet keys = preparedStatement.getGeneratedKeys();
			keys.next();
			id = keys.getInt(1);

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
		assert id > 0;
		return id;
	}

	public boolean usernameExists(String username) throws SQLException {
		Connection connection;
		try {
			connection = pool.getConnection();

			Statement statement = connection.createStatement();
			statement.executeQuery("USE " + DBInfo.MYSQL_DATABASE_NAME);

			String query = "SELECT COUNT(*) FROM " + DBContract.UserTable.TABLE_NAME +
					" WHERE " + DBContract.UserTable.COLUMN_NAME_USERNAME + " = ?";

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
		} catch (SQLException ignored) {
		}

		return false;
	}

	public User getUser(String username, String password) {
		User user = null;

		Connection connection;
		try {
			connection = pool.getConnection();

			Statement statement = connection.createStatement();
			statement.executeQuery("USE " + DBInfo.MYSQL_DATABASE_NAME);

			String query = "SELECT * FROM " + DBContract.UserTable.TABLE_NAME +
					" WHERE " + DBContract.UserTable.COLUMN_NAME_USERNAME + " = ?" + " AND " +
					DBContract.UserTable.COLUMN_NAME_PASSWORD + " = ?";

			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);

			ResultSet resultSet = preparedStatement.executeQuery();
			resultSet.next();
			user = fetchUser(resultSet);

			statement.close();
			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			System.out.println("Something wrong in select from users table");
			System.out.println(e.getCause());
		}

		return user;
	}

	public User getUserById(int userId) {
		User user = null;

		Connection connection;
		try {
			connection = pool.getConnection();

			Statement statement = connection.createStatement();
			statement.executeQuery("USE " + DBInfo.MYSQL_DATABASE_NAME);

			String query = "SELECT * FROM " + DBContract.UserTable.TABLE_NAME +
					" WHERE " + DBContract.UserTable.COLUMN_NAME_ID + " = ?";

			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, userId);

			ResultSet resultSet = preparedStatement.executeQuery();
			resultSet.next();
			user = fetchUser(resultSet);

			statement.close();
			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			System.out.println("Something wrong in select from users table");
		}

		return user;
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
		user.setUserId(resultSet.getInt(DBContract.UserTable.COLUMN_NAME_ID));
		user.setFirstName(resultSet.getString(DBContract.UserTable.COLUMN_NAME_FIRST_NAME));
		user.setLastName(resultSet.getString(DBContract.UserTable.COLUMN_NAME_LAST_NAME));
		user.setUsername(resultSet.getString(DBContract.UserTable.COLUMN_NAME_USERNAME));
		user.setPassword(resultSet.getString(DBContract.UserTable.COLUMN_NAME_PASSWORD));
		user.setGender(resultSet.getString(DBContract.UserTable.COLUMN_NAME_GENDER));
		user.setCountry(resultSet.getString(DBContract.UserTable.COLUMN_NAME_COUNTRY));
		user.setPicture(resultSet.getString(DBContract.UserTable.COLUMN_NAME_PICTURE));
		user.setDateOfBirth(resultSet.getDate(DBContract.UserTable.COLUMN_NAME_DATE_OF_BIRTH));

		return user;
	}
}