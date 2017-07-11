package model;

import database.dao.MessageDAO;

import java.util.Date;

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
}
