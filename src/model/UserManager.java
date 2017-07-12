package model;

import database.bean.User;
import database.dao.UserDAO;

import java.sql.SQLException;


public class UserManager {
	private UserDAO dao;

	public UserManager(UserDAO dao) {
		this.dao = dao;
	}

	public User getUser(String username, String password) {
		return dao.getUser(username, password);
	}

	public User getUserByUsername(String username) {
		return dao.getUserByUsername(username);
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

	public void addUserQuizHistory(int userId, int quizId, int status, String duration, double score) {
		try {

			dao.addUserQuizHistory(userId, quizId, status, duration, score);
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