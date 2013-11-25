package game;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import net.GameClient;
import net.GameServer;
import net.packets.Packet00Login;



public class Game extends JFrame{

  private GameServer socketServer;
  private GameClient socketClient;
  public Game()
  {  
    if(JOptionPane.showConfirmDialog(this, "Do you want to run the server") == 0) {
      socketServer = new GameServer();
      socketServer.start();
    }
    
    
    
    socketClient = new GameClient("localhost");
    socketClient.start();
    Packet00Login loginPacket = new Packet00Login(JOptionPane.showInputDialog(this, "Please enter username"));
    loginPacket.writeData(socketClient);
    
    add(new Board());
    setTitle("Breakout");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setSize(Commons.WIDTH, Commons.HEIGTH);
    setLocationRelativeTo(null);
    setIgnoreRepaint(true);
    setResizable(false);
    setVisible(true);   
  }
  public static void main(String[] args) {
   
    

    
//    socketClient.sendData("ping".getBytes());

    new Game();
  }
}
