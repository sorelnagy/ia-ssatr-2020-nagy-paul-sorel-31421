/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nps.barcute.Entity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.nps.barcute.server.DatabaseServer;
import java.util. * ;

/**
 *
 * @author nagyp
 */
public class MatchesEntity {

  DatabaseServer db;
  private Connection conn;

  public MatchesEntity() {
    try {
      dbConnection();
    } catch(Exception e) {

}
  }

  public String getPlayer1() {
    return player1;
  }

  public void setPlayer1(String player1) {
    this.player1 = player1;
  }

  public String getPlayer2() {
    return player2;
  }

  public void setPlayer2(String player2) {
    this.player2 = player2;
  }

  String player1;
  String player2;
  String type;

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  private void dbConnection() throws ClassNotFoundException,
  SQLException {

    DatabaseServer db = new DatabaseServer();
    this.conn = db.getConnection();
  }

  public void addMatch() {

    try {
      addMatchQuery();
    } catch(Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public List < String > getOponents(String username) {

    List < String > oponents = new ArrayList < String > ();

    try {
      oponents = getOponentsQuery(username);
    } catch(Exception e) {
      System.out.println(e.getMessage());
    }
    return oponents;

  }

  private void addMatchQuery() throws SQLException {
    Statement s = this.conn.createStatement();
    s.executeUpdate("INSERT INTO matches(player1,player2,type) VALUES ('" + this.getPlayer1() + "','" + this.getPlayer2() + "','" + this.getType() + "')");
  }

  private List < String > getOponentsQuery(String username) throws SQLException {
    Statement s = this.conn.createStatement();
    ResultSet rs = s.executeQuery("SELECT * FROM matches WHERE player1='" + username + "' OR player2='" + username + "'");

    List < String > oponents = new ArrayList < String > ();

    while (rs.next()) {

      if (rs.getString("player1").equals(username)) {
        oponents.add("You played versus: " + rs.getString("player2") + " in a " + rs.getString("type").toUpperCase() + " game");
      } else {
        oponents.add("You played versus: " + rs.getString("player1") + " in a " + rs.getString("type").toUpperCase() + " game");
      }

    }
    return oponents;
  }

}