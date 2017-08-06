package com.n26.transaction.controller;

import com.n26.transaction.controller.bean.RequestBean;
import com.n26.transaction.mapper.TransactionBeanMapper;
import com.n26.transaction.service.StatisticService;
import com.n26.transaction.service.bean.StatisticBean;
import com.n26.transaction.service.bean.TransactionBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class MainController {
    @Autowired
    private StatisticService<StatisticBean, TransactionBean> statisticService;
    @Autowired
    private TransactionBeanMapper transactionBeanMapper;

    private final static long TRANSACTION_MILLIS = 60000L;


    @RequestMapping(value = "/transaction", method = RequestMethod.POST)
    public ResponseEntity addTransaction(RequestBean requestBean) {
        TransactionBean transactionBean = transactionBeanMapper.map(requestBean);
        Date transactionDate = transactionBean.getTimestamp();

        Date currentDate = new Date();
        Date dateBeforeTransaction = new Date(System.currentTimeMillis() - TRANSACTION_MILLIS);

        if (transactionDate.after(currentDate) || transactionDate.before(dateBeforeTransaction))
            return new ResponseEntity(HttpStatus.NO_CONTENT);

        statisticService.add(transactionBean);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping("/statistics")
    public StatisticBean getTotalStatistics() {
        return statisticService.getTotal();
    }
}
