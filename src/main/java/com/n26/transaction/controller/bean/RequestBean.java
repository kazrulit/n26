package com.n26.transaction.controller.bean;

public class RequestBean {
    private double amount;
    private String timestamp;

    public RequestBean() {
    }

    public RequestBean(double amount, String timestamp) {
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
