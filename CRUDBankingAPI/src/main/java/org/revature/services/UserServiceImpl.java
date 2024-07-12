package org.revature.services;

import org.revature.daos.UserDAO;
import org.revature.daos.UserDAOImpl;
import org.revature.models.User;

import java.io.IOException;
import java.sql.SQLException;

public class UserServiceImpl implements UserService {
    UserDAOImpl userDAOImpl;
    AccountServiceImpl accountServiceImpl;

    public UserServiceImpl(UserDAOImpl userDAOImpl, AccountServiceImpl accountServiceImpl) {
        this.userDAOImpl = userDAOImpl;
        this.accountServiceImpl = accountServiceImpl;
    }

    public boolean checkUserExists(String username) throws SQLException, IOException {
        return userDAOImpl.checkUserExists(username);
    }

    @Override
    public User registerUser(User user) {
        // username and email are unique (no user already has them), exist, and pw has at least 8 chars
        // all required fields are not null
        if (
            userDAOImpl.getUserByUsername(user.getUsername()) == null &&
            userDAOImpl.getUserByEmail(user.getEmail()) == null &&
            !user.getFirstName().isBlank() &&
            !user.getLastName().isBlank() &&
            !user.getUsername().isBlank() &&
            user.getPassword().length() >= 8 &&
            !user.getEmail().isBlank()
        ) {
            return userDAOImpl.createUser(user);
        }

        return user;
    }

    @Override
    public User login(String username, String password) {
        return userDAOImpl.login(username, password);
    }

    public User updateUser(int userId, User user) {
        return userDAOImpl.updateUser(userId, user);
    }
}
