
import java.util.Arrays;
import java.util.Random;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author nagyp
 */
public class TestBarcute {
    
public static int FIELD_SIZE = 10;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        deployPlayerShips();
    }
    
    public static void deployPlayerShips() {
    Random random = new Random();
    int[][] field = new int[FIELD_SIZE][FIELD_SIZE];
    for (int i = 5; i > 0; i--) {
        int x = random.nextInt(field.length);
        int y = random.nextInt(field.length);
        boolean vertical = random.nextBoolean();

        if (vertical) {
            if (y + i > FIELD_SIZE) {
                y -= i;
            }
        } else if (x + i > FIELD_SIZE) {
            x -= i;
        }
        boolean isFree = true;

        if (vertical) {
            for (int m = y; m < y + i; m++) {
                if (field[m][x] != 0) {
                    isFree = false;
                    break;
                }
            }
        } else {
            for (int n = x; n < x + i; n++) {
                if (field[y][n] != 0) {
                    isFree = false;
                    break;
                }
            }
        }
        if (!isFree) {
            i++;
            continue;
        }

        if (vertical) {
            for (int m = Math.max(0, x - 1); m < Math.min(FIELD_SIZE, x + 2); m++) {
                for (int n = Math.max(0, y - 1); n < Math.min(FIELD_SIZE, y + i + 1); n++) {
                    field[n][m] = 9;
                }
            }
        } else {
            for (int m = Math.max(0, y - 1); m < Math.min(FIELD_SIZE, y + 2); m++) {
                for (int n = Math.max(0, x - 1); n < Math.min(FIELD_SIZE, x + i + 1); n++) {
                    field[m][n] = 9;
                }
            }
        }

        for (int j = 0; j < i; j++) {
            field[y][x] = i;
            if (vertical) {
                y++;
            } else {
                x++;
            }
        }
    }

    System.out.print(" ");
    System.out.println("0 1 2 3 4 5 6 7 8 9");
    char[][] map = new char[FIELD_SIZE][FIELD_SIZE];
    
    int[][] projectonMap = new int[FIELD_SIZE][FIELD_SIZE];
    
    for (int i = 0; i < FIELD_SIZE; i++) {
        for (int j = 0; j < FIELD_SIZE; j++) {
            if(field[i][j] == 0 || field[i][j] == 9){
               projectonMap[i][j] = 0;
            }else{
                projectonMap[i][j] = 1;
            }
          }
    }
    
      for (int i = 0; i < FIELD_SIZE; i++) {
        for (int j = 0; j < FIELD_SIZE; j++) {
          System.out.println(projectonMap[i][j]);
         }
        System.out.println("\n");
    }


     

     
   
}
    
}
