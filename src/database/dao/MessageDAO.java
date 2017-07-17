package database.dao;

import database.DBContract;
import database.DBInfo;

import javax.sql.DataSource;
import java.sql.*;

/**
 * Created by m1sho on 12.07.2017.
 */
public class MessageDAO {

	private DataSource pool;

	public MessageDAO(DataSource pool) {
		this.pool = pool;
	}

	/**
	 * sends the message from currentUser to friend
	 *
	 * @param currentUserId
	 * @param friendID
	 * @param message
	 */
	public void sendMessage(int currentUserId, int friendID, String message) {
		Connection connection = null;

		try {
			connection = pool.getConnection();
			Statement statement = connection.createStatement();
			statement.executeQuery("USE " + DBInfo.MYSQL_DATABASE_NAME);

			PreparedStatement preparedStatement = connection.prepareStatement(DBContract.Messages.SQL.SEND_MESSAGE_QUERY);
			preparedStatement.setString(1, Integer.toString(currentUserId));
			preparedStatement.setString(2, String.valueOf(friendID));
			preparedStatement.setString(3, message);

			preparedStatement.executeUpdate();

			preparedStatement.close();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * returns the chat history
	 */
	public String getChatHistory(int currentUserId, int friendUserID, String friendName) {
		Connection connection = null;
		String html = "";

		try {
			connection = pool.getConnection();
			Statement statement = connection.createStatement();
			statement.executeQuery("USE " + DBInfo.MYSQL_DATABASE_NAME);

			PreparedStatement preparedStatement = connection.prepareStatement(DBContract.Messages.SQL.GET_CHAT_HISTORY);
			preparedStatement.setString(1, String.valueOf(currentUserId));
			preparedStatement.setString(2, String.valueOf(friendUserID));
			preparedStatement.setString(3, String.valueOf(currentUserId));
			preparedStatement.setString(4, String.valueOf(friendUserID));

			ResultSet resultSet = preparedStatement.executeQuery();
			html += "";
			while (resultSet.next()) {


				String id = resultSet.getString(DBContract.Messages.ID);
				String friend1 = resultSet.getString(DBContract.Messages.FRIEND_ONE);
				String friend2 = resultSet.getString(DBContract.Messages.FRIEND_TWO);
				String message = resultSet.getString(DBContract.Messages.MESSAGE);

				/*System.out.println("message: " + message);
				System.out.println("--------");
				System.out.println("id " + id);
				System.out.println("friend1 " + friend1);
				System.out.println("frined2 " + friend2);*/

				int friend1_ID = Integer.parseInt(friend1);
				int friend2_ID = Integer.parseInt(friend2);

				if (friend1_ID == currentUserId && friend2_ID == friendUserID) {
					html += "You:\n";
				} else if (friend1_ID == friendUserID && friend2_ID == currentUserId) {
					html += friendName;
					html += ":";
					html += "\n";
				}
				html += message + "\n";
				html += "\n";

			}
			resultSet.close();
			preparedStatement.close();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			return html.toString();
		}
	}
}
