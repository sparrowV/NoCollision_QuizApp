package model;

import database.bean.TimelineActivity;
import database.bean.User;
import database.dao.TimelineDAO;

import java.util.List;

/**
 * Created by m1sho on 16.07.2017.
 */
public class TimelineManager {
	private TimelineDAO dao;

	public TimelineManager(TimelineDAO dao) {
		this.dao = dao;
	}

	public List<TimelineActivity> getTimeline(List<User> myFriends) {
		return this.dao.getTimeline(myFriends);
	}

}
