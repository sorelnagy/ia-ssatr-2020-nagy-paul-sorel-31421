/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package part1curs2;

/**
 *
 * @author nagyp
 */
public class Car {
    //atribute
    private String name;
    public  int speed;
    private String plateNumber; 
    
    //constructori
    Car(String name, int speed, String plateNumber){
        this.name = name;
        this.speed = speed;
        this.plateNumber = plateNumber; 
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
    
    public String getPlateNumber() {
        return plateNumber;
    }  
    
    
}