package net;

import game.PlayerMP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import net.packets.Packet;
import net.packets.Packet00Login;
import net.packets.Packet.PacketTypes;

public class GameClient extends Thread{
  
  private InetAddress ipAddress;
  private DatagramSocket socket;
//  private Game game;
  
  public GameClient(String ipAddress) {
    try {
      this.ipAddress = InetAddress.getByName(ipAddress);
      this.socket = new DatagramSocket();
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
      System.out.println("Client > " + new String(packet.getData()));
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
      System.out.println(address.getHostAddress() + ": " + port
          + " " + ((Packet00Login)packet).getUsername() + " has joined...");
      
      PlayerMP player = new PlayerMP(((Packet00Login)packet).getUsername(), address, port);
      
      
      break;
    case DISCONNECTED:
      break;
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
  
}
