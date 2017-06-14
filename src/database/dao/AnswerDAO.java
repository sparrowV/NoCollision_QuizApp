package database.dao;


import database.DBContract;
import database.DBInfo;
import database.bean.*;

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

	public List<Answer> getAnswersByQuestionId(Integer questionId, Integer typeId) {
		ArrayList<Answer> res = new ArrayList<>();
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
					DBContract.AnswerQuestionTable.COLUMN_NAME_QUESTION_ID + " = 4;";

			System.out.println(query);
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			res = fetchAnswers(resultSet, typeId);

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
		return res;
	}

	private ArrayList<Answer> fetchAnswers(ResultSet resultSet, int typeId) throws SQLException {
		ArrayList<Answer> res = new ArrayList<>();
		if (typeId == AnswerPlain.TYPE) {
			while (resultSet.next()) {
				res.add(new AnswerPlain(resultSet.getString(DBContract.AnswerTable.COLUMN_NAME_ANSWER_TEXT1)));
			}
			return res;
		} else if (typeId == AnswerMultipleChoice.TYPE) {
			while (resultSet.next()) {
				res.add(new AnswerMultipleChoice(resultSet.getString(DBContract.AnswerTable.COLUMN_NAME_ANSWER_TEXT1),
						resultSet.getBoolean(DBContract.AnswerTable.COLUMN_NAME_IS_CORRECT)));
			}
			return res;
		}
		return null;
	}


}
