package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 600; // sets width of screen
    static final int SCREEN_HEIGHT = 600; // sets height of screen
    static final int UNIT_SIZE = 25; // how big the objects will be
    static final int GAME_UNITS = (SCREEN_WIDTH *SCREEN_HEIGHT)/(UNIT_SIZE*UNIT_SIZE); // How many units on the screen
    static final int DELAY = 70;  // the higher the number, the slower the game

    final int[] x = new int[GAME_UNITS]; // hold x co-ordinates of the body of the snake
    final int[] y = new int[GAME_UNITS]; // hold y co-ordinates of the body of the snake

    int bodyParts = 6; // will begin with 6 body parts
    int applesEaten = 0; // Score starts at 0
    int appleX; // will be random location on x-axis
    int appleY; // will be random location on y-axis

    char direction = 'R'; // snake will begin going right
    boolean running = false; // checks if the game is running

    Timer timer; // creates timer
    Random random; // creates random no.


    GamePanel(){
        random = new Random(); // creates instance of random number
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT)); // sets preferred size
        this.setBackground(Color.black); // sets background color
        this.setFocusable(true); // gives the ability to gain focus
        this.addKeyListener(new MyKeyAdapter()); // Receives keyboard events
        startGame(); // calls start game func
    }
    public void startGame(){
        newApple();
        running = true; // Game runs
        timer = new Timer(DELAY, this); // will dictate how fast the game is running
        timer.start(); // starts timer
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);

    }
    public void draw(Graphics g){
        if (running){
//        for(int i =0; i < SCREEN_HEIGHT / UNIT_SIZE; i++){ // creates game grid
//          g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT); // lines along x-axis
//            g.drawLine(0 ,i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE); // lines along y-axis
//        }
        g.setColor(Color.green); // sets color of apple
        g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE); // parameters are (x, y, width, height)

            for (int i = 0; i < bodyParts; i++) { // sets the color for the head and body colors of the snake
                if(i == 0) { // then we are dealing with the head of the snake
                    g.setColor(Color.blue); // sets color of snake to green
                    g.fillOval(x[i], y[i], UNIT_SIZE,UNIT_SIZE); // fills the rectangle

                } else { // dealing with the body of the snake
                    g.setColor(new Color(173, 216, 230)); // sets a specific color
                    g.fillOval(x[i], y[i], UNIT_SIZE,UNIT_SIZE); // fills the rectangle
                }
            }
                g.setColor(Color.red); // Sets colour
                g.setFont(new Font("Ink Free", Font.BOLD, 25));
                g.drawString("Score: " + applesEaten, SCREEN_WIDTH - 130, SCREEN_HEIGHT - 30 ); // Adds score to screen
             } else {

            gameOver(g);
        }
    }

    public void newApple(){ // generate the coordinates of a new apple
        appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;// makes sure its one of the squares along x-axis
        appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;// makes sure its one of the squares along y-axis
    }
    public void move(){
        for (int i = bodyParts; i > 0; i--) { // iterates over all the body parts of the snake
            x[i] = x[i-1];// shifting all the coordinates of the x-axis of the array by one space
            y[i] = y[i-1]; // shifting all the coordinates of the y-axis of the array by one space
        }

        switch (direction) { // enhanced switch statement for switching directions
            case 'U' -> y[0] = y[0] - UNIT_SIZE;
            case 'D' -> y[0] = y[0] + UNIT_SIZE;
            case 'L' -> x[0] = x[0] - UNIT_SIZE;
            case 'R' -> x[0] = x[0] + UNIT_SIZE;
        }


    }
    public void checkApple(){
        if(x[0] == appleX && y[0] == appleY ){ // check if head is touching the apple on both y and x-axis
            bodyParts ++ ; // adds an extra body part
            applesEaten ++; // adds 1 point to apples eaten
            newApple(); // calls new apple to be displayed on the screen
        }
    }
    public void checkCollisions() {
        for (int i = bodyParts; i > 0; i--) { // iterates over the body parts
            if (x[0] == x[i] & y[0] == y[i]) { // means the head has collided with the body
                running = false; // ends game
            }
        }
        if (x[0] < 0) {// checks if head touches left border
            running = false; // ends game
        }
        if (x[0] > SCREEN_WIDTH) {// checks if head touches right border
            running = false; // ends game
        }
        if (y[0] < 0) {// checks if head touches top border
            running = false; // ends game
        }
        if (y[0] > SCREEN_WIDTH) {// checks if head touches bottom border
            running = false; // ends game
        }
        if(!running){
            timer.stop(); // ends timer
        }

    }
    public void gameOver(Graphics g){
        g.setColor(Color.red); // Sets colour
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics = getFontMetrics(g.getFont()); // sets font in the middles of the screen
        g.drawString("GAME OVER", (SCREEN_WIDTH - metrics.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2); // Game Over text
        g.setColor(Color.red); // Sets colour
        g.setFont(new Font("Ink Free", Font.BOLD, 25));
        g.drawString("Score: " + applesEaten, SCREEN_HEIGHT - 100, SCREEN_WIDTH - 10 ); // Adds score to screen
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(running){ //if the game is running
           move(); // calls move function
            checkApple(); // calls checkApple func
            checkCollisions(); // calls checkCollisions func
        }
        repaint();

    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e){
        switch(e.getKeyCode()){ //checks what arrow key is pressed
            case KeyEvent.VK_LEFT :
                if (direction != 'R'){
                    direction = 'L';
                }
                break;
            case KeyEvent.VK_RIGHT :
                if (direction != 'L'){
                    direction = 'R';
                }
                break;
            case KeyEvent.VK_UP:
                if (direction != 'D'){
                    direction = 'U';
                }
                break;
            case KeyEvent.VK_DOWN:
                if (direction != 'U'){
                    direction = 'D';
                }
                break;
        }
        }
    }
}
