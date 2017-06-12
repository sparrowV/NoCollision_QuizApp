package database.daoInterface;


import database.bean.User;

import java.util.List;

public interface UserDAO {
    public List<User> getUsers();

    public void addUser(User user) throws Exception;

    public void uptdateUser(User user);

    public void deleteUser(User user);

    public int getUserId(String username);
}
