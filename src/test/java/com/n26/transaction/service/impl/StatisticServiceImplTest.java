package com.n26.transaction.service.impl;

import com.n26.transaction.TestConfig;
import com.n26.transaction.service.StatisticService;
import com.n26.transaction.service.bean.StatisticBean;
import com.n26.transaction.service.bean.TransactionBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StatisticServiceImplTest {
    @Autowired
    private StatisticService<StatisticBean, TransactionBean> statisticService;

    @Test
    public void shouldTestServiceAdding() {
        statisticService.add(new TransactionBean(10, new Timestamp(System.currentTimeMillis())));
        StatisticBean statisticBean = statisticService.getTotal();
        assertEquals(10, statisticBean.getSum(), TestConfig.DELTA);
        assertEquals(1, statisticBean.getCount());
    }

}