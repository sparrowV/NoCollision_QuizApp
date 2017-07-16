package database.dao;


import database.DBContract;
import database.DBInfo;
import database.bean.Badge;
import database.bean.Leaderboard;
import model.UserManager;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LeaderboardDAO {

	private DataSource pool;
	private UserManager userManager;

	public LeaderboardDAO(DataSource pool, UserManager userManager) {
		this.userManager = userManager;
		this.pool = pool;
	}

	public Leaderboard getLeaderboardByQuizId(int quizId) {
		Leaderboard board = new Leaderboard(quizId);
		Connection connection = null;
		try {
			// Get the connection from the pool.
			connection = pool.getConnection();

			Statement statement = connection.createStatement();
			statement.executeQuery("USE " + DBInfo.MYSQL_DATABASE_NAME);

			// Prepare and execute 'SELECT' query.
			String query = "SELECT " + DBContract.UserQuizHistoryTable.COLUMN_NAME_USER_ID + ", MAX(" +
					DBContract.UserQuizHistoryTable.COLUMN_NAME_SCORE + ") AS score" +
					" FROM " + DBContract.UserQuizHistoryTable.TABLE_NAME +
					" WHERE " + DBContract.UserQuizHistoryTable.COLUMN_NAME_QUIZ_ID + " = ? " +
					" GROUP BY " + DBContract.UserQuizHistoryTable.COLUMN_NAME_USER_ID +
					" ORDER BY MAX(" + DBContract.UserQuizHistoryTable.COLUMN_NAME_SCORE + ") " +
					DBContract.UserQuizHistoryTable.COLUMN_NAME_DURATION;

			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, quizId);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				int userId = resultSet.getInt(DBContract.UserQuizHistoryTable.COLUMN_NAME_USER_ID);
				double score = resultSet.getDouble(DBContract.UserQuizHistoryTable.COLUMN_NAME_SCORE);
				board.addUser(userManager.getUserById(userId), score);
			}

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
		return board;
	}

}
