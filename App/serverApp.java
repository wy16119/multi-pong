package App;

import game.Breakout;
import net.GameServer;

public class serverApp {
  
  public static void main(String args[]) {
    GameServer socketServer;
    socketServer = new GameServer();
    socketServer.start();
  }
}
