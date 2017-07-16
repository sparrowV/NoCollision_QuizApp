package database.dao;


import database.DBContract;
import database.DBInfo;
import database.bean.Category;
import database.bean.Question;
import database.bean.Quiz;
import model.QuestionManager;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuizDAO {
	private DataSource pool;
	private QuestionManager questionManager;

	public QuizDAO(DataSource pool, QuestionManager questionManager) {
		this.pool = pool;
		this.questionManager = questionManager;
	}

	/**
	 * Returns a list of quizzes from database for particular author.
	 *
	 * @return list of quizzes.
	 */
	public List<Quiz> getQuizzes(int authorId) {
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
	public int addQuiz(Quiz quiz) throws SQLException {
		Connection connection = null;
		int id = 0;
		try {
			connection = pool.getConnection();

			Statement statement = connection.createStatement();
			statement.executeQuery("USE " + DBInfo.MYSQL_DATABASE_NAME);

			// query inserting into quizzes table
			String query = "INSERT INTO " + DBContract.QuizTable.TABLE_NAME + " " + "(" +
					DBContract.QuizTable.COLUMN_NAME_AUTHOR_ID + ", " +
					DBContract.QuizTable.COLUMN_NAME_TITLE + ", " +
					DBContract.QuizTable.COLUMN_NAME_DATA_CREATED + ", " +
					DBContract.QuizTable.COLUMN_NAME_RANDOMIZED_ORDER + "," +
					DBContract.QuizTable.COLUMN_NAME_MULTIPLE_PAGES + "," +
					DBContract.QuizTable.COLUMN_NAME_IMMEDIATE_CORRECTION + "," +
					DBContract.QuizTable.COLUMN_NAME_CATEGORY_ID + ")" +

					"VALUES (?,?,?,?,?,?,?);";


			PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, quiz.getAuthorId());
			preparedStatement.setString(2, quiz.getTitle());
			preparedStatement.setDate(3, new java.sql.Date(quiz.getDateCreated().getTime()));  // Convert Java date to SQL date.
			preparedStatement.setBoolean(4, quiz.getIsRandomizedOrder());
			preparedStatement.setBoolean(5, quiz.getIsMultiplePages());
			preparedStatement.setBoolean(6, quiz.getIsImmediateCorrection());
			preparedStatement.setInt(7, quiz.getCategoryId());
			preparedStatement.executeUpdate();

			ResultSet keys = preparedStatement.getGeneratedKeys();
			keys.next();
			id = keys.getInt(1);

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

		List<Question> questions = quiz.getQuestions();

		for (int i = 0; i < questions.size(); i++) {
			questionManager.addQuestionToQuiz(questions.get(i), id, i + 1);
		}

		assert id > 0;
		return id;
	}

	public void deleteQuiz(int quizId) {
		Connection connection = null;
		try {
			connection = pool.getConnection();

			Statement statement = connection.createStatement();
			statement.executeQuery("USE " + DBInfo.MYSQL_DATABASE_NAME);

			// delete query
			String deleteUserQuery = "DELETE FROM " + DBContract.QuizTable.TABLE_NAME + " WHERE " +
					DBContract.QuizTable.COLUMN_NAME_ID + " =?;";

			PreparedStatement preparedStatement = connection.prepareStatement(deleteUserQuery);
			preparedStatement.setInt(1, quizId);

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

	public Quiz getQuizById(int id) {
		Quiz quiz = null;

		Connection connection;
		try {
			connection = pool.getConnection();

			Statement statement = connection.createStatement();
			statement.executeQuery("USE " + DBInfo.MYSQL_DATABASE_NAME);

			String query = "SELECT * FROM " + DBContract.QuizTable.TABLE_NAME +
					" WHERE " + DBContract.QuizTable.COLUMN_NAME_ID + " = ?";


			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);


			ResultSet resultSet = preparedStatement.executeQuery();
			resultSet.next();
			quiz = fetchQuiz(resultSet);

			statement.close();
			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			System.out.println("Something wrong in select from quiz table");
		}

		return quiz;
	}

	public List<Quiz> getQuizList() {
		// Create a new empty list.
		List<Quiz> users = new ArrayList<>();

		Connection connection = null;
		try {
			// Get the connection from the pool.
			connection = pool.getConnection();

			Statement statement = connection.createStatement();
			statement.executeQuery("USE " + DBInfo.MYSQL_DATABASE_NAME);

			// Prepare and execute 'SELECT' query.
			String query = "SELECT * FROM " + DBContract.QuizTable.TABLE_NAME + ";";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();

			// Iterate over result set and add products to the list.
			while (resultSet.next()) {
				users.add(fetchQuiz(resultSet));
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

		return users;
	}

	public Quiz getQuizByTitle(String title) {
		Quiz quiz = null;

		Connection connection;
		try {
			connection = pool.getConnection();

			Statement statement = connection.createStatement();
			statement.executeQuery("USE " + DBInfo.MYSQL_DATABASE_NAME);

			String query = "SELECT * FROM " + DBContract.QuizTable.TABLE_NAME +
					" WHERE " + DBContract.QuizTable.COLUMN_NAME_TITLE + " = ?";


			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, title);


			ResultSet resultSet = preparedStatement.executeQuery();
			resultSet.next();
			quiz = fetchQuiz(resultSet);

			statement.close();
			preparedStatement.close();
			connection.close();

		} catch (SQLException e) {
			System.out.println("Something wrong in select from quiz table");
		}

		return quiz;
	}


	public List<Category> getQuizCategories() {
		List<Category> res = new ArrayList<>();

		Connection connection;
		try {
			connection = pool.getConnection();

			Statement statement = connection.createStatement();
			statement.executeQuery("USE " + DBInfo.MYSQL_DATABASE_NAME);

			String query = "SELECT * FROM " + DBContract.QuizCategoryTable.TABLE_NAME +
					" ORDER BY " + DBContract.QuizCategoryTable.COLUMN_NAME_CATEGORY_ID + ";";

			PreparedStatement preparedStatement = connection.prepareStatement(query);

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				int categoryId = resultSet.getInt(DBContract.QuizCategoryTable.COLUMN_NAME_CATEGORY_ID);
				String categoryName = resultSet.getString(DBContract.QuizCategoryTable.COLUMN_NAME_CATEGORY_NAME);
				res.add(new Category(categoryId, categoryName));
			}

			statement.close();
			preparedStatement.close();
			connection.close();
		} catch (SQLException e) {
			System.out.println("Something wrong in select from quiz_categories table");
		}

		return res;
	}

	public Category getQuizCategoryById(int categoryId) {
		Category res = null;

		Connection connection;
		try {
			connection = pool.getConnection();

			Statement statement = connection.createStatement();
			statement.executeQuery("USE " + DBInfo.MYSQL_DATABASE_NAME);

			String query = "SELECT * FROM " + DBContract.QuizCategoryTable.TABLE_NAME +
					" WHERE " + DBContract.QuizCategoryTable.COLUMN_NAME_CATEGORY_ID + " = ? ;";

			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, categoryId);

			ResultSet resultSet = preparedStatement.executeQuery();

			resultSet.next();
			String categoryName = resultSet.getString(DBContract.QuizCategoryTable.COLUMN_NAME_CATEGORY_NAME);
			res = new Category(categoryId, categoryName);

			statement.close();
			preparedStatement.close();
			connection.close();
		} catch (SQLException e) {
			System.out.println("Something wrong in select from quiz_categories table");
		}

		return res;
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
		quiz.setQuizId(resultSet.getInt(DBContract.QuizTable.COLUMN_NAME_ID));
		quiz.setAuthorId(resultSet.getInt(DBContract.QuizTable.COLUMN_NAME_AUTHOR_ID));
		quiz.setTitle(resultSet.getString(DBContract.QuizTable.COLUMN_NAME_TITLE));
		quiz.setDateCreated(resultSet.getDate(DBContract.QuizTable.COLUMN_NAME_DATA_CREATED));
		quiz.setQuestions(questionManager.getQuestionsByQuiz(resultSet.getInt(DBContract.QuizTable.COLUMN_NAME_ID)));
		quiz.setIsRandomizedOrder(resultSet.getBoolean(DBContract.QuizTable.COLUMN_NAME_RANDOMIZED_ORDER));
		quiz.setIsMultiplePages(resultSet.getBoolean(DBContract.QuizTable.COLUMN_NAME_MULTIPLE_PAGES));
		quiz.setIsImmediateCorrection(resultSet.getBoolean(DBContract.QuizTable.COLUMN_NAME_IMMEDIATE_CORRECTION));
		quiz.setCategoryId(resultSet.getInt(DBContract.QuizTable.COLUMN_NAME_CATEGORY_ID));
		return quiz;
	}


}
