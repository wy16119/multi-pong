package net;

import game.Board;
import game.Commons;
import game.PaddleInfo;
import game.PlayerMP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;

import net.packets.Packet;
import net.packets.Packet.PacketTypes;
import net.packets.Packet00Login;
import net.packets.Packet02Move;
import net.packets.Packet03Brick;
import net.packets.Packet04Ball;

public class GameClient extends Thread implements Commons {
  
  private InetAddress ipAddress;
  private DatagramSocket socket;
//  private PlayerMP myPlayer;
  private Board game;
//  private Ball ball;
//  private Brick bricks[];
  private Timer timer;
//  private List<PlayerMP> connectedPlayers = new ArrayList<PlayerMP>();
  
  public GameClient(String ipAddress, Board game) {
    try {
      this.ipAddress = InetAddress.getByName(ipAddress);
      this.socket = new DatagramSocket();
      this.game = game;
      timer = new Timer();
      timer.scheduleAtFixedRate(new ScheduleTask(), 6000, 5);
    } catch (UnknownHostException e) {
      e.printStackTrace();
    } catch (SocketException e) {
      e.printStackTrace();
    }
  }
  
  public void run() {
    System.out.println("client started...");
    while(true) {
      byte[] data = new byte[1024];
      DatagramPacket packet = new DatagramPacket(data, data.length);
      try {
        socket.receive(packet);
      } catch (IOException e) {
        e.printStackTrace();
      }
      this.parsePacket(packet.getData(), packet.getAddress(), packet.getPort());
//      System.out.println("Client received: " + new String(packet.getData()));
    }
  }

  class ScheduleTask extends TimerTask {

    public void run() {
      // sun
      if (game.getPlayer() == null)
        return;
      //
      
      Packet02Move packet = new Packet02Move(game.getPlayer().getUsername(), 
          game.getPlayer().getX(), game.getPlayer().getY(), 
          game.getBalls(0).getX(), game.getBalls(0).getY(), game.getPlayer().getScale());
      sendData(packet.getData());
    }
  }
  
  //take all packets we get, find what packet it is and how to deal with it
  private void parsePacket(byte[] data, InetAddress address, int port) {
    String message = new String(data).trim(); 
    PacketTypes type = Packet.lookupPacket(message.substring(0, 2));
    Packet packet = null;
    
    switch(type) {
    default:
    case INVALID:
      break;
    case LOGIN:
      packet = new Packet00Login(data);
      handleLogin((Packet00Login)packet, address, port);
      break;
    case DISCONNECTED:
      break;
    case MOVE:
      packet = new Packet02Move(data);
      handleMove((Packet02Move)packet, address, port);
      break;
    case BRICK:
      packet = new Packet03Brick(data);
      handleBrick((Packet03Brick)packet, address, port);
      break;
    case BALL:
      packet = new Packet04Ball(data);
      handleBall((Packet04Ball)packet, address, port);
      break;
    }
  }
  
  private void handleBrick(Packet03Brick packet, InetAddress address, int port) {
    boolean[] brickBool = packet.getBricks();
    for(int i = 0; i < NUM_BRICKS; i++) {
      if (! brickBool[i])
        this.game.getBricks()[i].setDestroyed();
      else
        this.game.getBricks()[i].setAppear();
    }
  }

  private void handleBall(Packet04Ball packet, InetAddress address, int port) {
    for(int i = 0; i < BALL_TOTAL; i++) {
      if(i == packet.getId()) {
        this.game.getBalls(i).setX(packet.getBallX());
        this.game.getBalls(i).setY(packet.getBallY());
        this.game.getBalls(i).setLost(packet.getLost());
      }
    }
  }
  
  private void handleMove(Packet02Move packet, InetAddress address, int port) {
//    handle player move
    if(!packet.getUsername().equalsIgnoreCase(game.getPlayer().getUsername())) {
      for(PlayerMP movedPlayer : game.getConnectedPlayers()) {
        if(movedPlayer.getUsername().equalsIgnoreCase(packet.getUsername())) {
          movedPlayer.set(packet.getX(), packet.getY());
        }
      }      
    }
    for(PlayerMP allPlayer : game.getConnectedPlayers()) {
      if(allPlayer.getUsername().equalsIgnoreCase(packet.getUsername()))
        allPlayer.setScaledImage(packet.getScale());
      if(game.getPlayer().getUsername().equalsIgnoreCase(packet.getUsername()))
        game.getPlayer().setScaledImage(packet.getScale());
    }
  }


  public void sendData(byte[] data) {
    DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, 3333);
    try {
      socket.send(packet);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void handleLogin(Packet00Login packet, InetAddress address, int port) {
    // sun
    System.out.println(address.getHostAddress() + ": " + port
        + " " + packet.getUsername() + " login packet received...");  
    //
    
//  my player back
  if(game.getPlayer().getUsername().equalsIgnoreCase(packet.getUsername())) {
    // sun 
    // password incorrect
    if (packet.getValid() == 0)
    {
      JOptionPane.showMessageDialog(game, "Password incorrect!",
          "Wrong Answer", JOptionPane.ERROR_MESSAGE);
      System.exit(0);
    }      
    game.getPlayer().setPosition(packet.getPosition());
    game.getPlayer().setValid(1);
    //
    
      System.out.println("My position:" + packet.getPosition());
      game.getInfos()[packet.getPosition()].setPlayer(game.getPlayer(), "c.png");
    }

  // sun 
PlayerMP player = new PlayerMP(packet.getUsername(), packet.getPassword(), packet.getX(), packet.getY(), address, port);
System.out.println(player.getUsername() + "'s valid is " + player.getValid());;
//

    this.addConnection(player, packet);
    System.out.println("num of players on client: " + game.getConnectedPlayers().size());
    
    List<PlayerMP> allPlayers = game.getConnectedPlayers();
    for(int i = 0; i < allPlayers.size(); i++) {
      if(packet.getUsername().equalsIgnoreCase(allPlayers.get(i).getUsername())) {
        allPlayers.get(i).setPosition(packet.getPosition());
        System.out.println("Setting player:" + i + " position: " + packet.getPosition());
        if(!game.getPlayer().getUsername().equalsIgnoreCase(packet.getUsername()))
          game.getInfos()[packet.getPosition()].setPlayer(allPlayers.get(i), "a.png");
      }
    }

  }
  
  public void addConnection(PlayerMP player, Packet00Login packet) {
    System.out.println("new player added in client");
    
    this.game.addConnectedPlayers(player);
  }
  
//  public void addPlayer(PlayerMP player) {
//      this.game.setPlayer(player);
////      this.connectedPlayers.add(myPlayer);
////      game.addConnectedPlayers(player);
//  }

}
