package game;

/**
 * Paddle at the top
 * @author yukejun
 */
public class PaddleUp extends PaddleHor
{
    public void resetState() {
        x = 200;
        y = 0;
    }
}
