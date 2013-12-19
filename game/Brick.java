package game;

import javax.swing.ImageIcon;


public abstract class Brick extends Sprite {

    String brickie;

    boolean destroyed;


    public Brick(int x, int y, String inbrickie) {
      this.x = x;
      this.y = y;
      brickie = inbrickie;
      
      ImageIcon ii = new ImageIcon(this.getClass().getResource(brickie));
      image = ii.getImage();

      width = image.getWidth(null);
      heigth = image.getHeight(null);

      destroyed = false;
    }

    public boolean isDestroyed()
    {
      return destroyed;
    }

    public void setDestroyed()
    {
      // if the brickie need to be destroyed twice, once it was destroyed,
      // it will change to normal bricks
      if (brickie.equals("brickie4.png"))
      {
        brickie = "brickie.png";
        ImageIcon ii = new ImageIcon(this.getClass().getResource(brickie));
        image = ii.getImage();
        this.destroyed = false;
        return;
      }
      else
          this.destroyed = true;
    }
    
    public void setAppear() {
      this.destroyed = false;
    }

}