package org.revature.controllers;

import io.javalin.Javalin;
import io.javalin.http.Context;
import org.revature.models.Transaction;
import org.revature.services.TransactionServiceImpl;
import org.revature.utils.CookieUtil;

import java.util.List;

public class TransactionController {
    TransactionServiceImpl transactionServiceImpl;
    Javalin api;

    public TransactionController(TransactionServiceImpl transactionServiceImpl, Javalin api) {
        this.transactionServiceImpl = transactionServiceImpl;
        this.api = api;

        api.get("users/{user_id}/accounts/{account_id}/transactions", this::getAllAccountTransactionsHandler);
        api.get("users/{user_id}/accounts/{account_id}/transactions/{transaction_id}", this::getTransactionByIdHandler);
        api.post("users/{user_id}/accounts/{account_id}/transactions", this::postTransactionHandler);
    }

    private void getAllAccountTransactionsHandler(Context ctx) {
        String username = ctx.cookie("Credentials");
        if (CookieUtil.validateAuthCookie(username)) {
            int transactionId = Integer.parseInt(ctx.pathParam("account_id"));

            List<Transaction> transactions = transactionServiceImpl.getAllTransactionsByAccountId(transactionId);
            ctx.json(transactions);
            ctx.status(200);
        } else {
            ctx.json("you are not that user");
            ctx.status(400);
        }
    }

    private void getTransactionByIdHandler(Context ctx) {
        String username = ctx.cookie("Credentials");
        if (CookieUtil.validateAuthCookie(username)) {
            int transactionId = Integer.parseInt(ctx.pathParam("transaction_id"));
            Transaction transaction = transactionServiceImpl.getTransactionById(transactionId);

            ctx.json(transaction);
            ctx.status(200);
        } else {
            ctx.json("you are not that user");
            ctx.status(200);
        }
    }

    private void postTransactionHandler(Context ctx) {
        String username = ctx.cookie("Credentials");
        if (CookieUtil.validateAuthCookie(username)) {
            Transaction endUserTransactionInput = ctx.bodyAsClass(Transaction.class);

            Transaction newTransaction = transactionServiceImpl.createTransaction(endUserTransactionInput);

            if (newTransaction != null) {
                ctx.json(newTransaction);
                ctx.status(200);
            } else {
                ctx.json("Could not create that transaction");
                ctx.status(400);
            }
        } else {
            ctx.json("you are not that user");
            ctx.status(400);
        }
    }
}
