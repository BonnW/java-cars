package com.burahan.carsdemo;

public class CarNotFoundException extends RuntimeException
{
    public CarNotFoundException(Long id)
    {
        super("Could not find Car " + id);
    }
}
