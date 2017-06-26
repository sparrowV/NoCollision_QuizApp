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
	 * returns list of the currentID user's friends
	 */
	public List<User> getFriends(String currentUserID) {
		Connection connection = null;
		List<User> friends = new ArrayList<>();
		try {
			connection = pool.getConnection();

			Statement statement = connection.createStatement();
			statement.executeQuery("USE " + DBInfo.MYSQL_DATABASE_NAME);

			// this query selects A->B friendship where A are we and the status of this relationship is 1 (active, friends)
			PreparedStatement preparedStatement1 = connection.prepareStatement(DBContract.Friends.SQL.GET_FRIENDS_FIRST_QUERY);
			preparedStatement1.setString(1, currentUserID);
			preparedStatement1.setString(2, String.valueOf(DBContract.Friends.STATUS_ACTIVE));

			// B->A friendship where A are we and relationship status is 1/0 (active or pending)
			PreparedStatement preparedStatement2 = connection.prepareStatement(DBContract.Friends.SQL.GET_FRIENDS_SECOND_QUERY);
			preparedStatement2.setString(1, currentUserID);
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
	 * Sends friend request from currentUserID  to requestUserID
	 */
	public void sendFriendRequest(String currentUserID, String requestUserID) {
		Connection connection = null;
		try {
			connection = pool.getConnection();
			Statement statement = connection.createStatement();
			statement.executeQuery("USE " + DBInfo.MYSQL_DATABASE_NAME);
			String query = "INSERT INTO " + DBContract.Friends.TABLE_NAME+ " (" +
					DBContract.Friends.FRIEND_ONE + ", " +
					DBContract.Friends.FRIEND_TWO + ") " +
					"VALUES (?,?);";
			PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, currentUserID);
			preparedStatement.setString(2, requestUserID);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<User> getRecievedFriendRequests(String currentID) {
		return null;
	}

	public void confirmRequest(String currentUserID, String requestUserID) {

	}

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
