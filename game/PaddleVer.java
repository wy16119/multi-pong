package game;

import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

/**
 * Super class of left and right paddles, i.e. those moving vertically
 * @author yukejun
 */
public abstract class PaddleVer extends Paddle
{
    int dy;
    
    public PaddleVer()
    {
        super("../images/paddleVer.png");
        paddleName = "../images/paddleVer.png";
    }
    
    public void move() {
        y += dy;
        if (y <= 2) 
          y = 2;
        if (y >= Commons.PADDLE_DOWN + Commons.NORMAL_PADDLE - 5 * scale)
          y = Commons.PADDLE_DOWN + Commons.NORMAL_PADDLE - 5 * scale;
    }

    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_UP) {
            dy = -2;

        }

        if (key == KeyEvent.VK_DOWN) {
            dy = 2;
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_UP) {
            dy = 0;
        }

        if (key == KeyEvent.VK_DOWN) {
            dy = 0;
        }
    }
    
    public void longer()
    {
        if (paddleName.equals("../images/paddleVer.png"))
        {
            paddleName = "../images/paddleLongVer.png";
            ImageIcon ii = new ImageIcon(this.getClass().getResource(paddleName));
            image = ii.getImage();
            width = image.getWidth(null);
            heigth = image.getHeight(null);
            scale = LONG_PADDLE_SCALE;
        }
        else if (paddleName.equals("../images/paddleShortVer.png"))
        {
            paddleName = "../images/paddleVer.png";
            ImageIcon ii = new ImageIcon(this.getClass().getResource(paddleName));
            image = ii.getImage();
            width = image.getWidth(null);
            heigth = image.getHeight(null);
            scale = NORMAL_PADDLE_SCALE;
        }
    }
    
    public void shorter()
    {
        if (paddleName.equals("../images/paddleVer.png"))
        {
            paddleName = "../images/paddleShortVer.png";
            ImageIcon ii = new ImageIcon(this.getClass().getResource(paddleName));
            image = ii.getImage();
            width = image.getWidth(null);
            heigth = image.getHeight(null);
            scale = SHORT_PADDLE_SCALE;
        }
        else if (paddleName.equals("../images/paddleLongVer.png"))
        {
            paddleName = "../images/paddleVer.png";
            ImageIcon ii = new ImageIcon(this.getClass().getResource(paddleName));
            image = ii.getImage();
            width = image.getWidth(null);
            heigth = image.getHeight(null);
            scale = NORMAL_PADDLE_SCALE;
        }
    }
    
    public void move(Ball ball) {
      if (ball.getY() <= 10)
        setY(0);
      else if (ball.getY() >= PADDLE_DOWN){
        setY(PADDLE_DOWN);
      }
      else
        setY(ball.getY() - 5);
    }
}
