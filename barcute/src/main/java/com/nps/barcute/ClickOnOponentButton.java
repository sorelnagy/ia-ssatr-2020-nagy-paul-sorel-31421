/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nps.barcute;
/**
 * Sockets
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
/**
 *
 * @author nagyp
 */
public class ClickOnOponentButton extends JButton {

  private final int[][] fModel;
  private final int fX;
  private final int fY;
  private final int oponentPin1;

  public ClickOnOponentButton(final int x, final int y, final int[][] model, final int oponentPin) {

    fX = x;
    fY = y;
    fModel = model;
    oponentPin1 = oponentPin;

    addActionListener(new ActionListener() {@Override
      public void actionPerformed(ActionEvent e) {
        //UIClient.userOponentBoardPanel.setEnabled(false);
        UIClient.setEnableRec(UIClient.userOponentBoardPanel, false);
        try {
          Socket s = new Socket("127.0.0.1", oponentPin1);
          BufferedReader fluxIn = new BufferedReader(new InputStreamReader(s.getInputStream()));
          PrintWriter fluxOut = new PrintWriter(new OutputStreamWriter(s.getOutputStream()), true);

          fluxOut.println(fX + "," + fY);

          String oponentMessage = fluxIn.readLine();
          System.out.println(oponentMessage);

          switch (oponentMessage) {

          case "HIT":
            setBackground(Color.RED);
            setEnabled(false);
            break;

          case "NOHIT":
            setBackground(Color.GRAY);
            setEnabled(false);
            break;

          case "SUNT_DONE":
            UIClient.stopGame();
            break;
          }

          s.close();
        } catch(Exception ex) {

          System.out.println(ex.getMessage());
        }
      }

    });

  }

}