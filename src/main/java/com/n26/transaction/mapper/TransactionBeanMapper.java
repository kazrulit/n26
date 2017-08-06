package com.n26.transaction.mapper;

import com.n26.transaction.controller.bean.RequestBean;
import com.n26.transaction.service.bean.TransactionBean;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;


@Component
public class TransactionBeanMapper {
    public TransactionBean map(RequestBean requestBean) {
        TransactionBean transactionBean = new TransactionBean();
        transactionBean.setAmount(requestBean.getAmount());

        String timeStamp = requestBean.getTimestamp();
        transactionBean.setTimestamp(new Timestamp(Long.valueOf(timeStamp) * 1000));

        return transactionBean;
    }
}
