package com.company;

import javax.swing.*;

public class GameFrame extends JFrame {
    GameFrame(){
        GamePanel panel = new GamePanel();
        this.add(panel); // Adds panel to a set collection
        this.setTitle("Snake"); // Sets Title
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Sets option for close button - This will close the application
        this.setResizable(false); // User can not re-size frame
        this.pack(); // Sizes frame so all its contents are at or above their preferred sizes
        this.setVisible(true); // Sets visibility
        this.setLocationRelativeTo(null); // Makes window appear in middle of screen
    }
}
