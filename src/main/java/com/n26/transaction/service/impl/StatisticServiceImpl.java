package com.n26.transaction.service.impl;

import com.n26.transaction.service.StatisticService;
import com.n26.transaction.service.bean.StatisticBean;
import com.n26.transaction.service.bean.TransactionBean;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


@Service
public class StatisticServiceImpl implements StatisticService<StatisticBean, TransactionBean> {
    /**
     * Set up initial constant size for HashTable
     */
    private final static int TRANSACTION_SECONDS = 60;
    private Map<Integer, StatisticBean> statistics = new HashMap<>(TRANSACTION_SECONDS);

    private Lock lock = new ReentrantLock();


    @PostConstruct
    public void init() {
        Double nan = Double.NaN;
        for (int i = 1; i < TRANSACTION_SECONDS; i++) {
            statistics.put(i, new StatisticBean(nan, nan, 0, 0, new Date(0)));
        }
    }


    private long getDateDiffInSeconds(Date source, Date dest) {
        return (source.getTime() - dest.getTime()) / 1000;
    }

    private StatisticBean updateStatisticData(StatisticBean statistic, TransactionBean transactionBean) {
        double sum = statistic.getSum() + transactionBean.getAmount();
        double min = Math.min(statistic.getMin(), transactionBean.getAmount());
        double max = Math.max(statistic.getMax(), transactionBean.getAmount());
        int count = statistic.getCount() + 1;
        statistic.setSum(sum);
        statistic.setMin(min);
        statistic.setMax(max);
        statistic.setCount(count);
        return statistic;
    }

    private StatisticBean initStatisticData(StatisticBean statisticBean, TransactionBean transactionBean) {
        double amount = transactionBean.getAmount();
        statisticBean.setMax(amount);
        statisticBean.setMin(amount);
        statisticBean.setSum(amount);
        statisticBean.setCount(1);
        Date currentDate = new Date();
        statisticBean.setLastAddedTime(currentDate);
        return statisticBean;
    }

    @Override
    public void add(TransactionBean transactionBean) {
        lock.lock();
        Date transactionTime = transactionBean.getTimestamp();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(transactionTime);

        int transactionSecond = calendar.get(Calendar.SECOND);
        StatisticBean statisticBean = statistics.get(transactionSecond);

        Date currentDate = new Date();
        long diff = getDateDiffInSeconds(currentDate, statisticBean.getLastAddedTime());


        if (diff <= TRANSACTION_SECONDS) {
            updateStatisticData(statisticBean, transactionBean);
        } else {
            initStatisticData(statisticBean, transactionBean);
        }
        lock.unlock();
    }

    @Override
    public StatisticBean getTotal() {
        lock.lock();
        StatisticBean totalStatistic = new StatisticBean();
        Date currentDate = new Date();
        Double fullAmount = 0.0;
        Double maxAmount = Double.NaN;
        Double minAmount = Double.NaN;
        int count = 0;

        for (int i = 1; i < statistics.size(); i++) {
            StatisticBean statistic = statistics.get(i);
            Date date = statistic.getLastAddedTime();
            long seconds = getDateDiffInSeconds(currentDate, date);

            if (seconds <= TRANSACTION_SECONDS) {
                fullAmount += statistic.getSum();

                if (maxAmount.isNaN()) {
                    maxAmount = statistic.getMax();
                } else {
                    maxAmount = Math.max(statistic.getMax(), maxAmount);
                }
                if (minAmount.isNaN()) {
                    minAmount = statistic.getMin();
                } else {
                    minAmount = Math.min(statistic.getMax(), minAmount);
                }
                count += statistic.getCount();
            }
        }

        totalStatistic.setMax(maxAmount);
        totalStatistic.setMin(minAmount);
        totalStatistic.setSum(fullAmount);
        totalStatistic.setCount(count);

        lock.unlock();

        return totalStatistic;
    }
}
