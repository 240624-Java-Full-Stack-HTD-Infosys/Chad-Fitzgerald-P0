package org.revature.services;

import org.revature.models.User;

public interface UserService {
    public User registerUser(User user);
    public User login(String username, String password);
}
