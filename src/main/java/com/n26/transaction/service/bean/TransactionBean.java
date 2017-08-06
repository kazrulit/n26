package com.n26.transaction.service.bean;

import java.sql.Timestamp;

public class TransactionBean {
    private double amount;
    private Timestamp timestamp;

    public TransactionBean() {
    }

    public TransactionBean(double amount, Timestamp timestamp) {
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
