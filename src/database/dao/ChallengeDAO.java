package database.dao;

import database.DBContract;
import database.bean.Challenges;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;

/**
 * Created by m1sho on 14.07.2017.
 */
public class ChallengeDAO {

	private DataSource pool;
	private String databaseName;

	public ChallengeDAO(DataSource pool, String databaseName) {
		this.pool = pool;
		this.databaseName = databaseName;
	}

	/**
	 * @param currentUserID
	 * @return returns the ArrayList of challenges which he/she received
	 */
	public ArrayList<Challenges> getMyChallenges(int currentUserID) {
		Connection connection = null;
		ArrayList<Challenges> myChallenges = new ArrayList<>();
		try {
			connection = pool.getConnection();

			Statement statement = connection.createStatement();
			statement.executeQuery("USE " + databaseName);

			PreparedStatement preparedStatement = connection.prepareStatement(DBContract.Challenges.SQL.GET_MY_CHALLENGES);
			preparedStatement.setString(1, String.valueOf(currentUserID));
			ResultSet resultSet = null;

			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				int challengerUserID = resultSet.getInt(DBContract.UserTable.COLUMN_NAME_ID);
				String challengerUsername = resultSet.getString(DBContract.UserTable.COLUMN_NAME_USERNAME);

				int challengedQuizID = resultSet.getInt(DBContract.QuizTable.COLUMN_NAME_ID);
				String challengedQuizTitle = resultSet.getString(DBContract.QuizTable.COLUMN_NAME_TITLE);
				myChallenges.add(new Challenges(challengerUserID, challengerUsername, challengedQuizID, challengedQuizTitle));
			}
			resultSet.close();
			preparedStatement.close();
			statement.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			return myChallenges;
		}
	}

	/**
	 * @param currentUserID
	 * @param friendUserID
	 * @param quizID        currentUser sends the challenge to friendUser of quizID
	 */
	public void sendChallenge(int currentUserID, int friendUserID, int quizID) {
		challengeTask(currentUserID, friendUserID, quizID, DBContract.Challenges.SQL.SEND_CHALLENGE);
	}

	/**
	 * @param currentUserID
	 * @param friendUserID
	 * @param quizID        accepts sent challenge
	 */
	public void acceptChalenge(int currentUserID, int friendUserID, int quizID) {
		challengeTask(currentUserID, friendUserID, quizID, DBContract.Challenges.SQL.ACCEPT_CHALLENGE);
	}

	/**
	 * @param currentUserID
	 * @param friendUserID
	 * @param quizID
	 * @param query         executes the query string
	 */
	private void challengeTask(int currentUserID, int friendUserID, int quizID, String query) {
		Connection connection = null;
		try {
			connection = pool.getConnection();

			Statement statement = connection.createStatement();
			statement.executeQuery("USE " + databaseName);

			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, String.valueOf(currentUserID));
			preparedStatement.setString(2, String.valueOf(friendUserID));
			preparedStatement.setString(3, String.valueOf(quizID));
			preparedStatement.executeUpdate();

			preparedStatement.close();
			statement.close();
			connection.close();
			System.out.println("");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
