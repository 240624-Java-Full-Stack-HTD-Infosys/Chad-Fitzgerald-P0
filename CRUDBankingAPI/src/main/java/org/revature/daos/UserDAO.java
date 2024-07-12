package org.revature.daos;

import org.revature.models.User;

import java.sql.Connection;
import java.sql.SQLException;

public interface UserDAO {
    public User createUser(User user) throws SQLException;
    public User login(String username, String password);
    public User updateUser(int userId, User user);
    public User getUserById(int userId);
    public User getUserByUsername(String username);
    public User getUserByEmail(String email);
    public User updateOtherUser(User adminUser, User updatedUser);
}
