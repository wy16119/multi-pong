package game;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import net.GameClient;
import net.GameServer;
import net.packets.Packet00Login;



public class Game extends JFrame{

  public Game()
  {  
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
