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

/**
 *
 * @author nagyp
 */
public class UserEntity {

  DatabaseServer db;
  private Connection conn;

  public String getUsername() {
    return username;
  }

  public String getPin() {
    return pin;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setPin(String pin) {
    this.pin = pin;
  }

  String username;
  String pin;

  int ip;

  int userId;

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public int getIp() {
    return ip;
  }

  public void setIp(int ip) {
    this.ip = ip;
  }

  public UserEntity() {
    try {
      dbConnection();
    } catch(Exception e) {

}
  }

  private void dbConnection() throws ClassNotFoundException,
  SQLException {

    DatabaseServer db = new DatabaseServer();
    this.conn = db.getConnection();
  }

  public boolean login() {

    boolean canLogin;
    try {
      canLogin = loginQuery();
    } catch(Exception e) {
      System.out.println(e.getMessage());
      canLogin = false;
    }
    return canLogin;
  }

  private boolean loginQuery() throws SQLException {
    Statement s = this.conn.createStatement();
    ResultSet rs = s.executeQuery("SELECT * FROM users WHERE username='" + this.username + "' AND pin='" + this.pin + "'");
    return rs.next();
  }

    public void getByUsername() {

    try {
      getuserIdByUsernameQuery();
    } catch(Exception e) {

    }

  }

  private void getuserIdByUsernameQuery() throws SQLException {
    Statement s = this.conn.createStatement();
    ResultSet rs = s.executeQuery("SELECT * FROM users WHERE username='" + this.getUsername() + "'");
    while (rs.next()) {
      // Read values using column name
      this.setUserId(Integer.parseInt(rs.getString("id")));
      this.setPin(rs.getString("pin"));
    }
  }

}