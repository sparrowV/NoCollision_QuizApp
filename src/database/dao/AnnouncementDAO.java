package database.dao;


import database.DBContract;
import database.DBInfo;
import database.bean.Announcement;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class AnnouncementDAO {

	private DataSource pool;

	public AnnouncementDAO(DataSource pool) {
		this.pool = pool;
	}

	public void addAnnouncement(Announcement announcement) {
		Connection connection = null;

		try {
			connection = pool.getConnection();

			Statement statement = connection.createStatement();
			statement.executeQuery("USE " + DBInfo.MYSQL_DATABASE_NAME);

			String query = "INSERT INTO " + DBContract.AnnouncementTable.TABLE_NAME + " (" +
							DBContract.AnnouncementTable.COLUMN_NAME_TEXT + ", " +
							DBContract.AnnouncementTable.COLUMN_NAME_USER_ID + ") " +
							"VALUES(?,?);" ;

			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, announcement.getAnnouncement());
			preparedStatement.setInt(2, announcement.getAdminId());

			preparedStatement.executeUpdate();



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
}
