package game;

import javax.swing.ImageIcon;


public class Ball extends Sprite implements Commons {

   private int xdir;
   private int ydir;
   static private double speed;
   private PlayerMP lastHit;
   private boolean isItLost;

   protected String ballImageName = "ball.png";

   public Ball(int i) {

     if (i == 0)
     {
       xdir = 1;
       ydir = -1;
     }
     else
     {
       xdir = -1;
       ydir = 1;
     }
     speed = BALL_SPEED_NORMAL;
     lastHit = null;
     isItLost = false;
     
     ImageIcon ii = new ImageIcon(this.getClass().getResource(ballImageName));
     image = ii.getImage();

     width = image.getWidth(null);
     heigth = image.getHeight(null);

     resetState(i);
    }

   public void move()
   {
//     if(x<=10)
//       xdir = 1;
//     else if(x>=BALL_RIGHT- 10)
//       xdir = -1;
//     if(y<=10)
//       ydir=1;
//     else if(y>=BALL_DOWN- 10)
//       ydir=-1;
     if (xdir == 0)
       y += speed * ydir * 1.3;
     else
       y += speed * ydir;
     if (ydir == 0)
       x += speed * xdir * 1.3;
     else
       x += speed * xdir;
   }

   public void setSpeed(double inSpeed)
   {
     speed = inSpeed;
   }
   
   public double getSpeed()
   {
       return speed;
   }
   
   public void resetState(int i) 
   {
       //start from bottom
       if (i == 0)
       {
           x = 50;//230;
           y = 355;
       }
       //start from top
       else
       {
           x = 210;
           y = 10;
       }
   }

   public void setXDir(int x)
   {
     xdir = x;
   }

   public void setYDir(int y)
   {
     ydir = y;
   }

   public int getYDir()
   {
     return ydir;
   }
   
   public void hit(PlayerMP paddleHit)
   {
       lastHit = paddleHit;
   }
   
   public PlayerMP getLastHit()
   {
       return lastHit;
   }
   
   public boolean isLost()
   {
       return isItLost;
   }
   
   public void setLost(boolean isItLost)
   {
     this.isItLost = isItLost;
   }
   
   public void lose()
   {
       isItLost = true;
   }

}