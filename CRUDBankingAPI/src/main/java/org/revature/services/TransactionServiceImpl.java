package org.revature.services;

import org.revature.daos.AccountDAOImpl;
import org.revature.daos.TransactionDAOImpl;
import org.revature.models.Account;
import org.revature.models.Transaction;

import java.util.List;

public class TransactionServiceImpl implements TransactionService {
    TransactionDAOImpl transactionDAOImpl;
    AccountDAOImpl accountDAOImpl;

    public TransactionServiceImpl(TransactionDAOImpl transactionDAOImpl, AccountDAOImpl accountDAOImpl) {
        this.transactionDAOImpl = transactionDAOImpl;
        this.accountDAOImpl = accountDAOImpl;
    }

    @Override
    public List<Transaction> getAllTransactionsByAccountId(int accountId) {
        return transactionDAOImpl.getAllAccountTransactions(accountId);
    }

    @Override
    public Transaction getTransactionById(int transactionId) {
        return transactionDAOImpl.getTransactionById(transactionId);
    }

    @Override
    public Transaction createTransaction(Transaction transaction) {
        Account account = accountDAOImpl.getAccountById(transaction.getAccountId());
        double amount = transaction.getAmount();
        // if it's a withdrawal and
        // the withdrawal amount is more than account balance, don't do it
        if (amount < 0 && Math.abs(amount) > account.getBalance()) {
            return null;
        } else {
            account.setBalance(account.getBalance() + amount);
            accountDAOImpl.updateAccount(account.getAccountId(), account);
            return transactionDAOImpl.createTransaction(transaction);
        }
    }

    @Override
    public boolean deleteAllAccountTransactions(int accountId) {
        return transactionDAOImpl.deleteAllAccountTransactions(accountId);
    }
}
