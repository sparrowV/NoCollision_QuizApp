package database.dao;


import database.DBContract;
import database.DBInfo;
import database.bean.Quiz;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuizDAO {
	private DataSource pool;
	private QuestionDAO questionDAO;

	public QuizDAO(DataSource pool, QuestionDAO questionDAO) {
		this.pool = pool;
		this.questionDAO = questionDAO;
	}

	/**
	 * Returns a list of quizzes from database for particular author.
	 *
	 * @return list of quizzes.
	 */
	public List<Quiz> getQuizzes(Integer authorId) {
		List<Quiz> quizzes = new ArrayList<>();
		Connection connection = null;
		try {
			// Get the connection from the pool.
			connection = pool.getConnection();

			Statement statement = connection.createStatement();
			statement.executeQuery("USE " + DBInfo.MYSQL_DATABASE_NAME);

			// Prepare and execute 'SELECT' query.
			String query = "SELECT * FROM " + DBContract.QuizTable.TABLE_NAME + " WHERE " +
					DBContract.QuizTable.COLUMN_NAME_AUTHOR_ID + " = ?;";

			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, authorId);
			ResultSet resultSet = preparedStatement.executeQuery();

			// Iterate over result set and add products to the list.
			while (resultSet.next()) {
				quizzes.add(fetchQuiz(resultSet));
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

		return quizzes;


	}


	/**
	 * Adds given quiz to the particular author's quizzes.
	 *
	 * @param quiz
	 * @throws SQLException
	 */
	public void addQuiz(Quiz quiz) throws SQLException {
		Connection connection = null;
		try {
			connection = pool.getConnection();

			Statement statement = connection.createStatement();
			statement.executeQuery("USE " + DBInfo.MYSQL_DATABASE_NAME);

			// query inserting into quizzes table
			String query = "INSERT INTO " + DBContract.QuizTable.TABLE_NAME + " " + "(" +
					DBContract.QuizTable.COLUMN_NAME_AUTHOR_ID + ", " +
					DBContract.QuizTable.COLUMN_NAME_TITLE + ", " +
					DBContract.QuizTable.COLUMN_NAME_DATA_CREATED + ") " +
					"VALUES (?,?,?);";


			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, quiz.getAuthorId());
			preparedStatement.setString(2, quiz.getTitle());
			preparedStatement.setDate(3, new java.sql.Date(quiz.getDateCreated().getTime()));  // Convert Java date to SQL date.
			preparedStatement.executeUpdate();

			preparedStatement.close();
			statement.close();
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


	/**
	 * Creates and returns user from result set.
	 *
	 * @param resultSet
	 * @return quiz
	 * @throws SQLException
	 */
	private Quiz fetchQuiz(ResultSet resultSet) throws SQLException {
		Quiz quiz = new Quiz();

		// Set values.
		quiz.setAuthorId(resultSet.getInt(DBContract.QuizTable.COLUMN_NAME_AUTHOR_ID));
		quiz.setTitle(resultSet.getString(DBContract.QuizTable.COLUMN_NAME_TITLE));
		quiz.setDateCreated(resultSet.getDate(DBContract.QuizTable.COLUMN_NAME_DATA_CREATED));
		quiz.setQuestions(questionDAO.getQuestionsByQuiz(resultSet.getInt(DBContract.QuizTable.COLUMN_NAME_ID)));
		return quiz;
	}

}
