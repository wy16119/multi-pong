package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import net.GameClient;
import net.packets.Packet00Login;

public class Board extends JPanel implements Commons {

    Image ii;
    Timer timer;
    String message = "Game Over";
<<<<<<< HEAD
    Ball ball;
//    public Paddle paddle;
=======
//    Ball ball;
    Ball[] balls;
//    int ballActive;

>>>>>>> complete
    Brick bricks[];
    PlayerMP myPlayer;
    PaddleInfo[] infos = new PaddleInfo[5];
    private List<PlayerMP> connectedPlayers = new ArrayList<PlayerMP>();
    
    boolean ingame = true;
    int timerId;
    int gameId;

    private GameClient socketClient;
    public Board(PaddleInfo[] infos) {
      this.infos = infos;
      //initialize balls
      balls = new Ball[]{new Ball(0), new Ball(1)};
      
<<<<<<< HEAD
      
      player = new PlayerMP(JOptionPane.showInputDialog(this, "Please enter username"), 200, 360, null, -1);
      Packet00Login loginPacket = new Packet00Login(player.getUsername(), player.getX(), player.getY());
      gameId = JOptionPane.showConfirmDialog(this, "Please enter room id");
      
      socketClient = new GameClient("localhost", gameId);
=======
//      socketClient = new GameClient("localhost", this);
      socketClient = new GameClient("54.200.26.69", this);
>>>>>>> complete
      socketClient.start();
      
      // sun
      String username = JOptionPane.showInputDialog(this, "Please enter username");
      String password = JOptionPane.showInputDialog(this, "Please enter password");
      myPlayer = new PlayerMP(username, password, -1, -1, null, 3334);
      //

      Packet00Login loginPacket = new Packet00Login(myPlayer.getUsername(), myPlayer.getPassword(), myPlayer.getPosition(), myPlayer.getX(), myPlayer.getY(), myPlayer.getScore(), -1);
      loginPacket.writeData(socketClient);

        addKeyListener(new TAdapter());
        setFocusable(true);
        bricks = new Brick[BRICK_TOTAL];
        setDoubleBuffered(true);
        timer = new Timer();
        timer.scheduleAtFixedRate(new ScheduleTask(), 1000, 10);
    }

        public void addNotify() {
            super.addNotify();
            gameInit();
        }

        public void gameInit()
        {
            //initiate bricks
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
//                  else if ((i == 1 && j == 0) || (i == 0 && j == 1) || (i == 4 && j == 0) || (i == 0 && j == 4) 
//                      || (i == 1 && j == 5) || (i == 5 && j == 1) || (i == 4 && j == 5) || (i == 5 && j == 4))
//                    bricks[k] = new BrickSpeedUp(j * 20 + 135, i * 20 + 130);
                  // feature: normal
                  else
                    bricks[k] = new BrickSimple(j * 20 + 135, i * 20 + 130);
                  k++;
                }
            }
        }



    public void paint(Graphics g) {
        super.paint(g);
        
        if (ingame) {
            for (int i = 0; i < BALL_TOTAL; ++i)
            {
                Ball curBall = balls[i];
                if (!curBall.isLost())
                    g.drawImage(curBall.getImage(), curBall.getX(), curBall.getY(),
                            curBall.getWidth(), curBall.getHeight(), this);
            }
//            print my player
            g.drawImage(myPlayer.getImage(), myPlayer.getX(), myPlayer.getY(),
                myPlayer.getWidth(), myPlayer.getHeight(), this);
//            List<PlayerMP> allPlayers = new ArrayList<PlayerMP>(); 
//            allPlayers = socketClient.getConnectedPlayers();
//            System.out.println(allPlayers.size());
//            PRINT other player
            for(PlayerMP player : connectedPlayers) {
              if(!player.getUsername().equalsIgnoreCase(myPlayer.getUsername())) {
                g.drawImage(player.getImage(), player.getX(), player.getY(),
                    player.getWidth(), player.getHeight(), this);
              }
            }
            for (int i = 0; i < BRICK_TOTAL; i++) {
                if (!bricks[i].isDestroyed())
                    g.drawImage(bricks[i].getImage(), bricks[i].getX(),
                                bricks[i].getY(), bricks[i].getWidth(),
                                bricks[i].getHeight(), this);
            }
        } else {

            Font font = new Font("Verdana", Font.BOLD, 18);
            FontMetrics metr = this.getFontMetrics(font);

            g.setColor(Color.BLACK);
            g.setFont(font);
            g.drawString(message,
                         (Commons.WIDTH - metr.stringWidth(message)) / 2,
                         Commons.WIDTH / 2);
        }


        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }

    private class TAdapter extends KeyAdapter {

        public void keyReleased(KeyEvent e) {
          myPlayer.keyReleased(e);
        }

        public void keyPressed(KeyEvent e) {
          myPlayer.keyPressed(e);
        }
    }


    class ScheduleTask extends TimerTask {

        public void run() {
            
            myPlayer.move();
            repaint();

        }
    }

    public void stopGame() {
        ingame = false;
        timer.cancel();
    }

<<<<<<< HEAD
  //  getter
    public Ball getBall() {
      return this.ball;
    }
  
    public Brick[] getBricks() {
      return this.bricks;
=======
    public Brick[] getBricks() {
      return bricks;
    }

    public Ball getBalls(int i) {
      return balls[i];
    }

    public Player getPlayer() {
      return myPlayer;
    }

    public void setPlayer(PlayerMP player) {
      this.myPlayer = player;
    }

    public void addConnectedPlayers(PlayerMP player) {
      this.connectedPlayers.add(player);
    }
    
    public List<PlayerMP> getConnectedPlayers() {
      return this.connectedPlayers;
    }
    
    public PaddleInfo[] getInfos() {
      return this.infos;
>>>>>>> complete
    }
}