package org.revature.utils;

import io.javalin.http.Cookie;
import org.revature.models.User;
import org.revature.services.UserServiceImpl;

import java.io.IOException;
import java.sql.SQLException;

public class CookieUtil {
//    private static final UserServiceImpl userServiceImpl = (UserServiceImpl)
    static UserServiceImpl userServiceImpl;

    public CookieUtil (UserServiceImpl userServiceImpl) {
        CookieUtil.userServiceImpl = userServiceImpl;
    }

    public static boolean validateAuthCookie(String username) {
        try {
            return userServiceImpl.checkUserExists(username);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
