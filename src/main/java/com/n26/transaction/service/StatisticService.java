package com.n26.transaction.service;


public interface StatisticService<T, K> {
    T getTotal();
    void add(K transactionBean);
}
