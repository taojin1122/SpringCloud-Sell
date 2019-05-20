package com.reder.order.message;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * 发送mq消息测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ReceiveMessageTest {
    @Autowired
    private AmqpTemplate amqpTemplate;
    @Test
    public void send() throws Exception {
        amqpTemplate.convertAndSend("myqueue","now :" + new Date());
    }

    /**
     * 订单发送方
     * @throws Exception
     */
    @Test
    public void sendOrder() throws Exception {
        amqpTemplate.convertAndSend("myOrder","computer","now :" + new Date());
    }
}