package game;

/**
 * Paddle at the bottom
 * @author yukejun
 */
public class PaddleDown extends PaddleHor
{
    public void resetState() {
        x = 200;
        y = 360;
    }
}
