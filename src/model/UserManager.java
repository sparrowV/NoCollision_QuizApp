package model;

import database.bean.User;
import database.dao.UserDAO;

import java.util.List;

public class UserManager {
	private List<User> users;

	public UserManager(UserDAO dao) {
		this.users = dao.getUsers();
	}

	public boolean correctLogin(User user) {
		return users.contains(user);
	}

	public boolean usernameTaken(String username) {
		for (User user : this.users) {
			if (user.getUsername().equals(username))
				return true;
		}
		return false;
	}
}
