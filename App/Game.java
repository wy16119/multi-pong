package App;

import game.Ball;
import game.Brick;
import game.BrickHard;
import game.BrickLonger;
import game.BrickShorter;
import game.BrickSimple;
import game.BrickSpeedUp;
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
    Timer brickTimer;
    String message = "Game Over";
<<<<<<< HEAD
    Ball ball;
//    public Paddle paddle;
=======
    Ball[] balls;
    // modify
    int ballActive = BALL_TOTAL;
    public Paddle paddle;
>>>>>>> complete
    Brick bricks[];
    GameServer socketServer;
    
    
    boolean ingame = true;
    int timerId;
<<<<<<< HEAD
    int gameId;
    
    private List<PlayerMP> connectedPlayers;
    public Game(List<PlayerMP> players, GameServer socketServer, int gameId) {
        this.connectedPlayers = players; 
        this.socketServer = socketServer;
        this.gameId = gameId;
//        this.ball = ball;
//        this.bricks = bricks;
=======

    private List<PlayerMP> connectedPlayers;
    
    public Game(List<PlayerMP> players, GameServer socketServer, Ball[] balls, Brick[] bricks) {
        connectedPlayers = players; 
        this.socketServer = socketServer;
        // current one ball
        this.balls = balls;
        this.balls[0] = new Ball(0);
        this.balls[1] = new Ball(1);
        this.bricks = bricks;
>>>>>>> complete
        setDoubleBuffered(true);
        gameInit();
        timer = new Timer();
        timer.scheduleAtFixedRate(new ScheduleTask(), 5000, 20);
        
        brickTimer = new Timer();
        timer.scheduleAtFixedRate(new brickTask(), 5000, 5000);
    }

<<<<<<< HEAD
    public void gameInit() {
        bricks = new Brick[NUM_BRICKS];
        ball = new Ball();
        System.out.println("ball and player created");
=======
    public void gameInit()
    {
        //initiate bricks
>>>>>>> complete
        int k = 0;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
              
              // feature: bricks that can make paddle longer
              if (i == 2 && j == 2)
                bricks[k] = new BrickShorter(110, 180);
              else if (i == 2 && j == 3)
                bricks[k] = new BrickShorter(185, 105);
              else if (i == 3 && j == 2)
                bricks[k] = new BrickShorter(260, 180);
              else if (i == 3 && j == 3)
                  bricks[k] = new BrickShorter(185, 253);
              // feature: bricks that can make paddle shorter
              else if ((i == 1 && j == 1) || (i == 1 && j == 4) || (i == 4 && j == 1) || (i == 4 && j == 4))
                bricks[k] = new BrickLonger(j * 70 + 10, i * 70);
              // feature: bricks that need to be hit twice
              else if ((i == 0 && j == 0) || (i == 0 && j == 5) || (i == 5 && j == 0) || (i == 5 && j == 5))
                bricks[k] = new BrickHard(j * 30 + 110, i * 30 + 100);
              // feature: bricks that may speed up the ball
              else if ((i == 2 && j == 1) || (i == 1 && j == 3) || (i == 3 && j == 4) || (i == 4 && j == 2))
                bricks[k] = new BrickSpeedUp(j * 20 + 135, i * 20 + 130);
//              else if ((i == 1 && j == 0) || (i == 0 && j == 1) || (i == 4 && j == 0) || (i == 0 && j == 4) 
//                  || (i == 1 && j == 5) || (i == 5 && j == 1) || (i == 4 && j == 5) || (i == 5 && j == 4))
//                bricks[k] = new BrickSpeedUp(j * 20 + 135, i * 20 + 130);
              // feature: normal
              else
                bricks[k] = new BrickSimple(j * 20 + 135, i * 20 + 130);
              k++;
            }
        }
    }



    public void stopGame() {
      System.out.println("game stoped");
        ingame = false;
        timer.cancel();
    }

    class ScheduleTask extends TimerTask {

      public void run() {
        for (int i = 0; i < BALL_TOTAL; ++i)
        {
            if (!balls[i].isLost())
                balls[i].move();
        }
        checkCollision();

      }
    }
   
    
    class brickTask extends TimerTask {

      public void run() {
        System.out.println("New random brick");
        int rd =  (int)(Math.random() * ((BRICK_TOTAL)));
        bricks[rd].setAppear();
      }
    }
    
    public void checkCollision() {
      //check whether the ball has gone out of bound
      for (int i = 0; i < BALL_TOTAL; ++i)
      {
          Ball curBall = balls[i];
          if (!curBall.isLost())
          {
              if (curBall.getRect().getMaxY() > Commons.HEIGTH 
                      || curBall.getRect().getMinY() < 1
                      || curBall.getRect().getMinX() < 1
                      || curBall.getRect().getMaxX() > Commons.WIDTH)
              {
                  curBall.lose();
                  --ballActive;
                  //if the first ball dies, register all AI paddles with 
                  //  the other paddle
//                  if (i == 0)
//                  {
//                      for (int j = 0; j < 4; ++j)
//                          paddles[j].register(balls[1]);
//                  }
              }
          }
      }
      if (ballActive == 0)
          stopGame();
//      //check whether all bricks has been destroyed, i.e. win
//      for (int i = 0, j = 0; i < BRICK_TOTAL; i++)
//      {
//          if (bricks[i].isDestroyed())
//          {
//              j++;
//          }
//          if (j == BRICK_TOTAL)
//          {
//              message = "Victory";
//              stopGame();
//          }
//      }
      //if ball hits the paddle
      for (PlayerMP curPaddle: connectedPlayers)
      {
          for (int j = 0; j < BALL_TOTAL; ++j)
          {
              Ball curBall = balls[j];
              if (!curBall.isLost() && 
                      curBall.getRect().intersects(curPaddle.getRect()))
              {
                  curBall.hit(curPaddle);
                  //if the ball hits horizontal paddles
                  if (curPaddle.getPosition() == 1 || curPaddle.getPosition() == 2)
                  {
                      int paddleLPos = (int) curPaddle.getRect().getMinX();
                      int ballLPos = (int) curBall.getRect().getMinX();
                      int paddleScale = curPaddle.getScale();

                      int first = paddleLPos + paddleScale;
                      int second = paddleLPos + 2 * paddleScale;
                      int third = paddleLPos + 3 * paddleScale;
                      int fourth = paddleLPos + 4 * paddleScale;
                      if (curPaddle.getPosition() == 1)
                      {
                          //when hitting the leftmost part, reverse direction
                          if (ballLPos < first)
                          {
                              curBall.setXDir(-1);
                              curBall.setYDir(-1);
                          }
                          //when hitting middle-left part, reflect
                          else if ((ballLPos >= first && ballLPos < second)
                                  || (ballLPos >= third && ballLPos < fourth))
                          {
                              curBall.setYDir(-1);
                          }
                          //when hitting the middle, go upward
                          else if (ballLPos >= second && ballLPos < third)
                          {
                              curBall.setXDir(0);
                              curBall.setYDir(-1);
                          }
                          else if (ballLPos >= fourth)
                          {
                              curBall.setXDir(1);
                              curBall.setYDir(-1);
                          }
                      }
                      else if (curPaddle.getPosition() == 2)
                      {
                          //when hitting the leftmost part, reverse direction
                          if (ballLPos < first)
                          {
                              curBall.setXDir(-1);
                              curBall.setYDir(1);
                          }
                          //when hitting middle-left part, reflect
                          else if ((ballLPos >= first && ballLPos < second)
                                  || (ballLPos >= third && ballLPos < fourth))
                          {
                              curBall.setYDir(1);
                          }
                          //when hitting the middle, go upward
                          else if (ballLPos >= second && ballLPos < third)
                          {
                              curBall.setXDir(0);
                              curBall.setYDir(1);
                          }
                          else if (ballLPos >= fourth)
                          {
                              curBall.setXDir(1);
                              curBall.setYDir(1);
                          }
                      }
                  }
                  //or if the ball hits vertical paddles
                  else if (curPaddle.getPosition() == 3 || curPaddle.getPosition() == 4)
                  {
                      int paddleUPos = (int) curPaddle.getRect().getMinY();
                      int ballUPos = (int) curBall.getRect().getMinY();
                      int paddleScale = curPaddle.getScale();

                      int first = paddleUPos + paddleScale;
                      int second = paddleUPos + 2 * paddleScale;
                      int third = paddleUPos + 3 * paddleScale;
                      int fourth = paddleUPos + 4 * paddleScale;
                      if (curPaddle.getPosition() == 3)
                      {
                          //when hitting the uppermost part, reverse direction
                          if (ballUPos < first)
                          {
                              curBall.setXDir(1);
                              curBall.setYDir(-1);
                          }
                          //when hitting middle-upper part, reflect
                          else if ((ballUPos >= first && ballUPos < second)
                                  || (ballUPos >= third && ballUPos < fourth))
                          {
                              curBall.setXDir(1);
                          }
                          //when hitting the middle, go upward
                          else if (ballUPos >= second && ballUPos < third)
                          {
                              curBall.setXDir(1);
                              curBall.setYDir(0);
                          }
                          else if (ballUPos >= fourth)
                          {
                              curBall.setXDir(1);
                              curBall.setYDir(1);
                          }
                      }
                      else if (curPaddle.getPosition() == 4)
                      {
                          //when hitting the uppermost part, reverse direction
                          if (ballUPos < first)
                          {
                              curBall.setXDir(-1);
                              curBall.setYDir(-1);
                          }
                          //when hitting middle-upper part, reflect
                          else if ((ballUPos >= first && ballUPos < second)
                                  || (ballUPos >= third && ballUPos < fourth))
                          {
                              curBall.setXDir(-1);
                          }
                          //when hitting the middle, go upward
                          else if (ballUPos >= second && ballUPos < third)
                          {
                              curBall.setXDir(-1);
                              curBall.setYDir(0);
                          }
                          else if (ballUPos >= fourth)
                          {
                              curBall.setXDir(-1);
                              curBall.setYDir(1);
                          }
                      }
                  }
              }
          }
      }
      //if ball hits the brick
      for (int i = 0; i < BRICK_TOTAL; i++)
      {
          Brick curBrick = bricks[i];
          for (int j = 0; j < BALL_TOTAL; ++j)
          {
              Ball curBall = balls[j];
              if (!curBall.isLost() && 
                      curBall.getRect().intersects(bricks[i].getRect()))
              {
                  int ballLeft = (int) curBall.getRect().getMinX();
                  int ballHeight = (int) curBall.getRect().getHeight();
                  int ballWidth = (int) curBall.getRect().getWidth();
                  int ballTop = (int) curBall.getRect().getMinY();
                  Point pointRight
                          = new Point(ballLeft + ballWidth, ballTop + ballHeight / 2);
                  Point pointLeft = new Point(ballLeft, ballTop + ballHeight / 2);
                  Point pointTop = new Point(ballLeft + ballWidth / 2, ballTop);
                  Point pointBottom
                          = new Point(ballLeft + ballWidth / 2, ballTop + ballHeight);

                  if (!curBrick.isDestroyed()) {
                      if (curBrick.getRect().contains(pointRight)) {
                          System.out.println("hit brick left");
                          curBall.setXDir(-1);
                      }

                      else if (curBrick.getRect().contains(pointLeft)) {
                          System.out.println("hit brick right");
                          curBall.setXDir(1);
                      }

                      if (curBrick.getRect().contains(pointTop)) {
                          System.out.println("hit brick bottom");
                          curBall.setYDir(1);
                      }

                      else if (curBrick.getRect().contains(pointBottom)) {
                          System.out.println("hit brick top");
                          curBall.setYDir(-1);
                      }

                      curBrick.setDestroyed();
                      // speed up the ball
                      if (curBrick instanceof BrickSpeedUp)
                      {
                        curBall.setSpeed(BALL_SPEED_HIGH);
                      }
                      PlayerMP scoredPaddle = curBall.getLastHit();

                      //special effects
                      // make the paddle longer
                      if (curBrick instanceof BrickLonger)
                      {
                          if (scoredPaddle != null){
                            System.out.println("making shorter");
                            scoredPaddle.longer();
                            
                          }
                          curBall.setSpeed(1);
                      }
                      // make the paddle shorter
                      if (curBrick instanceof BrickShorter)
                      {
                          if (scoredPaddle != null) {
                            System.out.println("making longer");
                            scoredPaddle.shorter();
                            
                          }
                          curBall.setSpeed(1);
                      }
                      // speed up the ball
                      if (curBrick instanceof BrickSpeedUp)
                      {
                        curBall.setSpeed(BALL_SPEED_HIGH);
                      }

                      //score calculation
//                      if (scoredPaddle != null)
//                      {
//                          int scoreDelta = SCORE_UNIT;
//                          //if hit special-effects brick, score double
//                          if (!(curBrick instanceof BrickSimple))
//                              scoreDelta *= 2;
//                          //if double-speed ball, score double
//                          if (curBall.getSpeed() == BALL_SPEED_HIGH)
//                              scoreDelta *= 2;
//                          scoredPaddle.increaseScore(scoreDelta);
//                          scoredPaddle.infoSheet.updateScore();
//                      }
                  }
              }
          }
      }
    }
    
//    getter
    public Ball getBall() {
      return this.ball;
    }

    public Brick[] getBricks() {
      return this.bricks;
    }
}