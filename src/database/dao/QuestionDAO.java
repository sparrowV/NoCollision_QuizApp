package database.dao;


import database.DBContract;
import database.DBInfo;
import database.bean.Answer;
import database.bean.AnswerPlain;
import database.bean.Question;
import database.bean.QuestionPlain;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class QuestionDAO {
	private DataSource pool;
	private HashMap<Integer, String> questionTypeMapping;

	public QuestionDAO(DataSource pool) {
		this.pool = pool;
		questionTypeMapping = new HashMap<>();
		questionTypeMapping.put(1, DBContract.QuestionPlainTable.TABLE_NAME);
		questionTypeMapping.put(2, DBContract.QuestionMultipleChoiceTable.TABLE_NAME);
	}

	public Question getQuestionById(int typeId, int questionId) {
		Connection connection = null;
		try {
			connection = pool.getConnection();

			Statement statement = connection.createStatement();
			String query = "SELECT * FROM " +
					questionTypeMapping.get(typeId) + " WHERE " +
					"question_id = " + questionId + ";";
			ResultSet resultSet = statement.executeQuery(query);
			statement.close();
			resultSet.next();
			return fetchQuestion(resultSet);
		} catch (SQLException e) {
			e.getStackTrace();
		} finally {
			if (connection != null) try {
				// Returns the connection to the pool.
				connection.close();
			} catch (Exception ignored) {
			}
		}
		return null;
	}

	// to be moved to AnswerDAO
	private List<AnswerPlain> getAnswersPlainById(int questionId) {
		ArrayList<AnswerPlain> res = new ArrayList<>();
		Connection connection = null;
		try {
			connection = pool.getConnection();

			Statement statement = connection.createStatement();
			statement.executeQuery("USE " + DBInfo.MYSQL_DATABASE_NAME);

			String query = "select * from answers_to_questions_plain as atqp join answers_plain ap on atqp.answer_id = ap.answer_id"
					+ " where question_id = " + questionId;
			ResultSet resultSet = statement.executeQuery(query);

			while (resultSet.next()) {
				res.add(new AnswerPlain(resultSet.getString("answer")));
			}

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
		return res;
	}





	/**
	 * Inserts the given question into the database
	 *
	 * @param question the question to be inserted
	 * @throws SQLException if connection can't be established
	 */
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


	private Question fetchQuestion(ResultSet resultSet) throws SQLException {
		int typeId = resultSet.getInt("type_id");
		if (typeId == QuestionPlain.TYPE) {
			return new QuestionPlain(resultSet.getString("question"),
					getAnswersPlainById(resultSet.getInt("question_id")));
		}
		return null;
	}

}
