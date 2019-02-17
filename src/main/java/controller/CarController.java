package controller;

import domains.carsdemo.Car;
import domains.carsdemo.CarLog;
import domains.carsdemo.CarRepository;
import domains.carsdemo.CarsdemoApplication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        CarLog message = new CarLog("Retrieved All Cars");
        rt.convertAndSend(CarsdemoApplication.QUEUE_NAME, message.toString());
        log.info("Your presence has been noted");
        return cRepo.findAll();
    }

    @GetMapping("/cars/id/{id}")
    public Optional<Car> findById(@PathVariable Long id)
    {
        CarLog message = new CarLog("Retrieved Car with id: " + id);
        rt.convertAndSend(CarsdemoApplication.QUEUE_NAME, message.toString());
        log.info("Your presence has been noted");
        return cRepo.findById(id);
    }

    @GetMapping("/cars/year/{year}")
    public List<Car> findByYear(@PathVariable int year)
    {
        // return cRepo.findByYear(year);

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
        List<Car> carList = cRepo.findAll();
        List<Car> returnList = new ArrayList<>();
        for (Car c : carList)
        {
            if (c.getBrand().equalsIgnoreCase(brand))
            {
                returnList.add(c);
            }
        }
        CarLog message = new CarLog("Retrieved all cars made by " + brand);
        rt.convertAndSend(CarsdemoApplication.QUEUE_NAME, message.toString());
        log.info("Your presence has been noted");
        return returnList;
    }

    @PostMapping("/cars/upload")
    public List<Car> newCar(@RequestBody List<Car> newCarList)
    {
        CarLog message = new CarLog("Added Cars to Data");
        rt.convertAndSend(CarsdemoApplication.QUEUE_NAME, message.toString());
        log.info("Your presence has been noted");
        return cRepo.saveAll(newCarList);
    }

    @DeleteMapping("/cars/delete/{id}")
    public void deleteCar(@PathVariable Long id)
    {
        CarLog message = new CarLog("Deleted Car with id: " + id);
        rt.convertAndSend(CarsdemoApplication.QUEUE_NAME, message.toString());
        log.info("Your presence has been noted");
        cRepo.deleteById(id);
    }


}
