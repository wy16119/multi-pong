package game;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.net.UnknownHostException;

public class Breakout extends JFrame {

  boolean isReady = false;
  boolean setAI = false;
  JButton ready;
  JButton inviteAI;

    public Breakout()
    {        setLayout(new BorderLayout());

    //initiate game panel
    //initiate paddle info sheets
    PaddleInfo south = new PaddleInfo(1);
    PaddleInfo north = new PaddleInfo(2);
    PaddleInfo west = new PaddleInfo(3);
    PaddleInfo east = new PaddleInfo(4);
    PaddleInfo[] infos = new PaddleInfo[5];
    infos[1] = south;
    infos[2] = north;
    infos[3] = west;
    infos[4] = east;
    Board gamePanel = new Board(infos);
    gamePanel.setSize(Commons.WIDTH, Commons.HEIGTH);
    java.util.List<PlayerMP> connectedPlayers = gamePanel.getConnectedPlayers();
//    for (Player curPaddle: connectedPlayers)
//    {
//        if (curPaddle.getPosition() == 1)
//            south.setPlayer(curPaddle, "../images/brickie.png");
//        else if (curPaddle.getPosition() == 2)
//            north.setPlayer(curPaddle, "../images/star.png");
//        else if (curPaddle.getPosition() == 3)
//            west.setPlayer(curPaddle, "../images/speedUp.png");
//        else if (curPaddle.getPosition() == 4)
//            east.setPlayer(curPaddle, "../images/brickie3.png");
//    }

    //assemble together game panel and info sheets
    JPanel middlePanel = new JPanel();
    middlePanel.setLayout(new BorderLayout());
    middlePanel.add(gamePanel, BorderLayout.CENTER);
    middlePanel.add(north, BorderLayout.NORTH);
    middlePanel.add(south, BorderLayout.SOUTH);

    JPanel gameBoard = new JPanel();
    gameBoard.setLayout(new BorderLayout());
    gameBoard.add(middlePanel, BorderLayout.CENTER);
    gameBoard.add(east, BorderLayout.EAST);
    gameBoard.add(west, BorderLayout.WEST);
    gameBoard.setSize(Commons.BOARD_WIDTH, Commons.BOARD_HEIGHT);
//    gameBoard.setOpaque(true);
//    gameBoard.setBackground(Color.BLACK);

    //initiate buttons
    ready = new JButton("Ready");
    ready.addActionListener(new ButtonListener());
    inviteAI = new JButton("Invite AI");
    inviteAI.addActionListener(new ButtonListener());
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new GridLayout(1, 2));
    buttonPanel.add(ready);
    buttonPanel.add(inviteAI);

    add(gameBoard, BorderLayout.CENTER);
//    add(buttonPanel, BorderLayout.SOUTH);
    setSize(Commons.TOTAL_WIDTH, Commons.TOTAL_HEIGHT);
    setTitle("Breakout");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setIgnoreRepaint(true);
    setResizable(false);
    setVisible(true);
}

    public class ButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            String cmd = e.getActionCommand();
            if ("Ready".equals(cmd)) {
                isReady = true;
                ready.setEnabled(false);
            } else {
                setAI = true;
                inviteAI.setEnabled(false);
            }
        }
    }

    public static void main(String[] args) {
          new Breakout();
    }
}