/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nps.barcute.logic;

import java.util.Random;

/**
 *
 * @author nagyp
 */

public class LogicOfBarcute {

  public int FIELD_SIZE = 10;

  public LogicOfBarcute(Integer FIELD_SIZE) {
    this.FIELD_SIZE = FIELD_SIZE;
  }

  public int[][] projectionMatrix() {
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

    System.out.println("===> Projection Map Initialized");
    char[][] map = new char[FIELD_SIZE][FIELD_SIZE];

    int[][] projectonMap = new int[FIELD_SIZE][FIELD_SIZE];

    for (int i = 0; i < FIELD_SIZE; i++) {
      for (int j = 0; j < FIELD_SIZE; j++) {
        if (field[i][j] == 0 || field[i][j] == 9) {
          projectonMap[i][j] = 0;
        } else {
          projectonMap[i][j] = 1;
        }
      }
    }
    return projectonMap;
  }
}