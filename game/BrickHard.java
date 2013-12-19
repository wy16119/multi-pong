package game;

import javax.swing.ImageIcon;

/**
 * Representative of brick that need 2 strikes to destroy
 */
public class BrickHard extends Brick
{
    private boolean halfDestroyed;
    
    public BrickHard(int x, int y)
    {
        super(x, y, "brickie4.png");
        halfDestroyed = false;
    }
    
    public void setDestroyed()
    {
      // sun
        destroyed = false;
        //
    }
}
