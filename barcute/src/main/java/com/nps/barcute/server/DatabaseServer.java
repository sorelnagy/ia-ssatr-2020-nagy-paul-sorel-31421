/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nps.barcute.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseServer {

  private Connection conn;

  public DatabaseServer() throws ClassNotFoundException,
  SQLException {
    Class.forName("org.apache.derby.jdbc.ClientDriver");
    conn = DriverManager.getConnection("jdbc:derby://localhost/barcute_db;create=false", "barcute", "barcute");
  }

  public static void main(String[] args) throws ClassNotFoundException,
  SQLException {

    DatabaseServer db = new DatabaseServer();
    System.out.println("===> Connected to the database.");

  }

  public Connection getConnection() {
    return this.conn;
  }

}