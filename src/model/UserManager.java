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

	public boolean correctLogin(User user) {
		return dao.userExists(user.getUsername(), user.getPassword());
	}

	public void addUser(User user) {
		try {
			dao.addUser(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getUserId(User user) {
		return dao.getUserId(user.getUsername());
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