package org.revature.daos;

import org.revature.models.Transaction;
import org.revature.utils.ConnectionUtil;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAOImpl implements TransactionDAO {
    Connection connection;

    public TransactionDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Transaction createTransaction(Transaction transaction) {
        try {
            connection = ConnectionUtil.getConnection();

            String sql = "INSERT INTO transactions (account_id, amount, date) VALUES (?,?,?)";
            PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, transaction.getAccountId());
            pstmt.setDouble(2, transaction.getAmount());
            pstmt.setTimestamp(3, transaction.getDate());

            pstmt.executeUpdate();

            ResultSet resultSet = pstmt.getGeneratedKeys();
            if (resultSet.next()) {
                transaction.setTransactionId(resultSet.getInt(1));
                return transaction;
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<Transaction> getAllAccountTransactions(int accountId) {
        try {
            connection = ConnectionUtil.getConnection();
            List<Transaction> transactions = new ArrayList<>();

            String sql = "SELECT * FROM transactions WHERE account_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, accountId);

            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                Transaction transaction = new Transaction(
                        resultSet.getInt("transaction_id"),
                        accountId,
                        resultSet.getDouble("amount"),
                        resultSet.getTimestamp("date")
                );
                transactions.add(transaction);
            }
            return transactions;
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Transaction getTransactionById(int transactionId) {
        try {
            connection = ConnectionUtil.getConnection();

            String sql = "SELECT * FROM transactions WHERE transaction_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, transactionId);

            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                return new Transaction(
                        transactionId,
                        resultSet.getInt("account_id"),
                        resultSet.getDouble("amount"),
                        resultSet.getTimestamp("date")
                );
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public boolean deleteAllAccountTransactions(int accountId) {
        try {
            connection = ConnectionUtil.getConnection();

            String sql = "DELETE FROM transactions WHERE account_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, accountId);

            int numUpdatedRows = pstmt.executeUpdate();

            return numUpdatedRows != 0;
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
