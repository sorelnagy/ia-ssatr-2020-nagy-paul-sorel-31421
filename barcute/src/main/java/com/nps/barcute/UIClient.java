/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nps.barcute;

import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;

import javax.swing.JList;
import javax.swing.JScrollPane;

import javax.swing.border.EmptyBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.BoxLayout;
import javax.swing.Box;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Component;
import java.awt.Container;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

import java.util. * ;
import java.util.stream.Collectors;

import static javax.swing.JOptionPane.showMessageDialog;

import java.util.Random;

import com.nps.barcute.Entity.UserEntity;
import com.nps.barcute.Entity.VictoryEntity;
import com.nps.barcute.Entity.MatchesEntity;
import com.nps.barcute.Entity.LosesEntity;
import com.nps.barcute.logic.LogicOfBarcute;

/**
 * 
 * @author nagyp
 */
public class UIClient {

  //public static int myPin = 4061;
  //public static int oponentPin = 4062;

  /**
     * @param args the command line arguments
     */

  public static JFrame appFrame;

  public static JPanel userOponentBoardPanel;
  public static JPanel userPanel;
  public static JPanel userDashboard;

  public static UserEntity user;

  public static int ownProjectionMap[][];

  public static int isGameInit = 0;

  public static int hitsGot = 0;
  public static int FIELD_SIZE = 15;

  public static int userInviteCode;
  public static int onlineUsersCheked = 0;

  public static void main(String[] args) {

    refactorProjectionMatrix();

    createAppFrame();
    createUserLoginPanel();

    appFrame.add(userPanel);

    userPanel.setVisible(true);
    appFrame.setVisible(true);

  }

  public static void refactorProjectionMatrix() {

    FIELD_SIZE = Integer.parseInt(talkToServer("GAME_DIFFICULTY"));
    LogicOfBarcute logicBarcute = new LogicOfBarcute(FIELD_SIZE);
    ownProjectionMap = logicBarcute.projectionMatrix();

  }

  public static void createAppFrame() {

    appFrame = new JFrame("BarcuteGame - Connecting on Barcute");
    appFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    appFrame.setLayout(null);
    //appFrame.setLocationRelativeTo(new Dimension(100, 100));
    appFrame.setPreferredSize(new Dimension(1200, 500));
    appFrame.setMinimumSize(new Dimension(1200, 500));

    appFrame.addWindowListener(new WindowAdapter() {@Override
      public void windowClosing(WindowEvent e) {
        talkToServer("USER_LOGOUT");
        System.out.println("WindowClosingDemo.windowClosing");
        System.exit(0);
      }
    });

  }

  public static void stopGame() {
    if (hitsGot >= 15) {
      showMessageDialog(null, "You LOOOOOST");

      user.getByUsername();
      LosesEntity lost = new LosesEntity(user.getUserId());
      lost.addLost();

    } else {
      showMessageDialog(null, "You WON");
      user.getByUsername();

      VictoryEntity victory = new VictoryEntity(user.getUserId());
      victory.addVictory();

    }

    appFrame.getContentPane().removeAll();
    appFrame.repaint();
    userPanel.setVisible(false);
    createUserDashboard();
    userPanel.setVisible(false);
    userDashboard.setVisible(true);

    refactorProjectionMatrix();

    hitsGot = 0;
    talkToServer("GAME_OVER," + user.getUsername() + "," + user.getIp());

  }

  public static void createOwnBoardPanel() {

    int dim = FIELD_SIZE;
    int matrix[][] = new int[FIELD_SIZE][FIELD_SIZE];

    JPanel userOwnBoardPanel3 = new JPanel();
    userOwnBoardPanel3.setLayout(new GridLayout(dim, dim));
    userOwnBoardPanel3.setBounds(0, 0, 400, 400);

    for (int r = 0; r < dim; r++) {
      for (int c = 0; c < dim; c++) {

        OwnButton button = new OwnButton(r, c, matrix);

        switch (ownProjectionMap[r][c]) {
        case 1:
          button.setBackground(Color.YELLOW);
          break;
        case 0:
          button.setBackground(Color.WHITE);

          break;

        case - 1 : button.setBackground(Color.RED);
          break;

        case - 2 : button.setBackground(Color.GRAY);
          break;
        }

        button.setForeground(Color.BLACK);
        Border line = new LineBorder(Color.BLACK);
        Border margin = new EmptyBorder(15, 15, 15, 15);
        Border compound = new CompoundBorder(line, margin);
        button.setBorder(compound);

        userOwnBoardPanel3.add(button);

      }
    }

    appFrame.add(userOwnBoardPanel3);
    appFrame.pack();
    userOwnBoardPanel3.setVisible(true);

  }

  public static void createOponentBoardPanel(int myPin) {
    int dim = FIELD_SIZE;
    int matrix[][] = new int[FIELD_SIZE][FIELD_SIZE];

    userOponentBoardPanel = new JPanel();
    userOponentBoardPanel.setLayout(new GridLayout(dim, dim));
    userOponentBoardPanel.setBounds(700, 0, 400, 400);

    for (int r = 0; r < dim; r++) {
      for (int c = 0; c < dim; c++) {
        ClickOnOponentButton button = new ClickOnOponentButton(r, c, matrix, myPin);

        button.setForeground(Color.BLACK);
        button.setBackground(Color.WHITE);
        Border line = new LineBorder(Color.BLACK);
        Border margin = new EmptyBorder(15, 15, 15, 15);
        Border compound = new CompoundBorder(line, margin);
        button.setBorder(compound);
        userOponentBoardPanel.add(button);

      }
    }
    appFrame.add(userOponentBoardPanel);
    appFrame.pack();

    userOponentBoardPanel.setVisible(true);
  }

  public static void createUserLoginPanel() {

    /*
    Login by user Panel
    */
    userPanel = new JPanel();
    userPanel.setBounds(10, 10, 400, 400);
    userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.Y_AXIS)); //Setting BoxLayout Vertically

    JLabel labelUsername = new JLabel("Username:");
    userPanel.add(labelUsername, BorderLayout.WEST);

    /*username textbox*/
    JTextField usernameTextbox = new JTextField(10);
    usernameTextbox.setBounds(0, 0, 20, 20);
    usernameTextbox.setMaximumSize(
    new Dimension(200, usernameTextbox.getPreferredSize().height));

    userPanel.add(usernameTextbox, BorderLayout.WEST);

    JLabel label1Pin = new JLabel("PIN:");
    userPanel.add(label1Pin, BorderLayout.WEST);

    JTextField pinTextbox = new JTextField(10);
    pinTextbox.setBounds(0, 0, 20, 20);

    pinTextbox.setMaximumSize(
    new Dimension(200, pinTextbox.getPreferredSize().height));

    userPanel.add(pinTextbox, BorderLayout.WEST);

    /*username textbox*/
    JButton buttonSubmitUsername = new JButton();
    buttonSubmitUsername.setText("Login Barcute");
    userPanel.add(buttonSubmitUsername);

    buttonSubmitUsername.addActionListener(new ActionListener() {@Override
      public void actionPerformed(ActionEvent e) {

        if (usernameTextbox.getText().equals("") || pinTextbox.getText().equals("")) {
          showMessageDialog(null, "Username and PIN required.");
          return;
        }
        user = new UserEntity();

        user.setUsername(usernameTextbox.getText());
        user.setPin(pinTextbox.getText());

        Random r = new Random();
        int low = 6000;
        int high = 7000;
        userInviteCode = r.nextInt(high - low) + low;
        user.setIp(userInviteCode);

        if (user.login()) {

          talkToServer("NEW_USER" + "," + user.getUsername() + "," + user.getIp());
          showMessageDialog(null, "Logged In");

          userPanel.setVisible(false);

          createUserDashboard();
          userPanel.setVisible(false);
          userDashboard.setVisible(true);

        } else {
          talkToServer("WRONG_CREDENTIALS");
          showMessageDialog(null, "Invalid credentials.");
        }

      }
    });
  }

  public static void createUserDashboard() {

    /*
    Login by user Panel
    */
    userDashboard = new JPanel();
    userDashboard.setBounds(25, 25, 900, 900);
    userDashboard.setLayout(new BoxLayout(userDashboard, BoxLayout.Y_AXIS)); //Setting BoxLayout Vertically

    appFrame.setTitle("Barcute Game - Welcome user " + user.getUsername());

    JLabel labelUsername = new JLabel("Welcome " + user.getUsername());
    labelUsername.setFont(new Font("Serif", Font.BOLD, 25));
    userDashboard.add(labelUsername, BorderLayout.LINE_START);

    JButton buttonSubmitStats = new JButton();
    buttonSubmitStats.setText("View Your Stats");
    userDashboard.add(buttonSubmitStats);

    buttonSubmitStats.addActionListener(new ActionListener() {@Override
      public void actionPerformed(ActionEvent e) {

        createStatsDashboard();

      }
    });

    /** NEED SPACE */
    userDashboard.add(Box.createVerticalStrut(25));
    userDashboard.add(new JLabel("**************************************"));

    JLabel labelInfo1 = new JLabel("You can use the following PIN to play with a friend or join a game by a given PIN.");
    labelInfo1.setFont(new Font("Serif", Font.BOLD, 15));
    userDashboard.add(labelInfo1, BorderLayout.LINE_START);

    JLabel labelInfoPin = new JLabel("PIN: " + String.valueOf(userInviteCode));
    labelInfoPin.setFont(new Font("Serif", Font.BOLD, 25));
    userDashboard.add(labelInfoPin, BorderLayout.LINE_START);

    /** NEED SPACE */
    userDashboard.add(Box.createVerticalStrut(25));
    userDashboard.add(new JLabel("**************************************"));

    /**
----------
*/

    /**
------------
*/

    JLabel labelInfoJoinFriend = new JLabel("Join a friend by his or her PIN");
    labelInfoJoinFriend.setFont(new Font("Serif", Font.BOLD, 15));
    userDashboard.add(labelInfoJoinFriend, BorderLayout.LINE_START);

    JTextField pinOpoenentTextbox = new JTextField(10);
    pinOpoenentTextbox.setBounds(0, 0, 20, 20);

    pinOpoenentTextbox.setMaximumSize(
    new Dimension(200, pinOpoenentTextbox.getPreferredSize().height));

    userDashboard.add(pinOpoenentTextbox, BorderLayout.WEST);

    /*username textbox*/
    JButton buttonSubmitInviteCode = new JButton();
    buttonSubmitInviteCode.setText("JOIN by PIN ->");
    userDashboard.add(buttonSubmitInviteCode);

    /** NEED SPACE */
    userDashboard.add(Box.createVerticalStrut(25));
    userDashboard.add(new JLabel("**************************************"));

    JLabel labelInfoPlayRandom = new JLabel("Play a random game");
    labelInfoPlayRandom.setFont(new Font("Serif", Font.BOLD, 15));
    userDashboard.add(labelInfoPlayRandom, BorderLayout.LINE_START);

    JButton buttonStartLookingForGames = new JButton();
    buttonStartLookingForGames.setText("RANDOMIZE()");
    userDashboard.add(buttonStartLookingForGames);

    buttonStartLookingForGames.addActionListener(new ActionListener() {@Override
      public void actionPerformed(ActionEvent e) {
        //

        //Check if we have online users
        onlineUsersCheked = 0;
        talkToServer("ONLINE_USERS," + userInviteCode);

        try {
          String pips = talkToServer("GET_OPPONENT," + userInviteCode);
          Integer pip = Integer.parseInt(pips);
          talkToServer("START_GAME," + userInviteCode + "," + pip);
          startGame(userInviteCode, pip);
        } catch(Exception ex) {
          System.out.println(ex.getMessage());
        }

      }
    });

    buttonSubmitInviteCode.addActionListener(new ActionListener() {@Override

      public void actionPerformed(ActionEvent e) {

        if (pinOpoenentTextbox.getText().equals("") || pinOpoenentTextbox.getText().length() != 4) {
          showMessageDialog(null, "Invalid PIN");
        } else {
          Integer pip = Integer.parseInt(pinOpoenentTextbox.getText());
          //Start the game
          startGame(userInviteCode, pip);
          talkToServer("START_GAME," + userInviteCode + "," + pip);
        }

      }
    });

    //Listen to server
    listenMyOwnBoardForAttack(userInviteCode);

    appFrame.add(userDashboard);
    userDashboard.setVisible(false);
  }

  public static String talkToServer(String messageToTell) {
    String messageToReturn = "";
    try {
      Socket s = new Socket("127.0.0.1", 4000);
      BufferedReader fluxIn = new BufferedReader(new InputStreamReader(s.getInputStream()));
      PrintWriter fluxOut = new PrintWriter(new OutputStreamWriter(s.getOutputStream()), true);

      fluxOut.println(messageToTell);

      messageToReturn = fluxIn.readLine();

      s.close();
    } catch(Exception ex) {
      System.out.println(ex.getMessage());
    }
    return messageToReturn;
  }

  public static void startGame(int pin1, int pin2) {
    userPanel.setVisible(false);
    userDashboard.setVisible(false);
    
    createOponentBoardPanel(pin2);
    createOwnBoardPanel();

     Runnable projectionMapTask = new Runnable() {@Override
      public void run() {
        refactorProjectionMatrix();
      }
    };
    Thread projectionMapThread = new Thread(projectionMapTask);
    projectionMapThread.start();

    isGameInit = 1;
  }

  public static void disableGame() {
    userDashboard.setVisible(false);
  }

  public static void enableGame() {

    userDashboard.setVisible(true);
  }

  public static void listenMyOwnBoardForAttack(int oponentPin) {
    Runnable serverTask = new Runnable() {@Override
      public void run() {

        try {
          ServerSocket ss = new ServerSocket(oponentPin);

          while (true) {
            Socket s = ss.accept();
            BufferedReader fluxIn = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter fluxOut = new PrintWriter(new OutputStreamWriter(s.getOutputStream()), true);

            String coordonates = fluxIn.readLine();

            String[] intCoordonates = coordonates.split(",");

            if (coordonates.startsWith("START")) {
              int pip = Integer.parseInt(intCoordonates[1]);
              startGame(userInviteCode, pip);
            }
            else if (coordonates.equals("NO_ONLINE_USERS")) {

} else {

              System.out.println("Attacked on coordonates X: " + intCoordonates[0] + ", Y: " + intCoordonates[1]);

              int hitX = Integer.parseInt(intCoordonates[0]);
              int hitY = Integer.parseInt(intCoordonates[1]);

              System.out.println("Attacked on coordonates X: " + intCoordonates[0] + ", Y: " + intCoordonates[1]);

              if (ownProjectionMap[hitX][hitY] == 1) {

                hitsGot++;
                ownProjectionMap[hitX][hitY] = -1;

              } else {
                fluxOut.println("NOHIT");
                ownProjectionMap[hitX][hitY] = -2;
              }

              setEnableRec(userOponentBoardPanel, true);
              //  userOponentBoardPanel.setEnabled(true);
              createOwnBoardPanel();

              if (hitsGot >= 15) {
                fluxOut.println("SUNT_DONE");
                stopGame();
              } else {
                fluxOut.println("HIT");
              }
            }

            s.close();
          }
        } catch(Exception e) {
          e.printStackTrace();
        }

      }
    };
    Thread serverThread = new Thread(serverTask);
    serverThread.start();

  }

  public static void noOnlineUsers() {
    onlineUsersCheked = 1;
    showMessageDialog(null, "No one to play withy :(");
  }

  public static void talkToOponenet(int oponentPin, String messageToSend) {

    try {
      Socket s = new Socket("127.0.0.1", oponentPin);
      BufferedReader fluxIn = new BufferedReader(new InputStreamReader(s.getInputStream()));
      PrintWriter fluxOut = new PrintWriter(new OutputStreamWriter(s.getOutputStream()), true);

      fluxOut.println(messageToSend);

      s.close();
    } catch(Exception ex) {
      System.out.println(ex.getMessage());
    }
  }

  public static void setEnableRec(Component container, boolean enable) {
    container.setEnabled(enable);

    try {
      Component[] components = ((Container) container).getComponents();
      for (int i = 0; i < components.length; i++) {
        setEnableRec(components[i], enable);
      }
    } catch(ClassCastException e) {

}
  }

  public static void createStatsDashboard() {
    JFrame statsFrame = new JFrame("View your STATS");
    //statsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    statsFrame.setLayout(null);
    statsFrame.setLocationRelativeTo(null);
    statsFrame.setPreferredSize(new Dimension(500, 500));
    statsFrame.setMinimumSize(new Dimension(500, 500));
    statsFrame.setVisible(true);

    JPanel statsDashboardPanel = new JPanel();
    statsDashboardPanel.setBounds(0, 0, 900, 900);
    statsDashboardPanel.setLayout(new BoxLayout(statsDashboardPanel, BoxLayout.Y_AXIS)); //Setting BoxLayout Vertically

    user.getByUsername();

    VictoryEntity victory = new VictoryEntity(user.getUserId());
    JLabel labelVictories = new JLabel("Victories: " + victory.getVictoriesCount());
    labelVictories.setFont(new Font("Serif", Font.BOLD, 25));
    statsDashboardPanel.add(labelVictories, BorderLayout.LINE_START);

    LosesEntity loses = new LosesEntity(user.getUserId());
    JLabel labelLoses = new JLabel("Loses: " + loses.getLosesCount());
    labelLoses.setFont(new Font("Serif", Font.BOLD, 25));
    statsDashboardPanel.add(labelLoses, BorderLayout.LINE_START);

    MatchesEntity matches = new MatchesEntity();
    List < String > oponents = new ArrayList < String > ();

    oponents = matches.getOponents(user.getUsername());

    final JList < String > list = new JList < String > (oponents.toArray(new String[oponents.size()]));
    JScrollPane scrollPane = new JScrollPane();
    scrollPane.setViewportView(list);
    list.setLayoutOrientation(JList.VERTICAL);
    statsDashboardPanel.add(scrollPane, BorderLayout.LINE_START);

    statsDashboardPanel.setVisible(true);
    statsFrame.add(statsDashboardPanel);
  }

}