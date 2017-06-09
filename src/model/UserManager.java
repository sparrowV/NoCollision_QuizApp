package model;

import database.bean.User;
import database.dao.UserDAO;

import java.util.List;

public class UserManager {
	private UserDAO dao;
	private List<User> users;

	public UserManager(UserDAO dao) {
		this.dao = dao;
		this.users = dao.getUsers();
	}

	public boolean userExists(User user) {
		return users.contains(user);
	}
}
