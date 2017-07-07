package model;

import database.bean.User;
import database.dao.FriendshipDAO;

import java.util.List;

/**
 * Created by m1sho on 26.06.2017.
 */
public class FriendshipManager {
	private FriendshipDAO dao;

	public FriendshipManager(FriendshipDAO dao) {
		this.dao = dao;
	}

	public List<User> getFriends(int currentId) {
		return this.dao.getFriends(currentId);
	}

	public List<User> getReceivedFriendRequests(int currentId) {
		return this.dao.getReceivedFriendRequests(currentId);
	}

	public void sendFriendRequest(int currentId, int requestUserId) {
		this.dao.sendFriendRequest(currentId, requestUserId);
	}

	public boolean areFriends(int currentID, int friendID) {
		return this.dao.areFriends(currentID, friendID);
	}

	public void acceptRequest(int currentId, int requestUserId) {
		this.dao.acceptRequest(currentId, requestUserId);
	}

	public void rejectRequest(int currentId, int requestUserId) {
		this.dao.rejectRequest(currentId, requestUserId);
	}

}
