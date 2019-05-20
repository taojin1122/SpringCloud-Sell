package com.reder.order.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 接收方
 */
@Component
@Slf4j
public class ReceiveMessage {
    //@RabbitListener(queues = "myqueue")
    //2、自动创建队列
    //@RabbitListener(queuesToDeclare = @Queue("myQueue"))
    // 3、自动创建队列，Exchange和Queue绑定
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("myQueue"),
            exchange = @Exchange("myExchange")
    ))
    public void receive(String messge) {
      log.info("message={}",messge);
    }
    /**
     * 数码供应商服务 接收消息
     * @param messge
     */
    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange("myOrder"),
            key = "computer",
            value = @Queue("computerOrder")
    ))
    public void processComputer(String messge) {
        log.info("computer MqReceive: {}",messge);
    }

    /**
     * 水果供应商服务 接收消息
     * @param messge
     */
    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange("myOrder"),
            key = "fruit",
            value = @Queue("fruitOrder")
    ))
    public void fruitComputer(String messge) {
        log.info("computer MqReceive: {}",messge);
    }
}
