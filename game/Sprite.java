package game;

import java.awt.Image;
import java.awt.Rectangle;

public class Sprite {

    protected double x;
    protected double y;
    protected double width;
    protected double heigth;
    protected Image image;


    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return (int)x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return (int)y;
    }

    public int getWidth() {
        return (int)width;
    }

    public int getHeight() {
        return (int)heigth;
    }

    public Image getImage()
    {
      return image;
    }

    public Rectangle getRect()
    {
      return new Rectangle((int)x, (int)y, 
          image.getWidth(null), image.getHeight(null));
    }
}