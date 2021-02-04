/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nps.barcute;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.awt.Color;
/**
 *
 * @author nagyp
 */
public class OwnButton extends JButton {

  private final int[][] fModel;
  private final int fX;
  private final int fY;

  public OwnButton(final int x, final int y, final int[][] model) {
    fX = x;
    fY = y;
    fModel = model;

    addActionListener(new ActionListener() {@Override
      public void actionPerformed(ActionEvent e) {}
    });
    updateNameFromModel();
  }

  private void updateNameFromModel() {
    setEnabled(false);
  }

}