package database.dao;

import javax.sql.DataSource;

/**
 * Created by m1sho on 16.07.2017.
 */
public class TimelineDAO {
	private DataSource pool;

	public TimelineDAO(DataSource pool) {
		this.pool = pool;
	}

	public void getTimeline(int currentUserID) {

	}
}
