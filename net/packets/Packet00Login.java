package net.packets;

import net.GameClient;
import net.GameServer;

public class Packet00Login extends Packet{

  private String username;
  private String password;
  private int position;
  private int x,y;
  private int score;
  private int valid;
  
  public Packet00Login(byte[] data) {
    super(00);
    String[] dataArray = readData(data).split(",");
    this.username = dataArray[0];
    this.password = dataArray[1];
    this.position = Integer.parseInt(dataArray[2]);
    this.x = Integer.parseInt(dataArray[3]);
    this.y = Integer.parseInt(dataArray[4]);
    this.score = Integer.parseInt(dataArray[5]);
    this.valid = Integer.parseInt(dataArray[6]);
  }

  public Packet00Login(String username, String password, int position, int x, int y, int score, int valid) {
    super(00);
    this.username = username;
    this.password = password;
    this.position = position;
    this.x = x;
    this.y = y;
    this.score = score;
    this.valid = valid;
  }
  
//  send datatype+username to client socket
//  then client write data to its socket
  public void writeData(GameClient client) {
    client.sendData(getData());
  }
//  send datatype+username to server socket
//  then server write data to all clients
  public void writeData(GameServer server) {
    server.sendDataToAllClients(getData());
  }

  public byte[] getData() {
    return ("00" + this.username + "," + this.password + "," + this.position + "," + getX() + "," + getY() + "," + getScore() + "," + getValid()).getBytes();
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public String getUsername() {
    return username;
  }
  
  public String getPassword() {
    return password;
  }
  
  public int getPosition() {
    return position;
  }
  
  public int getScore() {
    return score;
  }
  
  public int getValid() {
    return valid;
  }
  
  public void setPosition(int position) {
    this.position = position;
  }
}
