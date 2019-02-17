package domains.carsdemo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Car
{
    private @Id @GeneratedValue Long id;
    private int year;
    private String brand;
    private String model;

    public Car()
    {
        // default constructor
    }

    public Car(int year, String brand, String model)
    {
        this.year = year;
        this.brand = brand;
        this.model = model;
    }
}
