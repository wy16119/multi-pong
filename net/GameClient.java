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
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import App.Game;
import net.packets.Packet;
import net.packets.Packet.PacketTypes;
import net.packets.Packet00Login;
import net.packets.Packet02Move;

public class GameClient extends Thread{
  
  private InetAddress ipAddress;
  private DatagramSocket socket;
  private Paddle paddle;
  private PlayerMP myPlayer;
  private Ball ball;
  private boolean isServer;
  private Timer timer;
  private List<PlayerMP> connectedPlayers = new ArrayList<PlayerMP>();
//  private Board board;
//  private Game game;
  
  public GameClient(String ipAddress) {
    try {
      this.ipAddress = InetAddress.getByName(ipAddress);
      this.socket = new DatagramSocket();
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
        Packet02Move packet = new Packet02Move(myPlayer.getUsername(), myPlayer.getX(), ball.getX(), ball.getY());
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
      if(!isServer) {
        packet = new Packet02Move(data);
        handleMove((Packet02Move)packet, address, port);
      }
    }
  }
  
  private void handleMove(Packet02Move packet, InetAddress address, int port) {
//    for(PlayerMP movedPlayer : connectedPlayers) {
//      if(!movedPlayer.getUsername().equalsIgnoreCase(packet.getUsername())) {
//        movedPlayer.setX(packet.getX());
//      }
//    }
    if(!packet.getUsername().equalsIgnoreCase(myPlayer.getUsername())) {
      for(PlayerMP movedPlayer : connectedPlayers) {
        if(movedPlayer.getUsername().equalsIgnoreCase(packet.getUsername())) {
          System.out.println("another player moved: " + packet.getX());
          movedPlayer.setX(packet.getX());
        }
      }      
    }
      
    this.ball.setX(packet.getBallX());
    this.ball.setY(packet.getBallY());
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
//    if(!packet.getUsername().equalsIgnoreCase(myPlayer.getUsername())) {
//      PlayerMP player = new PlayerMP(packet.getUsername(), packet.getX(), packet.getY(), address, port);
//      this.addConnection(player, packet);
//    }
    PlayerMP player = new PlayerMP(packet.getUsername(), packet.getX(), packet.getY(), address, port);
    this.addConnection(player, packet);
  }
  
  public void addConnection(PlayerMP player, Packet00Login packet) {
    this.connectedPlayers.add(player);
  }
  
  public void addPlayer(PlayerMP player) {
    System.out.println("new player added in client");
      this.myPlayer = player;
      this.connectedPlayers.add(myPlayer);
  }
  
  public void addBall(Ball ball) {
    if(this.ball == null) {
      this.ball = ball;
    }
  }

  public List<PlayerMP> getConnectedPlayers(){
    return this.connectedPlayers;
  }
}
