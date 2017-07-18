package database.dao;

import database.DBContract;
import database.bean.Badge;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BadgeDAO {

	private DataSource pool;
	private String databaseName;

	public BadgeDAO(DataSource pool, String databaseName) {
		this.pool = pool;
	}

	public void addBadge(Badge badge) {
		Connection connection = null;
		try {
			connection = pool.getConnection();

			Statement statement = connection.createStatement();
			statement.executeQuery("USE " + databaseName);

			// query inserting into users table
			String query = "INSERT INTO " + DBContract.BadgeTable.TABLE_NAME + " " + "(" +
					DBContract.BadgeTable.COLUMN_NAME_BADGE_NAME + ", " +
					DBContract.BadgeTable.COLUMN_NAME_DESCRIPTION + ", " +
					DBContract.BadgeTable.COLUMN_NAME_CATEGORY_ID + ", " +
					DBContract.BadgeTable.COLUMN_NAME_QUIZZES_NEEDED + ", " +
					DBContract.BadgeTable.COLUMN_NAME_XP_NEEDED + ")" +
					" VALUES (?, ?, ?, ?, ?);";

			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, badge.getBadgeName());
			preparedStatement.setString(2, badge.getDescription());
			preparedStatement.setInt(3, badge.getCategoryId());
			preparedStatement.setInt(4, badge.getQuizzesNeeded());
			preparedStatement.setDouble(5, badge.getXpNeeded());

			preparedStatement.executeUpdate();

			preparedStatement.close();
			statement.close();
		} catch (SQLException e) {
			e.getStackTrace();
			System.out.println("AddBadgeError");
			System.out.println(e.getMessage());
		} finally {
			if (connection != null) try {
				// Returns the connection to the pool.
				connection.close();
			} catch (Exception ignored) {
			}
		}
	}

	public void updateBadgesByUserId(int userId) {
		Connection connection = null;
		try {
			connection = pool.getConnection();

			Statement statement = connection.createStatement();
			statement.executeQuery("USE " + databaseName);

			String q = DBContract.QuizTable.TABLE_NAME;
			String q_catId = DBContract.QuizTable.COLUMN_NAME_CATEGORY_ID;
			String q_quizId = DBContract.QuizTable.COLUMN_NAME_ID;

			String uqh = DBContract.UserQuizHistoryTable.TABLE_NAME;
			String uqh_quizId = DBContract.UserQuizHistoryTable.COLUMN_NAME_QUIZ_ID;
			String uqh_userId = DBContract.UserQuizHistoryTable.COLUMN_NAME_USER_ID;
			String uqh_xp = DBContract.UserQuizHistoryTable.COLUMN_NAME_XP;

			String b = DBContract.BadgeTable.TABLE_NAME;
			String b_badgeId = DBContract.BadgeTable.COLUMN_NAME_BADGE_ID;
			String b_xpNeeded = DBContract.BadgeTable.COLUMN_NAME_XP_NEEDED;
			String b_quizzesNeeded = DBContract.BadgeTable.COLUMN_NAME_QUIZZES_NEEDED;
			String b_catId = DBContract.BadgeTable.COLUMN_NAME_CATEGORY_ID;

			String ub = DBContract.UserBadgeTable.TABLE_NAME;
			String ub_userId = DBContract.UserBadgeTable.COLUMN_NAME_USER_ID;
			String ub_badgeId = DBContract.UserBadgeTable.COLUMN_NAME_BADGE_ID;


			String query = "INSERT INTO " + ub +
					" SELECT t1." + uqh_userId + ", b." + b_badgeId +
					" FROM ((SELECT " + uqh_userId + ", " +
					q_catId + ", " +
					"COUNT(1) AS quizzesTaken, " +
					"SUM(" + uqh_xp + ") as xpEarned" +
					" FROM " + uqh +
					" INNER JOIN " + q +
					" ON " + uqh + "." + uqh_quizId + " = "
					+ q + "." + q_quizId +
					" WHERE " + uqh_userId + " = ? " +
					" GROUP BY " + uqh_userId + ", " + q_catId +
					") UNION (" +
					" SELECT " + uqh_userId + ", " +
					"NULL AS " + q_catId + ", " +
					"COUNT(1) AS quizzesTaken, " +
					"SUM(" + uqh_xp + ") as xpEarned" +
					" FROM " + uqh +
					" INNER JOIN " + q +
					" ON " + uqh + "." + uqh_quizId + " = "
					+ q + "." + q_quizId +
					" WHERE " + uqh_userId + " = ? " +
					" GROUP BY " + uqh_userId + ")) AS t1" +
					" INNER JOIN " + b + " AS b ON IFNULL(t1." + q_catId + ", 0) = IFNULL(b." + b_catId + ", 0)" +
					" AND t1.xpEarned >= b." + b_xpNeeded +
					" AND t1.quizzesTaken >= b." + b_quizzesNeeded +
					" LEFT JOIN " + ub + " AS ub ON ub." + ub_userId + " = t1." + uqh_userId + " AND ub." + ub_badgeId + " = b." + b_badgeId +
					" WHERE ub." + ub_userId + " IS NULL";

			System.out.println(query);
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, userId);
			preparedStatement.setInt(2, userId);

			preparedStatement.executeUpdate();

			preparedStatement.close();
			statement.close();
		} catch (SQLException e) {
			e.getStackTrace();
			System.out.println("updateBadgeError");
			System.out.println(e.getMessage());
		} finally {
			if (connection != null) try {
				// Returns the connection to the pool.
				connection.close();
			} catch (Exception ignored) {
			}
		}
	}

	public List<Badge> getBadgesByUserId(int userId) {
		List<Badge> result = new ArrayList<>();
		Connection connection = null;
		try {
			// Get the connection from the pool.
			connection = pool.getConnection();

			Statement statement = connection.createStatement();
			statement.executeQuery("USE " + databaseName);

			// Prepare and execute 'SELECT' query.
			String query = "SELECT * FROM " + DBContract.UserBadgeTable.TABLE_NAME +
					" JOIN " + DBContract.BadgeTable.TABLE_NAME + " ON " +
					DBContract.UserBadgeTable.TABLE_NAME + "." + DBContract.UserBadgeTable.COLUMN_NAME_BADGE_ID + " = " +
					DBContract.BadgeTable.TABLE_NAME + "." + DBContract.BadgeTable.COLUMN_NAME_BADGE_ID +
					" WHERE " + DBContract.UserBadgeTable.TABLE_NAME + "." +
					DBContract.UserBadgeTable.COLUMN_NAME_USER_ID + " = ?";

			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, userId);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				result.add(fetchBadge(resultSet));
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
		return result;
	}

	private Badge fetchBadge(ResultSet resultSet) throws SQLException {
		int badgeId = resultSet.getInt(DBContract.BadgeTable.COLUMN_NAME_BADGE_ID);
		String badgeName = resultSet.getString(DBContract.BadgeTable.COLUMN_NAME_BADGE_NAME);
		String description = resultSet.getString(DBContract.BadgeTable.COLUMN_NAME_DESCRIPTION);
		int quizzesNeeded = resultSet.getInt(DBContract.BadgeTable.COLUMN_NAME_QUIZZES_NEEDED);
		double xpNeeded = resultSet.getInt(DBContract.BadgeTable.COLUMN_NAME_XP_NEEDED);
		int categoryId = resultSet.getInt(DBContract.BadgeTable.COLUMN_NAME_CATEGORY_ID);
		return new Badge(badgeId, badgeName, description, quizzesNeeded, xpNeeded, categoryId);
	}

}
