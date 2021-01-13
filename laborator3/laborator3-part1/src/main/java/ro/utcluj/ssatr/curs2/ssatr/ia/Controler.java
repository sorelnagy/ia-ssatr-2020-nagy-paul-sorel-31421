/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.utcluj.ssatr.curs2.ssatr.ia;

/**
 *
 * @author CORI
 */
public class Controler {
    Sensor sensor;

    public Controler(Sensor sensor) {
        this.sensor = sensor;
    }
    
    void control(){
        if(25 < this.sensor.getValue())
            System.out.println("ACTIVARE MOD RACIRE");
        else  if(15 > this.sensor.getValue())
            System.out.println("ACTIVARE MOD INCALZIRE");
    }
}
