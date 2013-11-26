package net.packets;

import net.GameClient;
import net.GameServer;

public class Packet02Move extends Packet {

  private String username;
  private int x;
  private int ballX;
  private int ballY;
  
  public Packet02Move(byte[] data) {
    super(02);
    String[] dataArray = readData(data).split(",");
    this.username = dataArray[0];
    this.x = Integer.parseInt(dataArray[1]);
    this.ballX = Integer.parseInt(dataArray[2]);
    this.ballY = Integer.parseInt(dataArray[3]);
//    this.y = Integer.parseInt(dataArray[2]);
  }

  public Packet02Move(String username, int x, int ballX, int ballY) {
    super(02);
    this.username = username;
    this.x = x;
    this.ballX = ballX;
    this.ballY = ballY;
//    this.y = y;
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
    return ("02" + this.username + "," + getX() + "," + getBallX() + "," + getBallY()).getBytes();
  }

  public int getX() {
    return x;
  }

  public int getBallX() {
    return ballX;
  }
  
  public int getBallY() {
    return ballY;
  }

  

  public String getUsername() {
    return username;
  }
}
