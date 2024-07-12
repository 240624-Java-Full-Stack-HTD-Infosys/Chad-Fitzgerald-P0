package org.revature;

import io.javalin.Javalin;
import org.revature.controllers.AccountController;
import org.revature.controllers.TransactionController;
import org.revature.controllers.UserController;
import org.revature.daos.AccountDAOImpl;
import org.revature.daos.TransactionDAOImpl;
import org.revature.daos.UserDAOImpl;
import org.revature.services.AccountServiceImpl;
import org.revature.services.TransactionServiceImpl;
import org.revature.services.UserServiceImpl;
import org.revature.utils.ConnectionUtil;
import org.revature.utils.CookieUtil;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, IOException {
        Javalin api = Javalin.create().start(8080);
        Connection connection = ConnectionUtil.getConnection();
        UserDAOImpl userDAOImpl = new UserDAOImpl(connection);
        AccountDAOImpl accountDAOImpl = new AccountDAOImpl(connection);
        TransactionDAOImpl transactionDAOImpl = new TransactionDAOImpl(connection);
        TransactionServiceImpl transactionServiceImpl = new TransactionServiceImpl(transactionDAOImpl, accountDAOImpl);
        AccountServiceImpl accountServiceImpl = new AccountServiceImpl(accountDAOImpl);
        UserServiceImpl userServiceImpl = new UserServiceImpl(userDAOImpl, accountServiceImpl);
        CookieUtil cookieUtil = new CookieUtil(userServiceImpl);
        UserController userController = new UserController(userServiceImpl, api);
        AccountController accountController = new AccountController(transactionServiceImpl, accountServiceImpl, api);
        TransactionController transactionController = new TransactionController(transactionServiceImpl, api);
    }
}