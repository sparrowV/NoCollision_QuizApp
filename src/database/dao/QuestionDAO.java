package database.dao;


import database.DBContract;
import database.DBInfo;
import database.bean.*;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class QuestionDAO {
	private DataSource pool;
	private AnswerDAO answerDAO;
	private HashMap<Integer, String> questionTypeMapping;

	public QuestionDAO(DataSource pool, AnswerDAO answerDAO) {
		this.pool = pool;
		this.answerDAO = answerDAO;
	}

	public Question getQuestionById(Integer questionId) {
		Question question = null;
		Connection connection = null;
		try {
			// Get the connection from the pool.
			connection = pool.getConnection();

			Statement statement = connection.createStatement();
			statement.executeQuery("USE " + DBInfo.MYSQL_DATABASE_NAME);

			// Prepare and execute 'SELECT' query.
			String query = "SELECT * FROM " + DBContract.QuestionTable.TABLE_NAME + " WHERE " +
					DBContract.QuestionTable.COLUMN_NAME_QUESTION_ID + " = ?;";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, questionId);
			ResultSet resultSet = preparedStatement.executeQuery();

			resultSet.next();
			question = fetchQuestion(resultSet);

			// Close statement and result set.
			resultSet.close();
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

		return question;
	}


	public List<Question> getQuestionsByQuiz(int quizId) {
		List<Question> res = new ArrayList<>();
		Connection connection = null;
		try {
			// Get the connection from the pool.
			connection = pool.getConnection();

			Statement statement = connection.createStatement();
			statement.executeQuery("USE " + DBInfo.MYSQL_DATABASE_NAME);

			// Prepare and execute 'SELECT' query.
			String query = "SELECT * FROM " + DBContract.QuestionTable.TABLE_NAME + " JOIN " +
					DBContract.QuestionQuizTable.TABLE_NAME + " ON " +
					DBContract.QuestionTable.TABLE_NAME + "." + DBContract.QuestionTable.COLUMN_NAME_QUESTION_ID + " = " +
					DBContract.QuestionQuizTable.TABLE_NAME + "." + DBContract.QuestionQuizTable.COLUMN_NAME_QUESTION_ID +
					" WHERE " + DBContract.QuestionQuizTable.TABLE_NAME + "."
					+ DBContract.QuestionQuizTable.COLUMN_NAME_QUIZ_ID + " = ?;";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, quizId);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Question question = fetchQuestion(resultSet);
				res.add(question);
			}
			// Close statement and result set.
			resultSet.close();
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

		for (Question q : res) {
			System.out.println(q);
		}
		return res;
	}

	/*
	 * Inserts the given question into the database
	 *
	 * @param question the question to be inserted
	 * @throws SQLException if connection can't be established
	 */
	/*
	public void addQuestion(Question question) throws SQLException {
		Connection connection = null;
		try {
			connection = pool.getConnection();

			Statement statement = connection.createStatement();
			statement.executeQuery("USE " + DBInfo.MYSQL_DATABASE_NAME);
			statement.close();

			PreparedStatement preparedStatement = question.toSql();
			preparedStatement.executeUpdate();

			statement.close();
			preparedStatement.close();
		} catch (SQLException e) {
			e.getStackTrace();
		} finally {
			if (connection != null) try {
				// Returns the connection to the pool.
				connection.close();
			} catch (Exception ignored) {
			}
		}
	}

	*/
	private Question fetchQuestion(ResultSet resultSet) throws SQLException {
		int typeId = resultSet.getInt(DBContract.QuestionTable.COLUMN_NAME_TYPE_ID);
		int questionId = resultSet.getInt(DBContract.QuestionTable.COLUMN_NAME_QUESTION_ID);
		if (typeId == QuestionPlain.TYPE) {
			return new QuestionPlain(resultSet.getString(DBContract.QuestionTable.COLUMN_NAME_QUESTION_TEXT),
					answerDAO.getAnswersByQuestionId(questionId, typeId));
		} else if (typeId == QuestionMultipleChoice.TYPE) {
			return new QuestionMultipleChoice(resultSet.getString(DBContract.QuestionTable.COLUMN_NAME_QUESTION_TEXT),
					answerDAO.getAnswersByQuestionId(questionId, typeId));
		}
		return null;
	}

}
