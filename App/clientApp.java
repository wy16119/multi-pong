package App;

import net.GameClient;
import net.GameServer;

public class clientApp {

  public static void main(String args[]) {
    GameClient socketClient;
    socketClient = new GameClient("localhost");
    socketClient.start();
  }
}
