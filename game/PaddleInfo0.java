package game;

import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PaddleInfo0 extends JPanel {

    JLabel nameLabel;
    int score;
    JLabel scoreLabel;
    JLabel wIcon;
    Player paddle;

    /**
     * empty constructor
     */
    public PaddleInfo0(int position)
    {
        nameLabel = new JLabel("       ");
        scoreLabel = new JLabel("  ");
        wIcon = new JLabel();
        paddle = null;
        organize(position);
    }
    
    /**
     * Constructor and register with a certain player
     */
    public PaddleInfo0(Player paddleIn, String imgadd) {
        paddle = paddleIn;
        //paddle.infoSheet = this;
        nameLabel = new JLabel(paddle.getUsername());
        score = 0;
        scoreLabel = new JLabel(String.valueOf(score));
        wIcon = new JLabel(new ImageIcon(this.getClass().getResource(imgadd)));
        organize(paddle.getPosition());
    }
    
    /**
     * Change the player this info sheet is registered to
     */
    public void setPlayer(Player paddleIn, String imgadd)
    {
        paddle = paddleIn;
//        paddle.infoSheet = this;
        nameLabel.setText(paddle.getUsername());
        score = 0;
        scoreLabel.setText(String.valueOf(score));
//        wIcon.setIcon(new ImageIcon(this.getClass().getResource(imgadd)));
        organize(paddle.getPosition());
        
        try
        {
          wIcon.setIcon(new ImageIcon(this.getClass().getResource(paddle.getUsername().toString() + ".png")));
        } catch (NullPointerException e){
          wIcon.setIcon(new ImageIcon(this.getClass().getResource("default.png")));
        }
//        switch (paddle.getPosition())
//        {
//            case 1:
//                wIcon.setIcon(new ImageIcon(this.getClass().getResource("a.png")));
//                break;
//            case 2:
//                wIcon.setIcon(new ImageIcon(this.getClass().getResource("b.png")));
//                break;
//            case 3:
//                wIcon.setIcon(new ImageIcon(this.getClass().getResource("c.png")));
//                break;
//            case 4:
//                wIcon.setIcon(new ImageIcon(this.getClass().getResource("d.png")));
//                break;
//        }
            
    }
    
    public void updateScore()
    {
        scoreLabel.setText(String.valueOf(score));
    }
    
    /**
     * REQUIRES: info sheet MUST register to some valid player
     */
    private void organize(int position)
    {
        //for horizontal info sheet
        if (position == 1 || position == 2)
        {
            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
            this.setLayout(layout);
            layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(138, Short.MAX_VALUE)
                    .addComponent(wIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(scoreLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(nameLabel))
                    .addGap(147, 147, 147))
            );
            layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(nameLabel)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(scoreLabel))
                        .addComponent(wIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap())
            );
        }
        //for vertical info sheet
        else if (position == 3 || position == 4)
        {
            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
            this.setLayout(layout);
            layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(wIcon, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(scoreLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(nameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGap(0, 0, Short.MAX_VALUE)))
                    .addContainerGap())
            );
            layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(116, 116, 116)
                    .addComponent(wIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(nameLabel)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(scoreLabel)
                    .addContainerGap(176, Short.MAX_VALUE))
            );
        }
    }
}
