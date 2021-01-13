package com.mycompany.lab3_ex6;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Server {
    
   
    //declarare si initializare interfata grafica
    //declar lista care stocheaza inregistrarile 
    
    ServerJFrame f = new ServerJFrame();
    ArrayList<CarAccess> list = new ArrayList<>();
    
    public static final int UNIT_PRICE = 1; 
     
    private int computeParkingStayPrice(long entryTime){
        //1 LEU / secunda 
        
        long crt = System.currentTimeMillis();
        return (int)(((crt - entryTime)/1000)* UNIT_PRICE);
        
    }
     
    String handlePlateNumberRequest(String plateNumber){
        // verificam daca nr exista deja
            // daca nu exista il adaug in lista
            //returnez un sir "numaru_inmatriculare inregistrat"
        //daca exista 
            //calculez timpul de stationare
            //ster numarul din lista 
            //returnez un sir "numar_inmatriculare pret XX RON"
        String message;
       
        int index = list.indexOf(new CarAccess(plateNumber, System.currentTimeMillis())); 

        if( -1 == index ){
            System.out.println("Plate number not found.");
            list.add(new CarAccess(plateNumber, System.currentTimeMillis()));
            message = "Intare " + plateNumber + " Ora " + new SimpleDateFormat("HH:mm").format(new Date());
        }
        else{
            System.out.println("Plate number found at index " + index);
            
            message = "Iesire " + plateNumber +  " COST " + computeParkingStayPrice(list.get(index).getAccessTime())  + " RON";
            list.remove(index);
        }
        
       f.setVisible(true);
       f.showAllCars(getAllCarsDetails());
            
       return message;
    }
       
    String getAllCarsDetails(){
       String all = "";
       for(CarAccess ca: list){ 
           if(ca!=null){
             
            SimpleDateFormat sdf = new SimpleDateFormat("MMM-dd-yyyy HH:mm");
            Date resultdate = new Date(ca.getAccessTime());
            all += "Car " + ca.getPlateNumber() + " at time " + sdf.format(resultdate) + "\n";
           }
       }
       return all;
   }
    
    void startServer() throws IOException{
        //....
        ServerSocket ss =new ServerSocket(4050);
        
        while(true){
            System.out.println("Astept conexiune de la client...");
            Socket s = ss.accept(); //metoda blocanta
            System.out.println("Clientul s-a conectat!");
            //...... 
            BufferedReader fluxIn = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter fluxOut = new PrintWriter(new OutputStreamWriter(s.getOutputStream()),true);
            //......
            String line = "";
              //citesc nr inmatriculare
              //apelez metoda handle plate number
              //returnez catre client rezultatul 
            while(!line.equals("close connection")){
                line = fluxIn.readLine();
                System.out.println("Am primti de la client: " + line);
                String result = this.handlePlateNumberRequest(line);
                System.out.println("Result is  " + result);
                fluxOut.println(result);

            }

 

            s.close();
        }
    }
    
    public static void main(String[] args) throws IOException, InterruptedException {
       Server s = new Server();
          
        String x = s.handlePlateNumberRequest("AB-01-AAA");
        System.out.println(x);
        Thread.sleep(2000);
        
        s.list.add(new CarAccess("AB-01-ABC", System.currentTimeMillis()));
        x = s.handlePlateNumberRequest("AB-01-AAA");
        System.out.println(x);
        Thread.sleep(2000);
        
        s.list.add(new CarAccess("AB-01-BBB", System.currentTimeMillis()));
        x = s.handlePlateNumberRequest("AB-01-AAA");
        System.out.println(x);
        Thread.sleep(2000);
        
        x = s.handlePlateNumberRequest("AB-01-ABC");
        System.out.println(x);
        
        s.startServer();
    }
}
