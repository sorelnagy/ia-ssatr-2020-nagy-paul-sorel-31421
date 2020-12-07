package com.nps.lab31;

import java.util.Objects;

public class Sensor {
    public static void main(String[] args) {
  Sensor sensor1 = new Sensor(1, "test1");
  Sensor sensor2 = new Sensor(2, "test2");
   sensor1.equals(sensor2);
 
}
     private int value;
     private String location;
     
      Sensor(int value, String location){
        this.value = value;
        this.location = location;
    }
      Sensor(){}

      public String getLocation(){
          return location;
      }
      
      public int getValue(){
          return value;
      }
      
     static boolean equals(Sensor sensor1, Sensor sensor2){
           return sensor1.location.equals(sensor2.location);
        
      }

     @Override 
     public boolean equals(Object obj){
       if(this == obj){
           return true;
       }
       if(obj == null){
           return false;
       }
       if(getClass() != obj.getClass()){
           return false;
       }
       final Sensor other = (Sensor) obj;
       if(!Objects.equals(this.location, other.location)){
           return false;
       }
       return true;
   }
     
}