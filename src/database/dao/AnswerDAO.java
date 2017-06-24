package database.dao;


import database.DBContract;
import database.DBInfo;
import database.bean.Answer;
import database.bean.AnswerMatch;
import database.bean.AnswerMultipleChoice;
import database.bean.AnswerPlain;

import javax.sql.DataSource;
import javax.swing.text.html.HTMLDocument;
import java.sql.*;
import java.util.*;

public class AnswerDAO {
	private DataSource pool;

	public AnswerDAO(DataSource pool) {
		this.pool = pool;
	}

	public List<Integer> addAnswerPlain(AnswerPlain answer) {
		Connection connection = null;
		ArrayList<Integer> res = new ArrayList<>();
		try {
			connection = pool.getConnection();

			Statement statement = connection.createStatement();
			statement.executeQuery("USE " + DBInfo.MYSQL_DATABASE_NAME);

			List<String> answers = answer.getAnswers();

			// query inserting into users table
			String query = "INSERT INTO " + DBContract.AnswerTable.TABLE_NAME + " " + "(" +
					DBContract.AnswerTable.COLUMN_NAME_TYPE_ID + ", " +
					DBContract.AnswerTable.COLUMN_NAME_ANSWER_TEXT + ") VALUES ";
			for (int i = 0; i < answers.size() - 1; i++) query += "(?, ?),";
			query += "(?, ?);";
			PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

			int i = 0;
			for (String str : answers) {
				preparedStatement.setInt(i + 1, AnswerPlain.TYPE);
				preparedStatement.setString(i + 2, str);
				i += 2;
			}
			preparedStatement.executeUpdate();
			ResultSet keys = preparedStatement.getGeneratedKeys();

			while (keys.next()) {
				res.add(keys.getInt(1));
			}

			preparedStatement.close();
			statement.close();
		} catch (SQLException e) {
			e.getStackTrace();
			System.out.println("AddAnswerPlainError");
			System.out.println(e.getMessage());
		} finally {
			if (connection != null) try {
				// Returns the connection to the pool.
				connection.close();
			} catch (Exception ignored) {
			}
		}
		return res;
	}

	public List<Integer> addAnswerMultipleChoice(AnswerMultipleChoice answer) {
		Connection connection = null;
		ArrayList<Integer> res = new ArrayList<>();
		try {
			connection = pool.getConnection();

			Statement statement = connection.createStatement();
			statement.executeQuery("USE " + DBInfo.MYSQL_DATABASE_NAME);

			Map<String, Boolean> answers = answer.getAnswers();
			Iterator<String> iterator = answers.keySet().iterator();

			// query inserting into users table
			String query = "INSERT INTO " + DBContract.AnswerTable.TABLE_NAME + " " + "(" +
					DBContract.AnswerTable.COLUMN_NAME_TYPE_ID + ", " +
					DBContract.AnswerTable.COLUMN_NAME_ANSWER_TEXT + ", " +
					DBContract.AnswerTable.COLUMN_NAME_IS_CORRECT + ") VALUES ";
			for (int i = 0; i < answers.size() - 1; i++) query += "(?, ?, ?),";
			query += "(?, ?, ?);";

			PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			int i = 0;
			while (iterator.hasNext()) {
				String str = iterator.next();
				preparedStatement.setInt(i + 1, AnswerMultipleChoice.TYPE);
				preparedStatement.setString(i + 2, str);
				preparedStatement.setBoolean(i + 3, answers.get(str));
				i += 3;
			}
			preparedStatement.executeUpdate();

			ResultSet keys = preparedStatement.getGeneratedKeys();

			while (keys.next()) {
				res.add(keys.getInt(1));
			}

			preparedStatement.close();
			statement.close();
		} catch (SQLException e) {
			e.getStackTrace();
			System.out.println("AddAnswerMultError");
			System.out.println(e.getMessage());
		} finally {
			if (connection != null) try {
				// Returns the connection to the pool.
				connection.close();
			} catch (Exception ignored) {
			}
		}
		return res;
	}

	public List<Integer> addAnswerMatch(AnswerMatch answer) {
		Connection connection = null;
		ArrayList<Integer> res = new ArrayList<>();
		try {
			connection = pool.getConnection();

			Statement statement = connection.createStatement();
			statement.executeQuery("USE " + DBInfo.MYSQL_DATABASE_NAME);

			Map<String, String> answers = answer.getAnswers();
			Iterator<String> iterator = answers.keySet().iterator();

			// query inserting into users table
			String query = "INSERT INTO " + DBContract.AnswerTable.TABLE_NAME + " " + "(" +
					DBContract.AnswerTable.COLUMN_NAME_TYPE_ID + ", " +
					DBContract.AnswerTable.COLUMN_NAME_ANSWER_TEXT + ", " +
					DBContract.AnswerTable.COLUMN_NAME_ANSWER_TEXT2 + ") VALUES ";
			for (int i = 0; i < answers.size() - 1; i++) query += "(?, ?, ?),";
			query += "(?, ?, ?);";

			PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			int i = 0;
			while (iterator.hasNext()) {
				String str = iterator.next();
				preparedStatement.setInt(i + 1, AnswerMatch.TYPE);
				preparedStatement.setString(i + 2, str);
				preparedStatement.setString(i + 3, answers.get(str));
				i += 3;
			}
			preparedStatement.executeUpdate();

			ResultSet keys = preparedStatement.getGeneratedKeys();

			while (keys.next()) {
				res.add(keys.getInt(1));
			}

			preparedStatement.close();
			statement.close();
		} catch (SQLException e) {
			e.getStackTrace();
			System.out.println("AddAnswerMatchError");
			System.out.println(e.getMessage());
		} finally {
			if (connection != null) try {
				// Returns the connection to the pool.
				connection.close();
			} catch (Exception ignored) {
			}
		}
		return res;
	}

	public void addAnswerQuestionRelation(int answerId, int questionId) {
		Connection connection = null;
		try {
			connection = pool.getConnection();

			Statement statement = connection.createStatement();
			statement.executeQuery("USE " + DBInfo.MYSQL_DATABASE_NAME);

			// query inserting into users table
			String query = "INSERT INTO " + DBContract.AnswerQuestionTable.TABLE_NAME + " " + "(" +
					DBContract.AnswerQuestionTable.COLUMN_NAME_ANSWER_ID + ", " +
					DBContract.AnswerQuestionTable.COLUMN_NAME_QUESTION_ID + ") " +
					"VALUES (?, ?);";

			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, answerId);
			preparedStatement.setInt(2, questionId);

			preparedStatement.executeUpdate();

			preparedStatement.close();
			statement.close();
		} catch (SQLException e) {
			e.getStackTrace();
			System.out.println("AddAnswerQuestionError");
			System.out.println(e.getMessage());
		} finally {
			if (connection != null) try {
				// Returns the connection to the pool.
				connection.close();
			} catch (Exception ignored) {
			}
		}
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
				ArrayList<String> answers = new ArrayList<>();

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
