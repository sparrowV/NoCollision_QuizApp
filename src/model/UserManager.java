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

    public boolean correctLogIn(User user) {
        return users.contains(user);
    }

    public boolean userNameTaken(String username) {
        for (User user : this.users) {
            if (user.getUsername().equals(username))
                return true;
        }
        return false;
    }
}
