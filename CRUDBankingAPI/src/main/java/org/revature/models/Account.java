package org.revature.models;

public class Account {
    private Integer accountId;
    private Integer userId;
    private double balance;
    private String title;
    private String description;

    public Account() {}

    public Account(Integer accountId, Integer userId, Integer balance, String title, String description) {
        this.accountId = accountId;
        this.userId = userId;
        this.balance = balance;
        this.title = title;
        this.description = description;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
