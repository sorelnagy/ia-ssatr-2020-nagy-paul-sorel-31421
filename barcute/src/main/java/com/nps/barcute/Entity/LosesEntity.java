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
public class LosesEntity {

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

  public LosesEntity(Integer userId) {

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

  public void addLost() {

    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    Date date = new Date();
    this.setCreatedAt(String.valueOf(formatter.format(date)));

    try {
      addLostQuery();
    } catch(Exception e) {
      System.out.println(e.getMessage());
    }

  }

  public int getLosesCount() {

    try {
      return getLosesCountQuery();
    } catch(Exception e) {
      System.out.println(e.getMessage());
    }

    return 0;

  }

  private void addLostQuery() throws SQLException {
    Statement s = this.conn.createStatement();
    s.executeUpdate("INSERT INTO loses(user_id,created_at) VALUES (" + this.getUserId() + ",'" + this.getCreatedAt() + "')");
  }

  private int getLosesCountQuery() throws SQLException {
    Statement s = this.conn.createStatement();
    String query = "SELECT COUNT(*) FROM loses WHERE user_id =" + this.getUserId();
    ResultSet rs = s.executeQuery(query);
    rs.next();
    return rs.getInt(1);

  }

}