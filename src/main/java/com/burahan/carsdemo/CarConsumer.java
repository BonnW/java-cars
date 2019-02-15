package com.burahan.carsdemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CarConsumer
{
    @RabbitListener(queues = CarsdemoApplication.QUEUE_NAME)
    public void consumeMessage(final Message cm)
    {
        log.info("Received Message: ", cm.toString());
    }
}
