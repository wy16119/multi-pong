package net;

import game.Player;
import game.PlayerMP;

import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;

public class Database {
  private String driver, url, user, password;
  Connection conn;
  
  public Database()
  {
//    this.driver = "org.h2.Driver";
//    this.url = "jdbc:h2:tcp://localhost/~/285final_1";
//    this.user = "sa";
//    this.password = "";
//    
    this.driver = "com.mysql.jdbc.Driver";
    this.url = "jdbc:mysql://127.0.0.1/285final_trail1";
    this.user = "root";
    this.password = "0";
//    this.password = "xZ9pq3d7jJ7M4yFe";
    this.conn = null;
  }
  
  private void InitTables()
  {
    try
    {
      Statement statement = conn.createStatement();
      String sql = "CREATE TABLE IF NOT EXISTS players ("
          + "id INT PRIMARY KEY AUTO_INCREMENT, "
          + "username VARCHAR(50) NOT NULL, "
          + "password VARCHAR(50) NOT NULL, "
          + "score INT"
          + ");";
      //System.out.println(sql);
      statement.executeUpdate(sql);
      System.out.println("Succeeded initiating the table!");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
  
  public void Connect ()
  {
    try
    {
      Class.forName(driver).newInstance();
      conn = DriverManager.getConnection(url, user, password);
      if (!conn.isClosed())
        System.out.println("Succeeded connecting to the Database!");
      else
        System.out.println("Failed connecting to the Database!");
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
    InitTables();
  }
  
  public boolean Query(String sql)
  {
    try
    {
      Statement statement = conn.createStatement();
      statement.executeQuery(sql);
      return true;
    }
    catch (SQLException e){
      e.printStackTrace();
    }
    return true;
  }
  
  public void addPlayer(PlayerMP player)
  {
    try
    {
      Statement statement = conn.createStatement();
      String sql = "SELECT * FROM players WHERE username = '" + player.getUsername() + "'";
      //System.out.println(sql);
      ResultSet res = statement.executeQuery(sql);
      if (res.next())
      {
        System.out.println("Player " + player.getUsername() + "already exist");
        return;
      }
      sql = "INSERT INTO `players` "
            + "(`id`, `username`, `password`, `score`) "
            + "VALUES (NULL, "
            + "'" + player.getUsername() + "', "
            + "'" + player.getPassword() + "', "
            + "NULL);";
      statement.executeUpdate(sql);
      System.out.println("Succeed adding player" + player.getUsername());
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
  
  public void UpdateScore(PlayerMP player, int scoreInc)
  {
    int newScore;
    try
    {
      Statement statement = conn.createStatement();
      String sql = "SELECT score FROM players WHERE username = '" + player.getUsername() + "'";
      ResultSet res = statement.executeQuery(sql);
      if (res.next())
        newScore = res.getInt("score") + scoreInc;
      else
      {
        System.out.println("ERROR no return value");
        return;
      }
      sql = "UPDATE players SET score = " + newScore + " WHERE username = '" + player.getUsername() + "'";
      statement.executeUpdate(sql);
      System.out.println("Player " + player.getUsername() + "'s current score:" + newScore);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
  
  public boolean Authorize(String username, String password)
  {
    System.out.print("Authorize");
    try
    {
      Statement statement = conn.createStatement();
      String sql = "SELECT password FROM players WHERE username = '" + username + "'";
      ResultSet res = statement.executeQuery(sql);
      if (res.next())
      {
        String correctPwd = res.getString("password");
        if (correctPwd.equals(password))
        {
          System.out.print(" success");
          return true;
        }
        else
        {
          System.out.print(" fail");
          return false;
        }
      }
      else
      {
        System.out.print(" pending. New player register");
        PlayerMP newPlayer = new PlayerMP(username, password, -1, -1, null, -1);
        this.addPlayer(newPlayer);
        return true;
      }        
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }
}
