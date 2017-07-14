package database.dao;

import database.DBContract;
import database.DBInfo;
import database.bean.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by m1sho on 14.07.2017.
 */
public class ChallengeDAO {

	private DataSource pool;

	public ChallengeDAO(DataSource pool) {
		this.pool = pool;
	}

	public void sendChallenge(int currentUserID, int friendUserID, int quizID) {
		challengeTask(currentUserID, friendUserID, quizID, DBContract.Challenges.SQL.SEND_CHALLENGE);
	}

	public void acceptChalenge(int currentUserID, int friendUserID, int quizID) {
		challengeTask(currentUserID, friendUserID, quizID, DBContract.Challenges.SQL.ACCEPT_CHALLENGE);
	}

	private void challengeTask(int currentUserID, int friendUserID, int quizID, String query) {
		Connection connection = null;
		try {
			connection = pool.getConnection();

			Statement statement = connection.createStatement();
			statement.executeQuery("USE " + DBInfo.MYSQL_DATABASE_NAME);

			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, String.valueOf(currentUserID));
			preparedStatement.setString(2, String.valueOf(friendUserID));
			preparedStatement.setString(3, String.valueOf(quizID));
			preparedStatement.executeUpdate();
			System.out.println("");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
