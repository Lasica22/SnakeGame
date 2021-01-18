package com.baselukasz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

    // Rozmiar okna
    static final int screenWidth = 600;
    static final int screenHeight = 600;
    // Plytki na ekranie
    static final int unitSize = 30;
    static final int gameUnits = (screenWidth * screenHeight) / unitSize;
    // Predkosc gry
    static int delay = 100;
    final int[] x = new int[gameUnits];
    final int[] y = new int[gameUnits];
    // Poczatkowy rozmiar weza
    int bodyParts = 6;
    // Punkty
    int applesEaten = 0;
    // Pozycja jablek
    int appleX;
    int appleY;
    // Kierunek poczatkowy weza
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;
    static boolean gameOn = false;

    // Tablica wynikow
    ArrayList<Integer> scores = new ArrayList<>();

    GamePanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }

    public void startGame(){
        running = true;
        newApple();
        timer = new Timer(delay, this);
        timer.restart();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        if (running) {
            // Malowanie linii na ekranie
            for (int i = 0; i < screenHeight / unitSize; i++) {
                g.drawLine(i * unitSize, 0, i * unitSize, screenHeight);
                g.drawLine(0, i * unitSize, screenWidth, i * unitSize);
            }
            // Malowanie jablek
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, unitSize, unitSize);

            // Malowanie weza
            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(new Color(74,204,255));
                } else {
                    g.setColor(new Color(0, 150, 226));
                }
                g.fillRect(x[i], y[i], unitSize, unitSize);
            }
            // Malowanie wyniku podczas gry
            g.setColor(Color.red);
            g.setFont(new Font("Matura", Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Wynik: " + applesEaten, (screenWidth - metrics.stringWidth("Wynik: " + applesEaten)) / 2,
                    g.getFont().getSize());
        } else {
            gameOver(g);
        }
    }

    // Metoda generujaca nowe jablko na ekranie
    public void newApple() {
        appleX = random.nextInt(screenWidth / unitSize) * unitSize;
        appleY = random.nextInt(screenHeight / unitSize) * unitSize;

        for(int i = 0; i < bodyParts; i++){
            if (appleX == x[i] && appleY == y[i])
                newApple();
        }
    }

    // Metoda umozliwiajaca ruch weza
    public void move() {
        // Pojawienie sie kolejnych czesci weza, na miejsce poprzedzajacej
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        switch (direction) {
            case 'U' -> y[0] = y[0] - unitSize;
            case 'D' -> y[0] = y[0] + unitSize;
            case 'L' -> x[0] = x[0] - unitSize;
            case 'R' -> x[0] = x[0] + unitSize;
        }
    }

    // Metoda sprawdzajaca zdobywanie jablek
    public void checkApple() {
        if ((x[0] == appleX) && (y[0]) == appleY) {
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }

    // Sprawdzanie kolizji, warunki porazki
    public void checkCollisions() {
        // Sprawdza czy glowa przeciela cialo
        for (int i = bodyParts; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;
                break;
            }
        }
        // Sprawdza czy glowa wyszla poza plansze
        if (x[0] < 0) {
            running = false;
        }
        if (x[0] > screenWidth) {
            running = false;
        }
        if (y[0] < 0) {
            running = false;
        }
        if (y[0] > screenHeight) {
            running = false;
        }
        if (!running) {
            timer.stop();
        }
    }

    public void gameOver(Graphics g){
        // Score
        g.setColor(Color.red);
        g.setFont(new Font("Matura", Font.BOLD, 40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Wynik: " + applesEaten, (screenWidth - metrics1.stringWidth("Score: " + applesEaten)) / 2,
                g.getFont().getSize());
        // Game over text
        g.setFont(new Font("Matura", Font.BOLD, 75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Koniec gry", (screenWidth - metrics2.stringWidth("Game Over")) / 2, screenHeight / 2);

        g.setFont(new Font("Matura", Font.BOLD, 25));
        FontMetrics metrics5 = getFontMetrics(g.getFont());
        g.drawString("R - pokaz ranking", (screenWidth - metrics5.stringWidth("R - pokaz ranking")) / 2,
                screenHeight / 2 + 100);

        g.setFont(new Font("Matura", Font.BOLD, 25));
        FontMetrics metrics3 = getFontMetrics(g.getFont());
        g.drawString("ENTER - roczpocznij nowa gre", (screenWidth - metrics3.stringWidth("ENTER - roczpocznij nowa gre")) / 2,
                screenHeight / 2 + 150);

        g.setFont(new Font("Matura", Font.BOLD, 25));
        FontMetrics metrics4 = getFontMetrics(g.getFont());
        g.drawString("ESC - menu", (screenWidth - metrics4.stringWidth("ESC - menu")) / 2, screenHeight / 2 + 200);

        // Dodanie wyniku do tablicy rekordow
        scores.add(applesEaten);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }

    public void restart(){
        x[0] = 0;
        y[0] = 0;
        bodyParts = 6;
        applesEaten = 0;
        running = true;
        direction = 'R';
        repaint();
        startGame();
    }

    public void pause() {
        GamePanel.gameOn = true;
        timer.stop();
    }

    public void resume() {
        GamePanel.gameOn = false;
        timer.start();
    }

        public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U') {
                        direction = 'D';
                    }
                    break;
                case KeyEvent.VK_ENTER:
                    restart();
                    break;
                case KeyEvent.VK_ESCAPE:
                    new MenuFrame();
                    break;
                case KeyEvent.VK_R:
                    new RankingFrame(scores);
                    break;
                case KeyEvent.VK_SPACE:
                    if(GamePanel.gameOn) {
                        resume();
                    } else {
                        pause();
                    }
                    break;
            }
        }
    }
}