package model;

import database.dao.TimelineDAO;

/**
 * Created by m1sho on 16.07.2017.
 */
public class TimelineManager {
	private TimelineDAO dao;

	public TimelineManager(TimelineDAO dao) {
		this.dao = dao;
	}

	public void getTimeline(int currentUserID) {
		this.dao.getTimeline(currentUserID);
	}

}
