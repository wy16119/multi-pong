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
  
  public void writeData(GameClient client) {
    client.sendData(getData());
  }

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
