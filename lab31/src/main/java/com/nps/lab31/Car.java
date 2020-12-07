
package com.nps.lab31;


public class Car {
    //atribute
    private String name;
    public  int speed;
    
    //constructori
    Car(String name, int speed){
        this.name = name;
        this.speed = speed;
    }
    
    void accelerate(){
        speed++;
        System.out.println("Vehicle speed is "+speed);
    }

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }
    
      
    
}