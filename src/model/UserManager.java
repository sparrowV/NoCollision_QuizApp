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

	public void addUser(User user) {
		try {
			dao.addUser(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
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