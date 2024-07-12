package org.revature.daos;

import org.revature.models.Account;

import java.util.List;

public interface AccountDAO {
    public Account createAccount(Account account);
    public List<Account> getAllUserAccounts(int userId);
    public Account getAccountById(int accountId);
    public Account updateAccount(int accountId, Account account);
    public boolean deleteAccount(int accountId);
}
