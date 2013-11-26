package game;

import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

public class Player extends Sprite implements Commons {

  private String username;
  String paddle = "../images/paddle.png";
  int dx;
  
  public Player(String username, int x, int y) {
    this.username = username;
    this.x = x;
    this.y = y;
    
    ImageIcon ii = new ImageIcon(this.getClass().getResource(paddle));
    image = ii.getImage();

    width = image.getWidth(null);
    heigth = image.getHeight(null);
  }
  
  public void move() {
    x += dx;
    if (x <= 2) 
      x = 2;
    if (x >= Commons.PADDLE_RIGHT)
      x = Commons.PADDLE_RIGHT;
  }

  public void keyPressed(KeyEvent e) {
  
      int key = e.getKeyCode();
      
      if (key == KeyEvent.VK_LEFT) {
          dx = -2;
  
      }
  
      if (key == KeyEvent.VK_RIGHT) {
          dx = 2;
      }
  }
  
  public void keyReleased(KeyEvent e) {
      int key = e.getKeyCode();
  
      if (key == KeyEvent.VK_LEFT) {
          dx = 0;
      }
  
      if (key == KeyEvent.VK_RIGHT) {
          dx = 0;
      }
  }
  
  public String getUsername() {
    return this.username;
  }
}
