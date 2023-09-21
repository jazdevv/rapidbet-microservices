package com.rapidbetmicroserviceauth.rapidbetmicroserviceauth.rabbitmq.consumer;

import com.rapidbetmicroserviceauth.rapidbetmicroserviceauth.rabbitmq.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQCosumer {
    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void cosumeMessageFromQueueSetCredits(String data){
        System.out.println("consumer message receive from queue at auth: " + data);
    }
}
