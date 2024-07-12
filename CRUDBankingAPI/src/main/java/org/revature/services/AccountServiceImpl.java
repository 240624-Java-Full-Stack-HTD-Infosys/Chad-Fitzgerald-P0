package org.revature.services;

import org.revature.daos.AccountDAOImpl;
import org.revature.models.Account;

import java.util.List;

public class AccountServiceImpl implements AccountService {
    AccountDAOImpl accountDAOImpl;

    public AccountServiceImpl(AccountDAOImpl accountDAOImpl) {
        this.accountDAOImpl = accountDAOImpl;
    }

    @Override
    public List<Account> getAllUserAccountsByUserId(int userId) {
        return accountDAOImpl.getAllUserAccounts(userId);
    }

    @Override
    public Account getAccountById(int accountId) {
        return accountDAOImpl.getAccountById(accountId);
    }

    @Override
    public Account createAccount(Account account) {
        // title of account is not empty and length < 40
        // length of description is < 500
        if (!account.getTitle().isEmpty() &&
            account.getTitle().length() <= 40 &&
            (account.getDescription() == null || account.getDescription().length() <= 500)
        ) {
            return accountDAOImpl.createAccount(account);
        }
        return null;
    }

    @Override
    public Account updateAccount(int accountId, Account account) {
        // account exists, title isn't empty, and balance isn't negative
        if (accountId > 0 &&
            !account.getTitle().isEmpty() &&
            account.getBalance() >= 0
        ) {
            return accountDAOImpl.updateAccount(accountId, account);
        }
        return null;
    }

    public boolean deleteAccount(int accountId) {
        Account account = accountDAOImpl.getAccountById(accountId);

        if (account.getBalance() == 0) {
            return accountDAOImpl.deleteAccount(accountId);
        } else {
            return false;
        }
    }
}
