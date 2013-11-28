package game;

import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

/**
 * Super class of up and down paddle, i.e. those moving horizontally
 * @author yukejun
 */
public abstract class PaddleHor extends Paddle
{
    int dx;
    
    public PaddleHor()
    {
        super("../images/paddleHor.png");
        paddleName = "../images/paddleHor.png";
    }
    
    public void move() {
        x += dx;
        if (x <= 2) 
          x = 2;
        if (x >= Commons.PADDLE_RIGHT + Commons.NORMAL_PADDLE - 5 * scale)
          x = Commons.PADDLE_RIGHT + (Commons.NORMAL_PADDLE - 5 * scale);
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
    
    public void longer()
    {
        if (paddleName.equals("../images/paddleHor.png"))
        {
            paddleName = "../images/paddleLongHor.png";
            ImageIcon ii = new ImageIcon(this.getClass().getResource(paddleName));
            image = ii.getImage();
            width = image.getWidth(null);
            heigth = image.getHeight(null);
            scale = LONG_PADDLE_SCALE;
        }
        else if (paddleName.equals("../images/paddleShortHor.png"))
        {
            paddleName = "../images/paddleHor.png";
            ImageIcon ii = new ImageIcon(this.getClass().getResource(paddleName));
            image = ii.getImage();
            width = image.getWidth(null);
            heigth = image.getHeight(null);
            scale = NORMAL_PADDLE_SCALE;
        }
    }
    
    public void shorter()
    {
        if (paddleName.equals("../images/paddleHor.png"))
        {
            paddleName = "../images/paddleShortHor.png";
            ImageIcon ii = new ImageIcon(this.getClass().getResource(paddleName));
            image = ii.getImage();
            width = image.getWidth(null);
            heigth = image.getHeight(null);
            scale = SHORT_PADDLE_SCALE;
        }
        else if (paddleName.equals("../images/paddleLongHor.png"))
        {
            paddleName = "../images/paddleHor.png";
            ImageIcon ii = new ImageIcon(this.getClass().getResource(paddleName));
            image = ii.getImage();
            width = image.getWidth(null);
            heigth = image.getHeight(null);
            scale = NORMAL_PADDLE_SCALE;
        }
    }
    
    public void move(Ball ball) {
      if (ball.getX() <= 20)
        setX(0);
      else if (ball.getX() >= Commons.PADDLE_RIGHT + 20){
        setX(Commons.PADDLE_RIGHT);
      }
      else
        setX(ball.getX() - 20);
    }
}
