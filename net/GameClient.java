package net;

import game.Ball;
import game.Paddle;
//import game.Player;
import game.PlayerMP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

import net.packets.Packet;
import net.packets.Packet.PacketTypes;
import net.packets.Packet00Login;
import net.packets.Packet02Move;

public class GameClient extends Thread{
  
  private InetAddress ipAddress;
  private DatagramSocket socket;
  private Paddle paddle;
  private PlayerMP player;
  private Ball ball;
  private boolean isServer;
  private Timer timer;
//  private Board board;
//  private Game game;
  
  public GameClient(String ipAddress) {
    try {
      this.ipAddress = InetAddress.getByName(ipAddress);
      this.socket = new DatagramSocket();
      timer = new Timer();
      timer.scheduleAtFixedRate(new ScheduleTask(), 10000, 20);
//      this.isServer = isServer;
    } catch (UnknownHostException e) {
      e.printStackTrace();
    } catch (SocketException e) {
      e.printStackTrace();
    }
  }
  
  public void run() {
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
//      if(paddle != null) {
        Packet02Move packet = new Packet02Move(player.getUsername(), player.getX(), ball.getX(), ball.getY());
        sendData(packet.getData());
//      }
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
      if(!isServer) {
        packet = new Packet02Move(data);
        handleMove((Packet02Move)packet, address, port);
      }
    }
  }
  
  private void handleMove(Packet02Move packet, InetAddress address, int port) {
//    System.out.println("Another player moved");
    
    player.setX(packet.getX());
    ball.setX(packet.getBallX());
    ball.setY(packet.getBallY());
//    System.out.println("++++" + ball.getX() + ", " + ball.getY());
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
  }
  
  public void addPlayer(PlayerMP player) {
    System.out.println("new player added in client");
    if(this.player == null) {
      this.player = player;
    }
  }
  
  public void addBall(Ball ball) {
    if(this.ball == null) {
      this.ball = ball;
    }
  }
}
