package org.revature.controllers;

import io.javalin.Javalin;
import io.javalin.http.Context;
import org.revature.models.Account;
import org.revature.services.AccountServiceImpl;
import org.revature.services.TransactionServiceImpl;
import org.revature.utils.CookieUtil;

import java.util.List;

public class AccountController {
    TransactionServiceImpl transactionServiceImpl;
    AccountServiceImpl accountServiceImpl;
    Javalin api;

    public AccountController(TransactionServiceImpl transactionServiceImpl, AccountServiceImpl accountServiceImpl, Javalin api) {
        this.transactionServiceImpl = transactionServiceImpl;
        this.accountServiceImpl = accountServiceImpl;
        this.api = api;

        api.get("/users/{user_id}/accounts", this::getAllUserAccountsHandler);
        api.get("/users/{user_id}/accounts/{account_id}", this::getAccountByIdHandler);
        api.post("/users/{user_id}/accounts", this::postAccountHandler);
        api.put("/users/{user_id}/accounts/{account_id}", this::putAccountHandler);
        api.delete("/users/{user_id}/accounts/{account_id}", this::deleteAccountHandler);
    }

    private void getAllUserAccountsHandler(Context ctx) {
        String username = ctx.cookie("Credentials");
        if (CookieUtil.validateAuthCookie(username)) {
            int userId = Integer.parseInt(ctx.pathParam("user_id"));

            List<Account> accounts = accountServiceImpl.getAllUserAccountsByUserId(userId);
            ctx.json(accounts);
            ctx.status(200);
        } else {
            ctx.json("you are not that user");
            ctx.status(400);
        }
    }

    private void getAccountByIdHandler(Context ctx) {
        String username = ctx.cookie("Credentials");
        if (CookieUtil.validateAuthCookie(username)) {
            int accountId = Integer.parseInt(ctx.pathParam("account_id"));

            Account account = accountServiceImpl.getAccountById(accountId);
            ctx.json(account);
            ctx.status(200);
        } else {
            ctx.json("you are not that user");
            ctx.status(400);
        }
    }

    private void postAccountHandler(Context ctx) {
        Account endUserAccountInput = ctx.bodyAsClass(Account.class);

        Account newAccount = accountServiceImpl.createAccount(endUserAccountInput);

        String username = ctx.cookie("Credentials");
        if (newAccount != null && CookieUtil.validateAuthCookie(username)) {
            ctx.json(newAccount);
            ctx.status(200);
        } else {
            ctx.json("Could not create that account.");
            ctx.status(400);
        }
    }

    private void putAccountHandler(Context ctx) {
        int accountId = Integer.parseInt(ctx.pathParam("account_id"));
        Account endUserAccountInput = ctx.bodyAsClass(Account.class);

        Account updatedAccount = accountServiceImpl.updateAccount(accountId, endUserAccountInput);
        String username = ctx.cookie("Credentials");
        if (CookieUtil.validateAuthCookie(username) &&
            updatedAccount != null &&
            !updatedAccount.getTitle().isEmpty() &&
            updatedAccount.getBalance() >= 0
        ) {
            ctx.json(updatedAccount);
            ctx.status(200);
        } else {
            ctx.json("Could not update account.");
            ctx.status(400);
        }
    }

    private void deleteAccountHandler(Context ctx) {
        int accountId = Integer.parseInt(ctx.pathParam("account_id"));
        transactionServiceImpl.deleteAllAccountTransactions(accountId);

        if (transactionServiceImpl.getAllTransactionsByAccountId(accountId).isEmpty() && accountServiceImpl.deleteAccount(accountId)) {
            ctx.json("Your account has been deleted");
            ctx.status(200);
        } else {
            ctx.json("Could not delete your account");
            ctx.status(400);
        }
    }
}
