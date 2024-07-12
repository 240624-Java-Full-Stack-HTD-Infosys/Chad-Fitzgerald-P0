package org.revature.services;

import org.revature.models.Account;

import java.util.List;

public interface AccountService {
    public List<Account> getAllUserAccountsByUserId(int userId);
    public Account getAccountById(int accountId);
    public Account createAccount(Account account);
    public Account updateAccount(int accountId, Account account);
}
