package database.dao;


import database.DBContract;
import database.bean.Answer;
import database.bean.Question;
import model.AnswerManager;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class QuestionDAO {
	private DataSource pool;
	private AnswerManager answerManager;
	private HashMap<Integer, String> questionTypeMapping;
	private String databaseName;

	public QuestionDAO(DataSource pool, String databaseName, AnswerManager answerManager) {
		this.pool = pool;
		this.answerManager = answerManager;
	}

	public Question getQuestionById(int questionId) {
		Question question = null;
		Connection connection = null;
		try {
			// Get the connection from the pool.
			connection = pool.getConnection();

			Statement statement = connection.createStatement();
			statement.executeQuery("USE " + databaseName);

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
			statement.executeQuery("USE " + databaseName);

			// Prepare and execute 'SELECT' query.
			String query = "SELECT * FROM " + DBContract.QuestionTable.TABLE_NAME + " JOIN " +
					DBContract.QuestionQuizTable.TABLE_NAME + " ON " +
					DBContract.QuestionTable.TABLE_NAME + "." + DBContract.QuestionTable.COLUMN_NAME_QUESTION_ID + " = " +
					DBContract.QuestionQuizTable.TABLE_NAME + "." + DBContract.QuestionQuizTable.COLUMN_NAME_QUESTION_ID +
					" WHERE " + DBContract.QuestionQuizTable.TABLE_NAME + "."
					+ DBContract.QuestionQuizTable.COLUMN_NAME_QUIZ_ID + " = ? "
					+ "ORDER BY " + DBContract.QuestionQuizTable.TABLE_NAME + "." + DBContract.QuestionQuizTable.COLUMN_NAME_QUESTION_ID + " ASC;";


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

		return res;
	}

	/*
	 * Inserts the given question into the database
	 *
	 * @param question the question to be inserted
	 * @throws SQLException if connection can't be established
	 */

	public int addQuestion(Question question) throws SQLException {
		Connection connection = null;
		int id = 0;
		try {
			connection = pool.getConnection();

			Statement statement = connection.createStatement();
			statement.executeQuery("USE " + databaseName);

			// query inserting into users table
			String query = "INSERT INTO " + DBContract.QuestionTable.TABLE_NAME + " " + "(" +
					DBContract.QuestionTable.COLUMN_NAME_QUESTION_TEXT + ", " +
					DBContract.QuestionTable.COLUMN_NAME_BLANK_TEXT + ", " +
					DBContract.QuestionTable.COLUMN_NAME_MEDIA + ") VALUES " +
					"(?, ?, ?);";

			PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

			preparedStatement.setString(1, question.getQuestion());
			preparedStatement.setString(2, question.getFillText());
			preparedStatement.setString(3, question.getMedia());
			preparedStatement.executeUpdate();
			ResultSet keys = preparedStatement.getGeneratedKeys();
			keys.next();
			id = keys.getInt(1);

			preparedStatement.close();
			statement.close();
		} catch (SQLException e) {
			//	System.out.println("AddQuestionError");
			//	System.out.println(e.getMessage());
		} finally {
			if (connection != null) try {
				// Returns the connection to the pool.
				connection.close();
			} catch (Exception ignored) {
			}
		}
		assert id > 0;
		answerManager.addAnswerToQuestion(question.getAnswer(), id);
		return id;
	}

	public void addQuestionQuizRelation(int questionId, int quizId, int index) {
		Connection connection = null;
		try {
			connection = pool.getConnection();

			Statement statement = connection.createStatement();
			statement.executeQuery("USE " + databaseName);

			// query inserting into users table
			String query = "INSERT INTO " + DBContract.QuestionQuizTable.TABLE_NAME + " " + "(" +
					DBContract.QuestionQuizTable.COLUMN_NAME_QUESTION_ID + ", " +
					DBContract.QuestionQuizTable.COLUMN_NAME_QUIZ_ID + ", " +
					DBContract.QuestionQuizTable.COLUMN_NAME_INDEX_ID + ") " +
					"VALUES (?, ?, ?);";

			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, questionId);
			preparedStatement.setInt(2, quizId);
			preparedStatement.setInt(3, index);

			preparedStatement.executeUpdate();

			preparedStatement.close();
			statement.close();
		} catch (SQLException e) {
			//	System.out.println("AddQuestionQuizError");
			//	System.out.println(e.getMessage());
		} finally {
			if (connection != null) try {
				// Returns the connection to the pool.
				connection.close();
			} catch (Exception ignored) {
			}
		}
	}


	private Question fetchQuestion(ResultSet resultSet) throws SQLException {
		int questionId = resultSet.getInt(DBContract.QuestionTable.COLUMN_NAME_QUESTION_ID);
		String questionText = resultSet.getString(DBContract.QuestionTable.COLUMN_NAME_QUESTION_TEXT);
		String blankText = resultSet.getString(DBContract.QuestionTable.COLUMN_NAME_BLANK_TEXT);
		String media = resultSet.getString(DBContract.QuestionTable.COLUMN_NAME_MEDIA);
		Answer answer = answerManager.getAnswerByQuestionId(questionId);

		return new Question(questionId, questionText, media, blankText, answer);
	}

}
