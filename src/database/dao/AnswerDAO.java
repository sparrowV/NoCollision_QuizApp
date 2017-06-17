package database.dao;


import database.DBContract;
import database.DBInfo;
import database.bean.Answer;
import database.bean.AnswerMatch;
import database.bean.AnswerMultipleChoice;
import database.bean.AnswerPlain;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AnswerDAO {
	private DataSource pool;

	public AnswerDAO(DataSource pool) {
		this.pool = pool;
	}

	public Answer getAnswerByQuestionId(int questionId) {
		Answer result = null;
		Connection connection = null;
		try {
			// Get the connection from the pool.
			connection = pool.getConnection();

			Statement statement = connection.createStatement();
			statement.executeQuery("USE " + DBInfo.MYSQL_DATABASE_NAME);

			// Prepare and execute 'SELECT' query.
			String query = "SELECT * FROM " + DBContract.AnswerQuestionTable.TABLE_NAME +
					" JOIN " + DBContract.AnswerTable.TABLE_NAME + " ON " +
					DBContract.AnswerTable.TABLE_NAME + "." + DBContract.AnswerTable.COLUMN_NAME_ANSWER_ID + " = " +
					DBContract.AnswerQuestionTable.TABLE_NAME + "." + DBContract.AnswerQuestionTable.COLUMN_NAME_ANSWER_ID +
					" WHERE " + DBContract.AnswerQuestionTable.TABLE_NAME + "." +
					DBContract.AnswerQuestionTable.COLUMN_NAME_QUESTION_ID + " = ?;";

			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, questionId);
			ResultSet resultSet = preparedStatement.executeQuery();
			result = fetchAnswer(resultSet);

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

	private Answer fetchAnswer(ResultSet resultSet) throws SQLException {

		resultSet.next();
		int typeId = resultSet.getInt(DBContract.AnswerTable.COLUMN_NAME_TYPE_ID);
		boolean isText = resultSet.getBoolean(DBContract.AnswerTable.COLUMN_NAME_IS_TEXT);


		switch (typeId) {
			case AnswerPlain.TYPE:
				ArrayList<String> answers = new ArrayList<String>();

				do {
					answers.add(resultSet.getString(DBContract.AnswerTable.COLUMN_NAME_ANSWER_TEXT));
				} while (resultSet.next());
				return new AnswerPlain(answers);

			case AnswerMultipleChoice.TYPE:
				HashMap<String, Boolean> choices = new HashMap<>();

				do {
					String str = resultSet.getString(DBContract.AnswerTable.COLUMN_NAME_ANSWER_TEXT);
					boolean bool = resultSet.getBoolean(DBContract.AnswerTable.COLUMN_NAME_IS_CORRECT);
					choices.put(str, bool);
				} while (resultSet.next());
				return new AnswerMultipleChoice(choices, isText);
			case AnswerMatch.TYPE:
				HashMap<String, String> pairs = new HashMap<>();

				do {
					String str = resultSet.getString(DBContract.AnswerTable.COLUMN_NAME_ANSWER_TEXT);
					String str2 = resultSet.getString(DBContract.AnswerTable.COLUMN_NAME_ANSWER_TEXT2);
					pairs.put(str, str2);
				} while (resultSet.next());
				return new AnswerMatch(pairs, isText);
			default:
				return null;
		}
	}


}
