package com.n26.transaction.controller;

import com.n26.transaction.TestConfig;
import com.n26.transaction.controller.bean.RequestBean;
import com.n26.transaction.service.bean.StatisticBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MainControllerTest {
    @Autowired
    private MainController controller;

    @Test
    public void shouldAddTransaction() throws Exception {
        String timeStamp = String.valueOf(System.currentTimeMillis() / 1000L);
        ResponseEntity responseEntity = controller.addTransaction(new RequestBean(10, timeStamp));
        assertEquals(201, responseEntity.getStatusCode().value());

        timeStamp = String.valueOf((System.currentTimeMillis() - 60000L) / 1000L);
        responseEntity = controller.addTransaction(new RequestBean(10, timeStamp));
        assertEquals(204, responseEntity.getStatusCode().value());
    }

    @Test
    public void shouldGetTotalStatistics() throws Exception {
        String timeStamp = String.valueOf(System.currentTimeMillis() / 1000L);
        controller.addTransaction(new RequestBean(10, timeStamp));
        controller.addTransaction(new RequestBean(10, timeStamp));

        StatisticBean statisticBean = controller.getTotalStatistics();
        assertEquals(20, statisticBean.getSum(), TestConfig.DELTA);
        assertEquals(10, statisticBean.getAvg(), TestConfig.DELTA);
        assertEquals(2, statisticBean.getCount());
    }
}