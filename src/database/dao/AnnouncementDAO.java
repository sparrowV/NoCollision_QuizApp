package database.dao;


import database.DBContract;
import database.bean.Announcement;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnnouncementDAO {
	private DataSource pool;
	private String databaseName;

	public AnnouncementDAO(DataSource pool, String databaseName) {
		this.pool = pool;
		this.databaseName = databaseName;
	}

	public void addAnnouncement(Announcement announcement) {
		Connection connection = null;

		try {
			connection = pool.getConnection();

			Statement statement = connection.createStatement();
			statement.executeQuery("USE " + databaseName);

			String query = "INSERT INTO " + DBContract.AnnouncementTable.TABLE_NAME + " (" +
					DBContract.AnnouncementTable.COLUMN_NAME_TEXT + ", " +
					DBContract.AnnouncementTable.COLUMN_NAME_USER_ID + ") " +
					"VALUES(?,?);";

			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, announcement.getAnnouncement());
			preparedStatement.setInt(2, announcement.getAdminId());

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


	public List<Announcement> getAnnouncements(int userId) {
		Connection connection = null;
		List<Announcement> announcements = null;
		try {
			connection = pool.getConnection();

			Statement statement = connection.createStatement();
			statement.executeQuery("USE " + databaseName);

			// Query for selecting announcement of given user
			String query = "SELECT * FROM " + DBContract.AnnouncementTable.TABLE_NAME + " WHERE " +
					DBContract.AnnouncementTable.COLUMN_NAME_USER_ID + " =?;";

			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, userId);
			ResultSet resultSet = preparedStatement.executeQuery();

			announcements = new ArrayList<>();

			// Iterate over result set and add products to the list.
			while (resultSet.next()) {
				announcements.add(fetchAnnouncement(resultSet));
			}

			// Close statement and result set.
			resultSet.close();
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
		return announcements;
	}

	public Announcement getLastAnnouncement() {
		Connection connection = null;
		Announcement announcement = null;
		try {
			connection = pool.getConnection();

			Statement statement = connection.createStatement();
			statement.executeQuery("USE " + databaseName);

			ResultSet resultSet;
			// Query for selecting last announcement
			String query = "SELECT * FROM " + DBContract.AnnouncementTable.TABLE_NAME +
					" WHERE " + DBContract.AnnouncementTable.COLUMN_NAME_ID + " = " +
					"(SELECT MAX(" + DBContract.AnnouncementTable.COLUMN_NAME_ID + ") FROM " +
					DBContract.AnnouncementTable.TABLE_NAME + ");";

			resultSet = statement.executeQuery(query);
			if (resultSet.next()) {
				announcement = fetchAnnouncement(resultSet);
			}

			// Close statement and result set.
			resultSet.close();
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
		return announcement;
	}


	private Announcement fetchAnnouncement(ResultSet resultSet) {
		Announcement announcement = null;
		try {
			String text = resultSet.getString(DBContract.AnnouncementTable.COLUMN_NAME_TEXT);
			int id = resultSet.getInt(DBContract.AnnouncementTable.COLUMN_NAME_USER_ID);
			announcement = new Announcement(id, text);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return announcement;
	}


}

