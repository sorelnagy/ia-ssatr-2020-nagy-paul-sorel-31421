/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nps.barcute.server;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.BoxLayout;
import static javax.swing.JOptionPane.showMessageDialog;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import com.nps.barcute.logic.LogicOfBarcute;

import com.nps.barcute.Entity.MatchesEntity;
/**
 *
 * @author nagyp
 */
public class ServerBarcute {

  private Map < String,
  Integer > userIpMap = new HashMap < >();

  private int baseIp = 4060;

  public static JFrame serverStatsFrame;
  public static JPanel serverStatsPanel;
  public static JLabel label1StatsNumber;
  public static JLabel label1StatsGamesNumber;
  int connectedUsers = 0;
  int playingGames = 0;
  public static JTextField difficultyGameText;
  public void startServer() {

    try {

      ServerSocket ss = new ServerSocket(4000);

      while (true) {
        System.out.println("===> Connected to the server");
        Socket s = ss.accept();
        System.out.println("===> New client that wants something.");

        BufferedReader fluxIn = new BufferedReader(new InputStreamReader(s.getInputStream()));
        PrintWriter fluxOut = new PrintWriter(new OutputStreamWriter(s.getOutputStream()), true);

        String clientMessage = fluxIn.readLine();

        /*if(clientMessage.startsWith("LOGIN")){
                userIpMap.put(clientMessage.replace("LOGIN", ""), baseIp++);
                fluxOut.println("SUCCESS");
            }*/

        if (clientMessage.startsWith("NEW_USER")) {
          String[] cm = clientMessage.split(",");
          String username = cm[1];
          Integer port = Integer.parseInt(cm[2]);
          userIpMap.put(username, port);
          connectedUsers++;
          label1StatsNumber.setText(String.valueOf(connectedUsers));

          //  for (Map.Entry<String, Integer> entry : userIpMap.entrySet()) {
          //   talkToClient(entry.getValue(), "USERS_COUNT,"+userIpMap.size());	
          // }

          System.out.println("NEW USER CONNECTED");
        }

        if (clientMessage.equals("WRONG_CREDENTIALS")) {
          System.out.println("INVALID CREDENTIALS FROM USER");
        }

        if (clientMessage.equals("USER_LOGOUT")) {
          connectedUsers--;
          if (connectedUsers < 0) {
            connectedUsers = 0;
          }
          label1StatsNumber.setText(String.valueOf(connectedUsers));
          System.out.println("USER LEFT");
        }

        if (clientMessage.equals("GAME_DIFFICULTY")) {
          fluxOut.println(difficultyGameText.getText());
        }

        if (clientMessage.startsWith("START_GAME")) {
          String[] cm = clientMessage.split(",");
          Integer invCode = Integer.parseInt(cm[1]);
          Integer pip = Integer.parseInt(cm[2]);

          String usernamePlayer1 = getKey(userIpMap, invCode);
          String usernamePlayer2 = getKey(userIpMap, pip);

          if (usernamePlayer1 != null && usernamePlayer2 != null) {
            createMatch(usernamePlayer1, usernamePlayer2, "private");
          }

          playingGames++;
          label1StatsGamesNumber.setText(String.valueOf(playingGames));

          talkToClient(pip, "START," + invCode);

        }

        if (clientMessage.startsWith("ONLINE_USERS")) {
          String[] cm = clientMessage.split(",");
          Integer clientPort = Integer.parseInt(cm[1]);
          talkToClient(clientPort, "NO_ONLINE_USERS");
        }

        if (clientMessage.startsWith("GET_OPPONENT")) {
          String[] cm = clientMessage.split(",");
          Integer invCode = Integer.parseInt(cm[1]);
          Random r = new Random();

          String usernamePlayer1 = getKey(userIpMap, invCode);

          userIpMap.remove(getKey(userIpMap, invCode));
          Integer pip = userIpMap.values().stream().filter(pupi ->pupi != invCode).skip(r.nextInt(userIpMap.size())).findFirst().get();

          String usernamePlayer2 = getKey(userIpMap, pip);
          userIpMap.remove(getKey(userIpMap, pip));

          createMatch(usernamePlayer1, usernamePlayer2, "random");
          fluxOut.println(pip);

        }
        if (clientMessage.startsWith("GAME_OVER")) {
          String[] cm = clientMessage.split(",");
          String username = cm[1];
          Integer port = Integer.parseInt(cm[2]);

          playingGames--;
          if (playingGames < 0) {
            playingGames = 0;
          }
          label1StatsGamesNumber.setText(String.valueOf(playingGames));

          userIpMap.put(username, port);

        }

        for (Map.Entry < String, Integer > entry: userIpMap.entrySet()) {
          System.out.println("Username: " + entry.getKey() + " ip: " + entry.getValue());
        }

        s.close();
      }
    } catch(Exception e) {
      e.printStackTrace();
    }
  }

  public static < K,
  V > K getKey(Map < K, V > map, V value) {
    for (Map.Entry < K, V > entry: map.entrySet()) {
      if (value.equals(entry.getValue())) {
        return entry.getKey();
      }
    }
    return null;
  }

  public static String talkToClient(Integer ip, String messageToTell) {
    String messageToReturn = "";
    try {
      Socket s = new Socket("127.0.0.1", ip);
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

  public static void main(String[] args) throws ClassNotFoundException,
  SQLException {

    createServerFrame();
    createServerStats();

    ServerBarcute netCon = new ServerBarcute();
    netCon.startServer();
    Runnable serverTask = new Runnable() {@Override
      public void run() {
        netCon.startGameServer();
      }
    };

    Thread serverThread = new Thread(serverTask);
    serverThread.start();

  }

  public static void createServerFrame() {

    serverStatsFrame = new JFrame("BarcuteGame - Server Stats");
    serverStatsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    serverStatsFrame.setLayout(null);
    serverStatsFrame.setLocationRelativeTo(null);
    serverStatsFrame.setPreferredSize(new Dimension(500, 500));
    serverStatsFrame.setMinimumSize(new Dimension(500, 500));

    serverStatsFrame.setVisible(true);
    serverStatsFrame.pack();

  }

  public static void createServerStats() {

    serverStatsPanel = new JPanel();
    serverStatsPanel.setBounds(25, 25, 500, 500);
    serverStatsPanel.setLayout(new BoxLayout(serverStatsPanel, BoxLayout.Y_AXIS)); //Setting BoxLayout Vertically

    JLabel label1Info1 = new JLabel("Barcute's Server stats.");
    label1Info1.setFont(new Font("Serif", Font.PLAIN, 20));
    serverStatsPanel.add(label1Info1, BorderLayout.LINE_START);

    JLabel label1Stats = new JLabel("Connected Users: ");
    serverStatsPanel.add(label1Stats);
    label1StatsNumber = new JLabel("0");
    label1StatsNumber.setFont(new Font("Serif", Font.PLAIN, 25));
    serverStatsPanel.add(label1StatsNumber);

    JLabel label1StatsGames = new JLabel("Games in progress: ");
    serverStatsPanel.add(label1StatsGames, BorderLayout.LINE_START);

    label1StatsGamesNumber = new JLabel("0");
    label1StatsGamesNumber.setFont(new Font("Serif", Font.PLAIN, 25));
    serverStatsPanel.add(label1StatsGamesNumber);

    serverStatsPanel.add(new JLabel("GAME DIFFICULTY"));
    difficultyGameText = new JTextField(10);
    difficultyGameText.setBounds(0, 0, 20, 20);
    difficultyGameText.setMaximumSize(
    new Dimension(200, difficultyGameText.getPreferredSize().height));
    difficultyGameText.setText(String.valueOf(10));
    serverStatsPanel.add(difficultyGameText, BorderLayout.WEST);

    JButton difficultyGameButton = new JButton();
    difficultyGameButton.setText("SET DIFFICULTY");
    serverStatsPanel.add(difficultyGameButton);

    difficultyGameButton.addActionListener(new ActionListener() {@Override
      public void actionPerformed(ActionEvent e) {
        showMessageDialog(null, "Is SET");
      }
    });

    serverStatsPanel.setVisible(true);

    serverStatsFrame.add(serverStatsPanel);

  }

  public void startGameServer() {

}

  private void createMatch(String username1, String username2, String type) {
    MatchesEntity match = new MatchesEntity();
    match.setPlayer1(username1);
    match.setPlayer2(username2);
    match.setType(type);
    match.addMatch();
    System.out.println("===> New MATCH:" + username1 + " vs " + username2 + " on " + type + " type");
  }

}