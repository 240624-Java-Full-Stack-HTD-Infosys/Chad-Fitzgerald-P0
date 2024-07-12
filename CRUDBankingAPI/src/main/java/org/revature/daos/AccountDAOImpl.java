package org.revature.daos;

import org.revature.models.Account;
import org.revature.utils.ConnectionUtil;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAOImpl implements AccountDAO {
    Connection connection;

    public AccountDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Account createAccount(Account account) {
        try {
            connection = ConnectionUtil.getConnection();

            String sql = "INSERT INTO accounts (user_id, balance, title, description) VALUES (?,?,?,?)";
            PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, account.getUserId());
            pstmt.setDouble(2, account.getBalance());
            pstmt.setString(3, account.getTitle());
            pstmt.setString(4, account.getDescription());

            pstmt.executeUpdate();

            ResultSet resultSet = pstmt.getGeneratedKeys();
            if (resultSet.next()) {
                account.setAccountId(resultSet.getInt(1));
                return account;
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<Account> getAllUserAccounts(int userId) {
        try {
            connection = ConnectionUtil.getConnection();
            List<Account> accounts = new ArrayList<>();

            String sql = "SELECT * FROM accounts WHERE user_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, userId);

            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                Account account = new Account(
                        resultSet.getInt("account_id"),
                        userId,
                        resultSet.getInt("balance"),
                        resultSet.getString("title"),
                        resultSet.getString("description")
                );
                // add account to accounts ArrayList
                accounts.add(account);
            }
            return accounts;
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Account getAccountById(int accountId) {
        try {
            connection = ConnectionUtil.getConnection();

            String sql = "SELECT * FROM accounts WHERE account_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, accountId);

            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                return new Account(
                        resultSet.getInt("account_id"),
                        resultSet.getInt("user_id"),
                        resultSet.getInt("balance"),
                        resultSet.getString("title"),
                        resultSet.getString("description")
                );
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Account updateAccount(int accountId, Account account) {
        try {
            connection = ConnectionUtil.getConnection();

            String sql = "UPDATE accounts SET balance = ?, title = ?, description = ? WHERE account_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setDouble(1, account.getBalance());
            pstmt.setString(2, account.getTitle());
            pstmt.setString(3, account.getDescription());
            pstmt.setInt(4, accountId);

            pstmt.executeUpdate();

            return account;
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteAccount(int accountId) {
        try {
            connection = ConnectionUtil.getConnection();

            String sql = "DELETE FROM accounts WHERE account_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, accountId);

            int numUpdatedRows = pstmt.executeUpdate();

            return numUpdatedRows != 0;
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
