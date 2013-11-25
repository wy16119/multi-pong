package net;

import game.PlayerMP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import net.packets.Packet;
import net.packets.Packet.PacketTypes;
import net.packets.Packet00Login;

public class GameServer extends Thread{
  
  private DatagramSocket socket;
  
  private List<PlayerMP> connectedPlayers = new ArrayList<PlayerMP>();
//  private Game game;
  
  public GameServer() {
    try {
      this.socket = new DatagramSocket(3333);
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
//      String message = new String(packet.getData());
//      if(message.trim().equalsIgnoreCase("ping")) {
//        System.out.println("Client > " + message);
//        sendData("pong".getBytes(), packet.getAddress(), packet.getPort());
//      }
      
    }
  }
  
//  take all packets we get, find what packet it is and how to deal with it
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
      System.out.println(address.getHostAddress() + ": " + port
          + " " + ((Packet00Login)packet).getUsername() + " has connected...");
      
      PlayerMP player = new PlayerMP(((Packet00Login)packet).getUsername(), address, port);
      
      this.addConnection(player, ((Packet00Login)packet));
      
      break;
    case DISCONNECTED:
      break;
    }
  }
  /**
   * player is the player we want to add
   */
  private void addConnection(PlayerMP player, Packet00Login packet) {
    boolean alreadyConnected = false;
    for(PlayerMP p : this.connectedPlayers) {
//      if we have the username in our list
      if(player.getUsername().equalsIgnoreCase(p.getUsername())) {
        if(p.ipAddress == null) 
          p.ipAddress = player.ipAddress;
        if(p.port == -1)
          p.port = player.port;
        alreadyConnected = true;
      }
//    if not connected, sent data to the socket that is 
//      connected to the player who is not connected
      else {
        sendData(packet.getData(), p.ipAddress, p.port);
      }
    }
    if(!alreadyConnected) {
      this.connectedPlayers.add(player);
    }
  }

  public void sendData(byte[] data, InetAddress ipAddress, int port) {
    DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, port);
    try {
      this.socket.send(packet);
    } catch (IOException e) {
      e.printStackTrace();
    }
    System.out.println("Server sending: " + new String(packet.getData()));
  }

  public void sendDataToAllClients(byte[] data) {
    for(PlayerMP p : connectedPlayers) {
      sendData(data, p.ipAddress, p.port);
    }
  }
  
}