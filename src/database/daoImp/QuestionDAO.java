package database.daoImp;


import database.DBInfo;
import database.bean.Question;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class QuestionDAO {
	private DataSource pool;

	public QuestionDAO(DataSource pool) {
		this.pool = pool;
	}

	//getQuestionsByQuiz

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


	//fetchQuestion(typeId, questionId)

}
