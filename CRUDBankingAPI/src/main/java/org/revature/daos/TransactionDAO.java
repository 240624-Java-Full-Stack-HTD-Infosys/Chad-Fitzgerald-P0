package org.revature.daos;

import org.revature.models.Transaction;

import java.util.List;

public interface TransactionDAO {
    public Transaction createTransaction(Transaction transaction);
    public List<Transaction> getAllAccountTransactions(int accountId);
    public Transaction getTransactionById(int transactionId);
    public boolean deleteAllAccountTransactions(int accountId);
}
