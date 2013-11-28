package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import net.GameClient;
import net.GameServer;
import net.packets.Packet00Login;
import net.packets.Packet02Move;


public class Board extends JPanel implements Commons {

    Image ii;
    Timer timer;
    String message = "Game Over";
    Ball ball;
    public Paddle paddle;
    Brick bricks[];
    PlayerMP player;
    
//    boolean isServer = false;
    boolean ingame = true;
    int timerId;

    private GameClient socketClient;
    public Board() {
      
      
      socketClient = new GameClient("localhost");
      socketClient.start();
      player = new PlayerMP(JOptionPane.showInputDialog(this, "Please enter username"), 200, 360, null, -1);
      Packet00Login loginPacket = new Packet00Login(player.getUsername(), player.getX(), player.getY());
      
      
      
      loginPacket.writeData(socketClient);
      
        addKeyListener(new TAdapter());
        setFocusable(true);

        bricks = new Brick[30];
        setDoubleBuffered(true);
        timer = new Timer();
        timer.scheduleAtFixedRate(new ScheduleTask(), 1000, 10);
    }

        public void addNotify() {
            super.addNotify();
            gameInit();
        }

    public void gameInit() {

        ball = new Ball();
        socketClient.addPlayer(player);
        socketClient.addBall(ball); 
        socketClient.addBricks(bricks);
        int k = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 6; j++) {
                bricks[k] = new Brick(j * 40 + 30, i * 10 + 50);
                k++;
            }
        }
    }


    public void paint(Graphics g) {
        super.paint(g);
        
        if (ingame) {
            g.drawImage(ball.getImage(), ball.getX(), ball.getY(),
                        ball.getWidth(), ball.getHeight(), this);
            List<PlayerMP> allPlayers = new ArrayList<PlayerMP>(); 
            allPlayers = socketClient.getConnectedPlayers();
            for(PlayerMP player : allPlayers) {
              g.drawImage(player.getImage(), player.getX(), player.getY(),
                  player.getWidth(), player.getHeight(), this);
            }

            for (int i = 0; i < 30; i++) {
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
            player.keyReleased(e);
        }

        public void keyPressed(KeyEvent e) {
            player.keyPressed(e);
        }
    }


    class ScheduleTask extends TimerTask {

        public void run() {
            
            player.move();
            repaint();

        }
    }

    public void stopGame() {
        ingame = false;
        timer.cancel();
    }


}