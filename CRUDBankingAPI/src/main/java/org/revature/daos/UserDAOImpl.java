package org.revature.daos;

import org.revature.models.User;
import org.revature.utils.ConnectionUtil;

import java.io.IOException;
import java.sql.*;

// TODO: implement delete
public class UserDAOImpl implements UserDAO {
    Connection connection;

    public UserDAOImpl(Connection connection) {
        try {
            this.connection = ConnectionUtil.getConnection();
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean checkUserExists(String username) throws SQLException, IOException {
        connection = ConnectionUtil.getConnection();
        String sql = "SELECT * FROM users WHERE username = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, username);
        ResultSet resultSet = pstmt.executeQuery();

        return resultSet.next();
    }

    @Override
    public User createUser(User user) {
        try {
            connection = ConnectionUtil.getConnection();

            String sql = "INSERT INTO users (first_name, last_name, username, password, email, is_admin) VALUES (?,?,?,?,?,?)";
            PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, user.getFirstName());
            pstmt.setString(2, user.getLastName());
            pstmt.setString(3, user.getUsername());
            pstmt.setString(4, user.getPassword());
            pstmt.setString(5, user.getEmail());
            pstmt.setBoolean(6, user.isAdmin());

            pstmt.executeUpdate();

            ResultSet resultSet = pstmt.getGeneratedKeys();
            if (resultSet.next()) {
                user.setUserId(resultSet.getInt(1));
                return user;
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }

        return user;
    }

    @Override
    public User login(String username, String password) {
        try {
            connection = ConnectionUtil.getConnection();

            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet resultSet = pstmt.executeQuery();
            if(resultSet.next()) {
                return new User(
                        resultSet.getInt("user_id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("email"),
                        resultSet.getBoolean("is_admin")
                );
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public User updateUser(int userId, User user) {
        try {
            connection = ConnectionUtil.getConnection();

            String sql = "UPDATE users SET first_name = ?, last_name = ?, username = ?, password = ?, email = ? WHERE user_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, user.getFirstName());
            pstmt.setString(2, user.getLastName());
            pstmt.setString(3, user.getUsername());
            pstmt.setString(4, user.getPassword());
            pstmt.setString(5, user.getEmail());
            pstmt.setInt(6, userId);

            pstmt.executeUpdate();

            return user;
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getUserById(int userId) {
        try {
            connection = ConnectionUtil.getConnection();

            String sql = "SELECT * FROM users WHERE user_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, userId);

            ResultSet resultSet = pstmt.executeQuery();

            User user = new User();
            if (resultSet.next()) {
                user.setUserId(resultSet.getInt("user_id"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setEmail(resultSet.getString("email"));
                user.setAdmin(resultSet.getBoolean("is_admin"));
            }
            return user;
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getUserByUsername(String username) {
        try {
            connection = ConnectionUtil.getConnection();

            String sql = "SELECT * FROM users WHERE username = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, username);

            ResultSet resultSet = pstmt.executeQuery();

            User user = new User();
            if (resultSet.next()) {
                user.setUserId(resultSet.getInt("user_id"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setEmail(resultSet.getString("email"));
                user.setAdmin(resultSet.getBoolean("is_admin"));
            }
            return user;
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getUserByEmail(String email) {
        try {
            connection = ConnectionUtil.getConnection();

            String sql = "SELECT * FROM users WHERE username = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, email);

            ResultSet resultSet = pstmt.executeQuery();

            User user = new User();
            if (resultSet.next()) {
                user.setUserId(resultSet.getInt("user_id"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setEmail(resultSet.getString("email"));
                user.setAdmin(resultSet.getBoolean("is_admin"));
            }
            return user;
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User updateOtherUser(User adminUser, User updatedUser) {
        if (adminUser.isAdmin()) {
            try {
                connection = ConnectionUtil.getConnection();

                String sql = "UPDATE users SET first_name = ?, last_name = ?, username = ?, password = ?, email = ?, is_admin = ?"; //, accounts = ?";
                PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                pstmt.setString(1, updatedUser.getFirstName());
                pstmt.setString(2, updatedUser.getLastName());
                pstmt.setString(3, updatedUser.getUsername());
                pstmt.setString(4, updatedUser.getPassword());
                pstmt.setString(5, updatedUser.getEmail());
                pstmt.setBoolean(6, updatedUser.isAdmin());

                pstmt.executeUpdate();

                return updatedUser;
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            // TODO: how to tell user they are not an admin?
            System.out.println("you are not an admin");
        }
        return updatedUser;
    }
}
