package com.n26.transaction.service.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Date;

@JsonPropertyOrder({ "sum", "avg", "max", "min", "count" })
public class StatisticBean {
    private double sum;
    private double max;
    private double min;
    private int count;
    @JsonIgnore
    private Date lastAddedTime;

    public StatisticBean(){

    }

    public StatisticBean(double max, double min, double sum, int count, Date lastAddedTime) {
        this.max = max;
        this.min = min;
        this.sum = sum;
        this.count = count;
        this.lastAddedTime = lastAddedTime;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public double getAvg() {
        return sum / count;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getMin() {
        return min;
    }

    public Date getLastAddedTime() {
        return lastAddedTime;
    }

    public void setLastAddedTime(Date lastAddedTime) {
        this.lastAddedTime = lastAddedTime;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }



    @Override
    public String toString() {
        return "StatisticBean{" +
                "max=" + max +
                ", min=" + min +
                ", sum=" + sum +
                ", count=" + count +
                ", lastAddedTime=" + lastAddedTime +
                '}';
    }
}
