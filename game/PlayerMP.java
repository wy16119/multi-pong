package game;

import java.net.InetAddress;

public class PlayerMP extends Player {

  public InetAddress ipAddress;
  public int port;
  
  public PlayerMP(String username, int x, int y, InetAddress ipAddress, int port) {
    super(username, x, y);
    this.ipAddress = ipAddress;
    this.port = port;
  }

//  @Override
  
}
