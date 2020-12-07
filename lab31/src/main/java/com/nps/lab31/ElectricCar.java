package com.nps.lab31;

public class ElectricCar extends Car {
    private int batteryLevel;
    
    public ElectricCar(String name, int speed) {
        super(name, speed);
        batteryLevel = 100;
    }

    @Override //poate sa lipseasca
    void accelerate() {
        speed+=4;
        batteryLevel--;
        System.out.println("Electric car speed is "+speed+" and battery level is "+batteryLevel);        
    }
    
    
    
    
}