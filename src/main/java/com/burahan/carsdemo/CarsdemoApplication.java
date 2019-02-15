package com.burahan.carsdemo;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CarsdemoApplication
{
    public static final String EXCHANGE_NAME = "Car";
    public static final String QUEUE_NAME = "Log";

    public static void main(String[] args)
    {
        SpringApplication.run(CarsdemoApplication.class, args);
    }

    @Bean
    public TopicExchange appExchange()
    {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue appQueue()
    {
        return new Queue(QUEUE_NAME);
    }

    @Bean
    public Binding declareBinding()
    {
        return BindingBuilder.bind(appQueue()).to(appExchange()).with(QUEUE_NAME);
    }

}

