package com.burahan.carsdemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

    @GetMapping("/cars")
    public List<Car> getAll()
    {
        return cRepo.findAll();
    }

    @GetMapping("/cars/id/{id}")
    public Car findById(@PathVariable Long id)
    {
        List<Car> carList = cRepo.findAll();
        for (Car c : carList)
        {
            if (c.getId().equals(id))
            {
                CarLog message = new CarLog("Got one Car by Id. Car: " + c.getYear() + " " +c .getBrand() + " " + c.getModel());
                rt.convertAndSend(CarsdemoApplication.QUEUE_NAME, message.toString());
                log.info("Your presence has been noted");
                return c;
            }
        }
        return null;
    }

    @GetMapping("/cars/year/{year}")
    public List<Car> sortByYear(@PathVariable int year)
    {
        List<Car> carList = cRepo.findAll();
        List<Car> returnList = new ArrayList<>();
        for (Car c : carList)
        {
            if (c.getYear() == year)
            {
                returnList.add(c);
            }
        }
        CarLog message = new CarLog("Retrieved all cars made in " + year);
        rt.convertAndSend(CarsdemoApplication.QUEUE_NAME, message.toString());
        log.info("Your presence has been noted");
        return returnList;
    }

    @GetMapping("/cars/brand/{brand}")
    public List<Car> sortByBrand(@PathVariable String brand)
    {
        
    }

    @PostMapping("/cars/upload")
    public List<Car> newCar(@RequestBody List<Car> newCarList)
    {
        return cRepo.saveAll(newCarList);
    }


}
