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

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author nagyp
 */
public class VictoryEntity {

  DatabaseServer db;
  private Connection conn;

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public String getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }

  Integer userId;
  String createdAt;

  public VictoryEntity(Integer userId) {

    this.userId = userId;

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

  public void addVictory() {

    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    Date date = new Date();
    this.setCreatedAt(String.valueOf(formatter.format(date)));

    try {
      addVictoryQuery();
    } catch(Exception e) {
      System.out.println(e.getMessage());
    }

  }

  public int getVictoriesCount() {

    try {
      return getVictoriesCountQuery();
    } catch(Exception e) {
      System.out.println(e.getMessage());
    }

    return 0;

  }

  private void addVictoryQuery() throws SQLException {
    Statement s = this.conn.createStatement();
    s.executeUpdate("INSERT INTO victories(user_id,created_at) VALUES (" + this.getUserId() + ",'" + this.getCreatedAt() + "')");
  }

  private int getVictoriesCountQuery() throws SQLException {
    Statement s = this.conn.createStatement();
    String query = "SELECT COUNT(*) FROM victories WHERE user_id =" + this.getUserId();
    ResultSet rs = s.executeQuery(query);
    rs.next();
    return rs.getInt(1);

  }

}