package com.baselukasz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuFrame extends JFrame implements ActionListener {

    static final int screenWidth = 350;
    static final int screenHeight = 80;

    private JButton playSingle;
    private JButton ranking;
    private JButton exit;

    MenuFrame(){

        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setVisible(true);
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(null);

        this.setLayout(new FlowLayout());
        playSingle = new JButton("Pojedynczy gracz");
        this.add(playSingle);
        playSingle.addActionListener(this);

        ranking = new JButton("Ranking");
        this.add(ranking);
        ranking.addActionListener(this);

        exit = new JButton("Wyjscie");
        this.add(exit);
        exit.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Gra SinglePlayer
        if(e.getSource() == this.playSingle){
            setVisible(false);
            dispose();
            new GameFrame();
        }
        // Wyjscie
        if(e.getSource() == this.exit){
            System.exit(0);
        }
        if (e.getSource() == this.ranking){
            setVisible(false);
            dispose();
            new RankingFrame();
        }
    }
}
