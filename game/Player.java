package game;

import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

public class Player extends Sprite implements Commons {

  private String imageName;
  private String username;
  private String password;
  int scale = NORMAL_PADDLE_SCALE;
  private int score;
  Ball registeredBall;
  public PaddleInfo infoSheet;
  
  private int position; //1 down, 2 up, 3 left, 4 right, 0 invalid
  private int dx, dy;
  private int lifeLeft;
  private int valid;
  
  /**
   * Empty constructor
   */
  // sun
  public Player(String usernameIn, String passwordIn)
  {
      username = usernameIn;
      // sun
      password = passwordIn;
      valid = 0;
      //
      position = 0;
      dx = 0;
      dy = 0;
  }
  
  /**
   * Constructor with specified position
   */
  public Player(String usernameIn, int p)
  {
      username = usernameIn;
      dx = 0;
      dy = 0;
      setPosition(p);
  }
  
  public void setPosition(int p)
  {
      position = p;
      //set image
      if (position == 1 || position == 2)
          imageName = "paddleHor.png";
      else if (position == 3 || position == 4)
          imageName = "paddleVer.png";
      image = new ImageIcon(this.getClass()
              .getResource(imageName)).getImage();
      width = image.getWidth(null);
      heigth = image.getHeight(null);
      resetState();
  }
  
  /**
   * Update movement
   */
  public void move()
  {
      if (position == 1 || position == 2)
      {
          x += dx;
          if (x <= 2) 
            x = 2;
          if (x >= Commons.PADDLE_RIGHT + Commons.NORMAL_PADDLE - 5 * scale)
            x = Commons.PADDLE_RIGHT + (Commons.NORMAL_PADDLE - 5 * scale);
      }
      else if (position == 3 || position == 4)
      {
          y += dy;
          if (y <= 2) 
            y = 2;
          if (y >= Commons.PADDLE_DOWN + Commons.NORMAL_PADDLE - 5 * scale - 27)
            y = Commons.PADDLE_DOWN + Commons.NORMAL_PADDLE - 5 * scale - 27;
      }
  }

  public int getScale()
  {
    return scale;
  }
  
  public int getScore()
  {
      return score;
  }
  
  public String getUsername()
  {
      return username;
  }
  
  public int getPosition()
  {
      return position;
  }
  
  public void increaseScore(int delta)
  {
      infoSheet.score += delta;
  }
  
  public void register(Ball toRegister)
  {
      registeredBall = toRegister;
  }

  /**
   * Action listener for key pressed
   */
  public void keyPressed(KeyEvent e) {

      int key = e.getKeyCode();

      if (position == 1 || position == 2)
      {
          if (key == KeyEvent.VK_LEFT) {
              dx = -2;
          }
          if (key == KeyEvent.VK_RIGHT) {
              dx = 2;
          }
      }
      else if (position == 3 || position == 4)
      {
          if (key == KeyEvent.VK_UP) {
              dy = -2;
          }
          if (key == KeyEvent.VK_DOWN) {
              dy = 2;
          }
      }
  }

  /**
   * Action listener for key released
   */
  public void keyReleased(KeyEvent e) {
      int key = e.getKeyCode();

      if (position == 1 || position == 2)
      {
          if (key == KeyEvent.VK_LEFT) {
              dx = 0;
          }
          if (key == KeyEvent.VK_RIGHT) {
              dx = 0;
          }
      }
      else if (position == 3 || position == 4)
      {
          if (key == KeyEvent.VK_UP) {
              dy = 0;
          }
          if (key == KeyEvent.VK_DOWN) {
              dy = 0;
          }
      }
  }
  
  /**
   * Return to initial position of the paddle
   *   according to its position on the board
   */
  public void resetState()
  {
      //bottom
      if (position == 1)
      {
          x = 200;
          y = 360;
      }
      //top
      else if (position == 2)
      {
          x = 200;
          y = 0;
      }
      //left
      else if (position == 3)
      {
          x = 0;
          y = 150;
      }
      //right
      else if (position == 4)
      {
          x = 390;
          y = 150;
      }
  }
  
  public void setScaledImage(int scaleIn)
  {
    scale = scaleIn;
    if (position == 1 || position == 2)
    {
      if (scale == SHORT_PADDLE_SCALE)
      {
        image = new ImageIcon(this.getClass().getResource("paddleShortHor.png"))
          .getImage();
        width = image.getWidth(null);
        heigth = image.getHeight(null);
      }
      else if (scale == NORMAL_PADDLE_SCALE)
      {
        image = new ImageIcon(this.getClass().getResource("paddleHor.png"))
          .getImage();
        width = image.getWidth(null);
        heigth = image.getHeight(null);
      }
      else if (scale == LONG_PADDLE_SCALE)
      {
        image = new ImageIcon(this.getClass().getResource("paddleLongHor.png"))
          .getImage();
        width = image.getWidth(null);
        heigth = image.getHeight(null);
      }
    }
    else if (position == 3 || position == 4)
    {
      if (scale == SHORT_PADDLE_SCALE)
      {
        image = new ImageIcon(this.getClass().getResource("paddleShortVer.png"))
          .getImage();
        width = image.getWidth(null);
        heigth = image.getHeight(null);
      }
      else if (scale == NORMAL_PADDLE_SCALE)
      {
        image = new ImageIcon(this.getClass().getResource("paddleVer.png"))
          .getImage();
        width = image.getWidth(null);
        heigth = image.getHeight(null);
      }
      else if (scale == LONG_PADDLE_SCALE)
      {
        image = new ImageIcon(this.getClass().getResource("paddleLongVer.png"))
          .getImage();
        width = image.getWidth(null);
        heigth = image.getHeight(null);
      }
    }
  }
  
  /**
   * Special effects: make the paddle longer
   */
  public void longer()
  {
      if (position == 1 || position == 2)
      {
          if (imageName.equals("paddleHor.png"))
          {
              imageName = "paddleLongHor.png";
              ImageIcon ii = new ImageIcon(this.getClass().getResource(imageName));
              image = ii.getImage();
              width = image.getWidth(null);
              heigth = image.getHeight(null);
              scale = LONG_PADDLE_SCALE;
          }
          else if (imageName.equals("paddleShortHor.png"))
          {
              imageName = "paddleHor.png";
              ImageIcon ii = new ImageIcon(this.getClass().getResource(imageName));
              image = ii.getImage();
              width = image.getWidth(null);
              heigth = image.getHeight(null);
              scale = NORMAL_PADDLE_SCALE;
          }
      }
      else if (position == 3 || position == 4)
      {
          if (imageName.equals("paddleVer.png"))
          {
              imageName = "paddleLongVer.png";
              ImageIcon ii = new ImageIcon(this.getClass().getResource(imageName));
              image = ii.getImage();
              width = image.getWidth(null);
              heigth = image.getHeight(null);
              scale = LONG_PADDLE_SCALE;
          }
          else if (imageName.equals("paddleShortVer.png"))
          {
              imageName = "paddleVer.png";
              ImageIcon ii = new ImageIcon(this.getClass().getResource(imageName));
              image = ii.getImage();
              width = image.getWidth(null);
              heigth = image.getHeight(null);
              scale = NORMAL_PADDLE_SCALE;
          }
      }
      System.out.println("In longer: " + scale);
  }
//  
  /**
   * Special effects: make the paddle shorter
   */
  public void shorter()
  {
      if (position == 1 || position == 2)
      {
          if (imageName.equals("paddleHor.png"))
          {
              imageName = "paddleShortHor.png";
              ImageIcon ii = new ImageIcon(this.getClass().getResource(imageName));
              image = ii.getImage();
              width = image.getWidth(null);
              heigth = image.getHeight(null);
              scale = SHORT_PADDLE_SCALE;
          }
          else if (imageName.equals("paddleLongHor.png"))
          {
              imageName = "paddleHor.png";
              ImageIcon ii = new ImageIcon(this.getClass().getResource(imageName));
              image = ii.getImage();
              width = image.getWidth(null);
              heigth = image.getHeight(null);
              scale = NORMAL_PADDLE_SCALE;
          }
      }
      else if (position == 3 || position == 4)
      {
          if (imageName.equals("paddleVer.png"))
          {
              imageName = "paddleShortVer.png";
              ImageIcon ii = new ImageIcon(this.getClass().getResource(imageName));
              image = ii.getImage();
              width = image.getWidth(null);
              heigth = image.getHeight(null);
              scale = SHORT_PADDLE_SCALE;
          }
          else if (imageName.equals("paddleLongVer.png"))
          {
              imageName = "paddleVer.png";
              ImageIcon ii = new ImageIcon(this.getClass().getResource(imageName));
              image = ii.getImage();
              width = image.getWidth(null);
              heigth = image.getHeight(null);
              scale = NORMAL_PADDLE_SCALE;
          }
      }
      System.out.println("In shorter: " + scale);
  }
  
  public int getLifeLeft() {
    return lifeLeft;
  }

  public String getPassword() {
    return password;
  }
  
  // sun
  public void setValid(int validIn) {
    valid = validIn;
  }
  //
  
  public int getValid() {
    return valid;
  }
  
}
