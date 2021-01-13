/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.utcluj.ssatr.curs2.ssatr.ia;

import java.util.Objects;

/**
 *
 * @author CORI
 */
public class Sensor {
    int value;
    String location;

    public Sensor(int value, String location) {
        this.value = value;
        this.location = location;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public int hashCode() {     
        return  location.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj==null||!(obj instanceof Sensor) )
            return false;
        
        Sensor x = (Sensor)obj;
        return x.location.equals(location);
    }   
    
    public static void main(String[] args) {
        Sensor s1 = new Sensor(1, "locatie");
	Sensor s2 = new Sensor(2, "locatie");
        Sensor s3 = new Sensor(5, "locatie diferita");
        
	if(s1.equals(s2))
		System.out.println("Sersors are equals");
	else
		System.out.println("Sersors are NOT equals");
        
        if(s1.equals(s3))
		System.out.println("Sersors are equals");
	else
		System.out.println("Sersors are NOT equals");
 
    }
}
