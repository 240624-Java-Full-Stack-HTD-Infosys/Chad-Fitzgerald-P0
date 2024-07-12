package org.revature.services;

import org.revature.models.Transaction;

import java.util.List;

public interface TransactionService {
    public List<Transaction> getAllTransactionsByAccountId(int accountId);
    public Transaction getTransactionById(int transactionId);
    public Transaction createTransaction(Transaction transaction);
    public boolean deleteAllAccountTransactions(int accountId);
}
