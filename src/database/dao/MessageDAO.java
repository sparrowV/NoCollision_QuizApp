package database.dao;

import database.DBContract;
import database.DBInfo;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

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

	public void getConversationHistory(int currentUserId,int friendUserID){

	}
}
