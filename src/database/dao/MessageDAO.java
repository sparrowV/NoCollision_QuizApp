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
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String getChatHistory(int currentUserId, int friendUserID) {
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
			html += "<div class=\"chat\">\n";
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

				html += "<div class=\"single_message\">\n";
				html += "<h3>" + message + "</h3>\n";
				html += "<hr>\n";
				html += "</div>\n";
			}
			html += "</div>\n";
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			return html.toString();
		}
	}
}
