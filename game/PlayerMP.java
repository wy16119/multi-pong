package game;

import java.net.InetAddress;

public class PlayerMP extends Player {

  public InetAddress ipAddress;
  public int port;
  
  // sun
  public PlayerMP(String username, String password, int x, int y, InetAddress ipAddress, int port) {
    // sun
    super(username, password);
    this.ipAddress = ipAddress;
    this.port = port;
  }

  public void set (int x, int y)
  {
    this.x = x;
    this.y = y;
//    this.scale = scale;
  }




}
