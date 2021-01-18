package com.baselukasz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

public class RankingFrame extends JFrame implements ActionListener {

    static final int screenWidth = 200;
    static final int screenHeight = 450;

    ArrayList<Integer> scores;

    private JButton menu;
    private JButton exit;

    RankingFrame(ArrayList<Integer> scores)  {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setVisible(true);
        this.setTitle("Ranking");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(null);

        getContentPane().setLayout(new BorderLayout());
        JPanel rankingPanel = new JPanel();
        rankingPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        getContentPane().add(rankingPanel, BorderLayout.CENTER);

        this.scores = scores;

        // Sortuje wyniki w tabeli
        Collections.sort(scores, Collections.reverseOrder());

        String ranks = "<html>Ranking:<br/>";
        int j = 1;
        for(int i = 0; i < scores.size() && i < 10; i++){

            ranks = ranks + (j) + ".   Gracz:    " + scores.get(i) + "pkt<br/>";
            j++;
        }
        ranks = ranks + "</html>";

        JLabel wyniki = new JLabel(ranks , JLabel.CENTER);
        wyniki.setFont(new Font("Matura", Font.PLAIN, 21));
        wyniki.setAlignmentX(0);
        wyniki.setAlignmentY(0);
        rankingPanel.add(wyniki);

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        // Przycisk zamykajacy okno
        exit = new JButton("Wróć do gry");
        exit.addActionListener(this);
        exit.setActionCommand("Wróć do gry");
        buttonPane.add(exit);

        // Przycisk potwierdzajacy wprowadzone dane
        menu = new JButton("Menu");
        menu.addActionListener(this);
        menu.setActionCommand("Menu");
        buttonPane.add(menu);
    }

    RankingFrame(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setVisible(true);
        this.setTitle("Ranking");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(null);

        getContentPane().setLayout(new BorderLayout());
        JPanel rankingPanel = new JPanel();
        rankingPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        getContentPane().add(rankingPanel, BorderLayout.CENTER);

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        // Przycisk zamykajacy okno
        exit = new JButton("Wróć do gry");
        exit.addActionListener(this);
        exit.setActionCommand("Wróć do gry");
        buttonPane.add(exit);

        // Przycisk potwierdzajacy wprowadzone dane
        menu = new JButton("Menu");
        menu.addActionListener(this);
        menu.setActionCommand("Menu");
        buttonPane.add(menu);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == this.exit){
            setVisible(false);
            dispose();
        }

        if(e.getSource() == this.menu) {
            setVisible(false);
            dispose();
            new MenuFrame();
        }
    }
}
