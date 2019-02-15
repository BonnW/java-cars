package com.burahan.carsdemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class CarController
{
    private final CarRepository cRepo;
    private final RabbitTemplate rt;

    public CarController(CarRepository cRepo, RabbitTemplate rt)
    {
        this.cRepo = cRepo;
        this.rt = rt;
    }


}
