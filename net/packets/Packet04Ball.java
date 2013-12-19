package net.packets;

import net.GameClient;
import net.GameServer;


public class Packet04Ball extends Packet {
  private int id; 
  private int ballX;
  private int ballY;
  private boolean isItLost;
  
  public Packet04Ball(byte[] data) {
    super(04);
    String[] dataArray = readData(data).split(",");
    this.id = Integer.parseInt(dataArray[0]);
    this.ballX = Integer.parseInt(dataArray[1]);
    this.ballY = Integer.parseInt(dataArray[2]);
    if(dataArray[3] == "true")
      this.isItLost = true;
    else 
      this.isItLost = false;
      
  }

  public Packet04Ball(int i, int ballX, int ballY, boolean isItLost) {
    super(04);
    this.id = i;
    this.ballX = ballX;
    this.ballY = ballY;
    this.isItLost = isItLost;
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
    if(getLost()) 
      return ("04" + this.id + "," + getBallX() + "," + getBallY() + "," + "true").getBytes();
    else
      return ("04" + this.id + "," + getBallX() + "," + getBallY() + "," + "false").getBytes();     
  }
  
  public int getId() {
    return id;
  }
  
  public int getBallX() {
    return ballX;
  }
  
  public int getBallY() {
    return ballY;
  }

  public boolean getLost() {
    return isItLost;
  }
}
