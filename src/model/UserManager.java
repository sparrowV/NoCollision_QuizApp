package model;

import database.bean.User;
import database.daoImp.UserDAOSql;
import database.daoInterface.UserDAO;
import java.util.List;


public class UserManager {
	private UserDAOSql dao;

	public UserManager(UserDAOSql dao) {
		this.dao = dao;
	}

	public boolean correctLogin(User user) {
		List<User> users = dao.getUsers();

		return users.contains(user);
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
		List<User> users = dao.getUsers();
		for (User user : users) {
			if (user.getUsername().equals(username))
				return true;
		}
		return false;
	}
}
