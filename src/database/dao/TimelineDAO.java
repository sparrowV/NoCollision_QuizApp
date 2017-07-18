package database.dao;

import database.DBContract;
import database.bean.TimelineActivity;
import database.bean.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by m1sho on 16.07.2017.
 */
public class TimelineDAO {
	private DataSource pool;
	private String databaseName;

	public TimelineDAO(DataSource pool, String databaseName) {
		this.pool = pool;
		this.databaseName = databaseName;
	}

	/**
	 * returns the list of news as Timeline
	 *
	 * @param myFriends news is about friends activity
	 */
	public List<TimelineActivity> getTimeline(List<User> myFriends) {
		List<TimelineActivity> newsFeed = new ArrayList<>();

		Connection connection = null;
		try {
			connection = pool.getConnection();

			Statement statement = connection.createStatement();
			statement.executeQuery("USE " + databaseName);

			for (User myFriend : myFriends) {
				PreparedStatement preparedStatement = connection.prepareStatement(DBContract.TimeLine.SQL.GET_FRIENDS_QUIZ_ACTIVITY);
				preparedStatement.setString(1, String.valueOf(myFriend.getUserId()));
				ResultSet resultSet = null;
				resultSet = preparedStatement.executeQuery();

				while (resultSet.next()) {
					int quiz_id = resultSet.getInt(DBContract.QuizTable.COLUMN_NAME_ID);
					String quizTitle = resultSet.getString(DBContract.QuizTable.COLUMN_NAME_TITLE);
					String duration = resultSet.getString(DBContract.UserQuizHistoryTable.COLUMN_NAME_DURATION);
					String score = resultSet.getString(DBContract.UserQuizHistoryTable.COLUMN_NAME_SCORE);
					String xp = resultSet.getString(DBContract.UserQuizHistoryTable.COLUMN_NAME_XP);

					newsFeed.add(new TimelineActivity(myFriend.getUserId(), myFriend.getFirstName() + " " + myFriend.getLastName(), quiz_id, quizTitle, duration, score, xp));
				}

				preparedStatement.close();
			}
			connection.close();
			statement.close();


		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			return newsFeed;
		}
	}
}
