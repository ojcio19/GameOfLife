package agh.cs.project.simulation;

import agh.cs.project.graphics.ButtonPanel;

import javax.swing.*;

class GameSimulatorFrame extends JFrame {
    public GameSimulatorFrame(){
        super("GameOfLife");

        JPanel buttonPanel = new ButtonPanel();
        add(buttonPanel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800,500);
        setVisible(true);
    }
}
