package database.dao;

import database.DBContract;
import database.DBInfo;
import database.bean.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by m1sho on 26.06.2017.
 */
public class FriendshipDAO {

	private DataSource pool;

	public FriendshipDAO(DataSource pool) {
		this.pool = pool;
	}

	/**
	 * Returns list of the currentID user's friends
	 */
	public List<User> getFriends(int currentUserId) {
		Connection connection = null;
		List<User> friends = new ArrayList<>();
		try {
			connection = pool.getConnection();

			Statement statement = connection.createStatement();
			statement.executeQuery("USE " + DBInfo.MYSQL_DATABASE_NAME);

			// this query selects A->B friendship where A are we and the status of this relationship is 1 (active, friends)
			PreparedStatement preparedStatement1 = connection.prepareStatement(DBContract.Friends.SQL.GET_FRIENDS_FIRST_QUERY);
			preparedStatement1.setString(1, Integer.toString(currentUserId));
			preparedStatement1.setString(2, String.valueOf(DBContract.Friends.STATUS_ACTIVE));

			// B->A friendship where A are we and relationship status is 1/0 (active or pending)
			PreparedStatement preparedStatement2 = connection.prepareStatement(DBContract.Friends.SQL.GET_FRIENDS_SECOND_QUERY);
			preparedStatement2.setString(1, Integer.toString(currentUserId));
			preparedStatement2.setString(2, String.valueOf(DBContract.Friends.STATUS_ACTIVE));

			getFriendsStatementResult(preparedStatement1, friends);
			getFriendsStatementResult(preparedStatement2, friends);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			return friends;
		}
	}

	/**
	 * Sends friend request from currentUserId  to requestUserId
	 */
	public void sendFriendRequest(int currentUserId, int requestUserId) {
		Connection connection = null;
		try {
			connection = pool.getConnection();
			Statement statement = connection.createStatement();
			statement.executeQuery("USE " + DBInfo.MYSQL_DATABASE_NAME);
			String query = "INSERT INTO " + DBContract.Friends.TABLE_NAME + " (" +
					DBContract.Friends.FRIEND_ONE + ", " +
					DBContract.Friends.FRIEND_TWO + ") " +
					"VALUES (?,?);";
			PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, Integer.toString(currentUserId));
			preparedStatement.setString(2, Integer.toString(requestUserId));
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns all received friend request for the user (currentUserId)
	 */
	public List<User> getReceivedFriendRequests(int currentUserId) {
		List<User> pendingRequests = new ArrayList<>();
		Connection connection = null;
		try {
			connection = pool.getConnection();
			Statement statement = connection.createStatement();
			statement.executeQuery("USE " + DBInfo.MYSQL_DATABASE_NAME);

			PreparedStatement preparedStatement = connection.prepareStatement(DBContract.Friends.SQL.GET_FRIENDS_SECOND_QUERY);
			preparedStatement.setString(1, Integer.toString(currentUserId));
			preparedStatement.setString(2, String.valueOf(DBContract.Friends.STATUS_REQUEST));
			getFriendsStatementResult(preparedStatement, pendingRequests);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pendingRequests;
	}

	/**
	 * Confirms sent friend request from requestUserId to currentUserId
	 */
	public void acceptRequest(int currentUserId, int requestUserId) {
		Connection connection = null;
		try {
			connection = pool.getConnection();
			Statement statement = connection.createStatement();
			statement.executeQuery("USE " + DBInfo.MYSQL_DATABASE_NAME);

			PreparedStatement preparedStatement = connection.prepareStatement(DBContract.Friends.SQL.ACCEPT_REQUEST_QUERY);
			preparedStatement.setString(1, Integer.toString(currentUserId));
			preparedStatement.setString(2, Integer.toString(requestUserId));
			preparedStatement.executeUpdate();

			preparedStatement.close();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * rejects the friendship request
	 */
	public void rejectRequest(int currentUserId, int requestUserId) {
		Connection connection = null;
		try {
			connection = pool.getConnection();
			Statement statement = connection.createStatement();
			statement.executeQuery("USE " + DBInfo.MYSQL_DATABASE_NAME);

			PreparedStatement preparedStatement = connection.prepareStatement(DBContract.Friends.SQL.REJECT_REQUEST_QUERY);
			preparedStatement.setString(1, Integer.toString(currentUserId));
			preparedStatement.setString(2, Integer.toString(requestUserId));
			preparedStatement.executeUpdate();
			preparedStatement.close();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean areFriends(int currentUserId, int friendUserId) {
		Connection connection = null;
		try {
			connection = pool.getConnection();
			Statement statement = connection.createStatement();
			statement.executeQuery("USE " + DBInfo.MYSQL_DATABASE_NAME);

			PreparedStatement preparedStatement = connection.prepareStatement(DBContract.Friends.SQL.ARE_FRIENDS_QUERY);
			preparedStatement.setString(1, Integer.toString(currentUserId));
			preparedStatement.setString(2, Integer.toString(friendUserId));
			preparedStatement.setString(3, Integer.toString(currentUserId));
			preparedStatement.setString(4, Integer.toString(friendUserId));

			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				return true;
			}
			resultSet.close();
			preparedStatement.close();
			statement.close();
			connection.close();
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * loads following selection to List
	 */
	private void getFriendsStatementResult(PreparedStatement preparedStatement, List<User> friends) {
		ResultSet resultSet = null;
		try {
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				friends.add(fetchUser(resultSet));//save users as friends
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * private parsing method
	 */
	private User fetchUser(ResultSet resultSet) throws SQLException {
		User user = new User();

		// Set values.
		user.setUserId(resultSet.getInt(DBContract.UserTable.COLUMN_NAME_ID));
		user.setFirstName(resultSet.getString(DBContract.UserTable.COLUMN_NAME_FIRST_NAME));
		user.setLastName(resultSet.getString(DBContract.UserTable.COLUMN_NAME_LAST_NAME));
		user.setUsername(resultSet.getString(DBContract.UserTable.COLUMN_NAME_USERNAME));
		user.setGender(resultSet.getString(DBContract.UserTable.COLUMN_NAME_GENDER));
		user.setCountry(resultSet.getString(DBContract.UserTable.COLUMN_NAME_COUNTRY));
		user.setPicture(resultSet.getString(DBContract.UserTable.COLUMN_NAME_PICTURE));
		user.setDateOfBirth(resultSet.getDate(DBContract.UserTable.COLUMN_NAME_DATE_OF_BIRTH));
		return user;
	}
}
