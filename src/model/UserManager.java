package model;

import database.bean.User;
import database.dao.UserDAO;

import java.sql.SQLException;
import java.util.List;


public class UserManager {
	private UserDAO dao;

	public UserManager(UserDAO dao) {
		this.dao = dao;
	}

	public User getUser(String username, String password) {
		return dao.getUser(username, password);
	}

	public User getUserById(int userId) {
		try {
			return dao.getUserById(userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public User addUser(User user) {
		try {
			int id = dao.addUser(user);
			return getUserById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean usernameTaken(String username) {
		boolean answer = false;
		try {
			answer = dao.usernameExists(username);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return answer;
	}

}