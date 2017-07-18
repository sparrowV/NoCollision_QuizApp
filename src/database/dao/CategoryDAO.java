package database.dao;

import database.DBContract;
import database.DBInfo;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by m1sho on 18.07.2017.
 */
public class CategoryDAO {
	private DataSource pool;

	public CategoryDAO(DataSource pool) {
		this.pool = pool;
	}

	public void addCategory(String newCategory) {
		Connection connection = null;

		try {
			connection = pool.getConnection();
			Statement statement = connection.createStatement();
			statement.executeQuery("USE " + DBInfo.MYSQL_DATABASE_NAME);

			PreparedStatement preparedStatement = connection.prepareStatement(DBContract.QuizCategoryTable.SQL.ADD_NEW_CATEGORY);
			preparedStatement.setString(1, newCategory);

			preparedStatement.executeUpdate();

			preparedStatement.close();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
