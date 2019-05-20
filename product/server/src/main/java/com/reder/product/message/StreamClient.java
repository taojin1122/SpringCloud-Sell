package com.reder.product.message;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface StreamClient {
    String INPUT = "myMessage";
    String OUTPUT = "outMessage";
    @Input(OUTPUT)
    SubscribableChannel input();

    @Output(INPUT)
    MessageChannel output();
}
