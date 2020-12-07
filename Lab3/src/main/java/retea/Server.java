package retea;

import java.net.*;
import java.io.*;
import java.time.Instant;

public class Server {
    public static void main(String[] args) throws IOException {
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
            while(!line.equals("close connection")){
                line = fluxIn.readLine();
                if(line == null)
                    break;
                
                if(line.contains("+")){
                    TestString.getResult(line);
                    String rez = TestString.getResult(line);
                    fluxOut.println(rez);
                }
                System.out.println("Am primti de la client: "+line);
                line = "ECHO from server "+line+" at "+Instant.now().toString();
                fluxOut.println(line);
                
                
            }

            s.close();
        }
    }
}