package retea;

import java.util.StringTokenizer;

public class TestString {
  
    
    public static String getResult(String str){
    String token = "";
    if(str.contains("+")) token = "+";

    StringTokenizer st = new StringTokenizer(str,token);
    int op1 = Integer.parseInt(st.nextToken().trim());
    int op2 = Integer.parseInt(st.nextToken().trim());
    int rez = op1 + op2;
    return "Rezultat = "+rez;

    }
}