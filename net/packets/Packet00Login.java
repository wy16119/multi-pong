package net.packets;

import net.GameClient;
import net.GameServer;

public class Packet00Login extends Packet{

  private String username;
  private int x,y;
  private int position;
  
  public Packet00Login(byte[] data) {
    super(00);
    String[] dataArray = readData(data).split(",");
    this.username = dataArray[0];
    this.position = Integer.parseInt(dataArray[1]);
    this.x = Integer.parseInt(dataArray[2]);
    this.y = Integer.parseInt(dataArray[3]);
  }

  public Packet00Login(String username, int position, int x, int y) {
    super(00);
    this.username = username;
    this.position = position;
    this.x = x;
    this.y = y;
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
    return ("00" + this.username + "," + this.position + "," + getX() + "," + getY()).getBytes();
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

  public int getPosition() {
    return position;
  }
  
  public void setPosition(int position) {
    this.position = position;
  }
}
