package App;

import game.Ball;
import game.Brick;
import game.Commons;
import game.Paddle;
import game.PlayerMP;

import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

import net.GameServer;


public class Game extends JPanel implements Commons {

    Image ii;
    Timer timer;
    String message = "Game Over";
    Ball ball;
    public Paddle paddle;
    Brick bricks[];
    GameServer socketServer;
    
    boolean ingame = true;
    int timerId;
    
    private List<PlayerMP> connectedPlayers;
    public Game(List<PlayerMP> players, GameServer socketServer, Ball ball, Brick[] bricks) {
        connectedPlayers = players; 
        this.socketServer = socketServer;
        this.ball = ball;
        this.bricks = bricks;
        setDoubleBuffered(true);
        gameInit();
        timer = new Timer();
        timer.scheduleAtFixedRate(new ScheduleTask(), 5000, 10);
    }

    public void gameInit() {

        System.out.println("ball and player created");
        int k = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 6; j++) {
                bricks[k] = new Brick(j * 40 + 30, i * 10 + 50);
                k++;
            }
        }
    }


    public void stopGame() {
        ingame = false;
        timer.cancel();
    }

    class ScheduleTask extends TimerTask {

      public void run() {

          ball.move();
          checkCollision();

      }
    }
    
    public void checkCollision() {

        if (ball.getRect().getMaxY() > Commons.BOTTOM) {
            stopGame();
        }

        for (int i = 0, j = 0; i < 30; i++) {
            if (bricks[i].isDestroyed()) {
                j++;
            }
            if (j == 30) {
                message = "Victory";
                stopGame();
            }
        }
        for(PlayerMP player : connectedPlayers) {
          if ((ball.getRect()).intersects(player.getRect())) {
            
            int paddleLPos = (int)player.getRect().getMinX();
            int ballLPos = (int)ball.getRect().getMinX();
            
            int first = paddleLPos + 8;
            int second = paddleLPos + 16;
            int third = paddleLPos + 24;
            int fourth = paddleLPos + 32;
            
            if (ballLPos < first) {
              ball.setXDir(-1);
              ball.setYDir(-1);
            }
            
            if (ballLPos >= first && ballLPos < second) {
              ball.setXDir(-1);
              ball.setYDir(-1 * ball.getYDir());
            }
            
            if (ballLPos >= second && ballLPos < third) {
              ball.setXDir(0);
              ball.setYDir(-1);
            }
            
            if (ballLPos >= third && ballLPos < fourth) {
              ball.setXDir(1);
              ball.setYDir(-1 * ball.getYDir());
            }
            
            if (ballLPos > fourth) {
              ball.setXDir(1);
              ball.setYDir(-1);
            }
            
            
          }
          
        }


        for (int i = 0; i < 30; i++) {
            if ((ball.getRect()).intersects(bricks[i].getRect())) {

                int ballLeft = (int)ball.getRect().getMinX();
                int ballHeight = (int)ball.getRect().getHeight();
                int ballWidth = (int)ball.getRect().getWidth();
                int ballTop = (int)ball.getRect().getMinY();

                Point pointRight =
                    new Point(ballLeft + ballWidth + 1, ballTop);
                Point pointLeft = new Point(ballLeft - 1, ballTop);
                Point pointTop = new Point(ballLeft, ballTop - 1);
                Point pointBottom =
                    new Point(ballLeft, ballTop + ballHeight + 1);

                if (!bricks[i].isDestroyed()) {
                    if (bricks[i].getRect().contains(pointRight)) {
                        ball.setXDir(-1);
                    }

                    else if (bricks[i].getRect().contains(pointLeft)) {
                        ball.setXDir(1);
                    }

                    if (bricks[i].getRect().contains(pointTop)) {
                        ball.setYDir(1);
                    }

                    else if (bricks[i].getRect().contains(pointBottom)) {
                        ball.setYDir(-1);
                    }

                    bricks[i].setDestroyed(true);
                }
            }
        }
    }
}