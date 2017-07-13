package model;

import database.dao.MessageDAO;

/**
 * Created by m1sho on 12.07.2017.
 */
public class MessageManager {
	private MessageDAO dao;

	public MessageManager(MessageDAO dao) {
		this.dao = dao;
	}

	public void sendMessage(int currentId, int friendID, String message) {
		this.dao.sendMessage(currentId, friendID, message);
	}

	public String getChatHistory(int currentUserId, int friendUserID, String friendName) {
		return this.dao.getChatHistory(currentUserId, friendUserID, friendName);
	}
}
