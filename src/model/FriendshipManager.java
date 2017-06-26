package model;

import database.bean.User;
import database.dao.FriendshipDAO;
import database.dao.UserDAO;

import java.util.List;

/**
 * Created by m1sho on 26.06.2017.
 */
public class FriendshipManager {
	private FriendshipDAO dao;

	public FriendshipManager(FriendshipDAO dao) {
		this.dao = dao;
	}

	public List<User> getFriends(String currentID) {
		return this.dao.getFriends(currentID);
	}

	public List<User> getRecievedFriendRequests(String currentID) {
		return this.dao.getRecievedFriendRequests(currentID);
	}

	public void sendFriendRequest(String currentID, String requestUserID) {
		this.dao.sendFriendRequest(currentID, requestUserID);
	}

	public void confirmRequest(String currentID, String requestUserID) {
		this.dao.confirmRequest(currentID, requestUserID);
	}

}