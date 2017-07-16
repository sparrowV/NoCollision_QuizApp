package model;

import database.bean.User;
import database.dao.TimelineDAO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by m1sho on 16.07.2017.
 */
public class TimelineManager {
	private TimelineDAO dao;

	public TimelineManager(TimelineDAO dao) {
		this.dao = dao;
	}

	public void getTimeline(List<User> myFriends) {
		this.dao.getTimeline(myFriends);
	}

}
