package net.packets;

import net.GameClient;
import net.GameServer;

public class Packet00Login extends Packet{

  private String username;
  
  public Packet00Login(byte[] data) {
    super(00);
    this.username = readData(data);
  }

  public Packet00Login(String username) {
    super(00);
    this.username = username;
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
    return ("00" + this.username).getBytes();
  }

  public String getUsername() {
    return username;
  }
}
