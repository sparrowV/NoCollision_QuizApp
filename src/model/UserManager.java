package model;

import database.bean.User;
import database.dao.UserDAO;

import java.util.List;


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

	public List<User> getUserList() {
		return dao.getUsers();
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

	public void updateUser(User user, String firstName, String lastName, String pictureUrl, String country, int status) {
		if (firstName == null) firstName = user.getFirstName();
		if (lastName == null) lastName = user.getLastName();
		if (pictureUrl == null) pictureUrl = user.getPicture();
		if (country == null) country = user.getCountry();


		dao.updateUser(user, firstName, lastName, pictureUrl, country, status);
	}

	public void addUserQuizHistory(int userId, int quizId, String duration, double score, double xp) {
		dao.addUserQuizHistory(userId, quizId, duration, score, xp);
	}

	public void deleteUser(int userId) {
		dao.deleteUser(userId);
	}


	public boolean usernameTaken(String username) {
		return dao.usernameExists(username);
	}

	public void changeUserStatus(int userId, int status) {
		dao.updateUserStatus(userId, status);
	}
}