package net.packets;

import net.GameClient;
import net.GameServer;

public class Packet02Move extends Packet {

  private String username;
  private int x;
  private int y;
  private int scale;
  private int lifeLeft;
  private int score;
  
  public Packet02Move(byte[] data) {
    super(02);
    String[] dataArray = readData(data).split(",");
    this.username = dataArray[0];
    this.x = Integer.parseInt(dataArray[1]);
    this.y = Integer.parseInt(dataArray[2]);
    this.scale = Integer.parseInt(dataArray[3]);
    this.lifeLeft = Integer.parseInt(dataArray[4]);
    this.score = Integer.parseInt(dataArray[5]);
  }

  public Packet02Move(String username, int x, int y, int scale, int lifeLeft, int score) {
    super(02);
    this.username = username;
    this.x = x;
    this.y = y;
    this.scale = scale;
    this.lifeLeft = lifeLeft;
    this.score = score;
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
    return ("02" + this.username + "," + getX() + "," + getY() + "," + getScale()+ "," + getLifeLeft() + "," + getScore()).getBytes();
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }
  
  public int getScale() {
    return scale;
  }
  
  public int getLifeLeft() {
    return lifeLeft;
  }
  
  public int getScore() {
    return score;
  }
  
  public String getUsername() {
    return username;
  }

}
