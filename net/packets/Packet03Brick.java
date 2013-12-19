package net.packets;

import game.Commons;
import net.GameClient;
import net.GameServer;

public class Packet03Brick extends Packet implements Commons{
  
  boolean bricks[] = new boolean[NUM_BRICKS];
  
  public Packet03Brick(byte[] data) {
    super(03);
    String[] dataArray = readData(data).split(",");
    for(int i = 0; i < NUM_BRICKS; i++) {
      if(dataArray[i+1].equalsIgnoreCase("1"))
        this.bricks[i] = true;
      else
        this.bricks[i] = false;
    }
  }

  public Packet03Brick(boolean[] bricks) {
    super(03);
    this.bricks = bricks;
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
    String message = "03";
    for(int i = 0; i < NUM_BRICKS; i++) {
      if(this.bricks[i])
        message = message + "," + "1";
      else
        message = message + "," + "0";
    }
    return message.getBytes();
  }

  public boolean[] getBricks() {
    return this.bricks;
  }

}