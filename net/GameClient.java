package net;

import game.Ball;
import game.Board;
import game.Brick;
import game.Commons;
import game.PlayerMP;
import game.Sprite;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import net.packets.Packet;
import net.packets.Packet.PacketTypes;
import net.packets.Packet00Login;
import net.packets.Packet02Move;
import net.packets.Packet03Brick;

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
      timer.scheduleAtFixedRate(new ScheduleTask(), 5000, 20);
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
        Packet02Move packet = new Packet02Move(game.getPlayer().getUsername(), game.getPlayer().getX(), game.getBall().getX(), game.getBall().getY());
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
    }
  }
  
  private void handleBrick(Packet03Brick packet, InetAddress address, int port) {
    boolean[] brickBool = packet.getBricks();
    for(int i = 0; i < NUM_BRICKS; i++) {
      this.game.getBricks()[i].setDestroyed(brickBool[i]);
    }
  }

  private void handleMove(Packet02Move packet, InetAddress address, int port) {
    if(!packet.getUsername().equalsIgnoreCase(game.getPlayer().getUsername())) {
      for(PlayerMP movedPlayer : game.getConnectedPlayers()) {
        if(movedPlayer.getUsername().equalsIgnoreCase(packet.getUsername())) {
          movedPlayer.setX(packet.getX());
        }
      }      
    }
      
    this.game.getBall().setX(packet.getBallX());
    this.game.getBall().setY(packet.getBallY());
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
    System.out.println(address.getHostAddress() + ": " + port
        + " " + packet.getUsername() + " has joined...");
    PlayerMP player = new PlayerMP(packet.getUsername(), packet.getX(), packet.getY(), address, port);
    this.addConnection(player, packet);
    System.out.println(game.getConnectedPlayers().size());
  }
  
  public void addConnection(PlayerMP player, Packet00Login packet) {
    this.game.addConnectedPlayers(player);
  }
  
  public void addPlayer(PlayerMP player) {
    System.out.println("new player added in client");
      this.game.setPlayer(player);
//      this.connectedPlayers.add(myPlayer);
      game.addConnectedPlayers(player);
  }
  
//  public void addBall(Ball ball) {
//    this.ball = ball;
//  }

//  public List<PlayerMP> getConnectedPlayers(){
//    return this.connectedPlayers;
//  }

//  public void addBricks(Brick[] bricks) {
//    this.bricks = bricks;
//  }
}
