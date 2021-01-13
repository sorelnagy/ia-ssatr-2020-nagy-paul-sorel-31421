/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.lab3_ex6;

import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CORI
 */
public class CarAccess {
    private String plateNumber;
    private long accessTime; 


    public CarAccess(String plateNumber, long accessTime) {
        this.plateNumber = plateNumber;
        this.accessTime = accessTime;
    }

 

    public String getPlateNumber() {
        return plateNumber;
    }

 

    public long getAccessTime() {
        return accessTime;
    }

 

    @Override
    public String toString() {
        return "CarAccess{" + "plateNumber=" + plateNumber + ", accessTime=" + accessTime + '}';
    }

 

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

 

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CarAccess other = (CarAccess) obj;
        if (!Objects.equals(this.plateNumber, other.plateNumber)) {
            return false;
        }
        return true;
    }
    
    
        
    public static void main(String[] args) {
       CarAccess ca1 = new CarAccess("CJ-99-HCM", System.currentTimeMillis()); 
       System.out.println(ca1);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(CarAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("time="+(System.currentTimeMillis() - ca1.getAccessTime())); 
        
        ArrayList<CarAccess> list = new ArrayList<>();
        list.add(ca1);
        
        String test = "CJ-99-HCM";
        // 1. metoda 1 caoutare si stergere obiect
        if(list.contains(new CarAccess(test, System.currentTimeMillis()))){
            System.out.println("Plate number found.");
            list.remove(new CarAccess(test, System.currentTimeMillis()));
        }
        else{
            System.out.println("Plate number not found.");
        }
        
        //2. 
        int index = list.indexOf(new CarAccess(test, System.currentTimeMillis()));
        if(index!=-1){
             System.out.println("Plate number found.");
            CarAccess y = list.remove(index);
        }else{
             System.out.println("Plate number not found.");
        }
    }
}
