package agh.cs.project.graphics;

import agh.cs.project.maps.GrassField;
import agh.cs.project.parsers.JsonParser;
import agh.cs.project.simulation.GameSimulation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class ButtonPanel extends JPanel implements ActionListener {
    public static final int HEIGHT = 400;
    public static final int WIDTH = 400;
    private final JButton jButtonContinue,jButtonWriteJson,jButtonStart,jButtonStop;

    private final JTextField jTextFieldWidth,jTextFieldHeight,jTextFieldGrassNr,jTextFieldAnimalsNr,jTextFieldSpeed;
    private final JTextArea jTextFieldStats;
    private int fieldHeight=25,fieldWidth=25;
    private int grassNumber=600,animalsNumber=3;
    private JPanel mapGraphics;
    private GameSimulation gameSimulation;
    private Timer timer;
    private int day = 0;
    private JsonParser parser = new JsonParser();

    public ButtonPanel() {
        jButtonContinue = new JButton("continue");
        jButtonWriteJson = new JButton("to JSON");
        jButtonStart = new JButton("start");
        jButtonStop = new JButton("stop");

        jTextFieldWidth = new JTextField("25");
        jTextFieldHeight = new JTextField("25");
        jTextFieldGrassNr = new JTextField("600");
        jTextFieldAnimalsNr = new JTextField("3");
        jTextFieldSpeed = new JTextField("10");
        jTextFieldStats = new JTextArea("Start the simulation");


        createTextFields();
        timer = new Timer(100,this);

        jButtonContinue.addActionListener(this);
        jButtonWriteJson.addActionListener(this);
        jButtonStart.addActionListener(this);
        jButtonStop.addActionListener(this);


        jTextFieldWidth.setBounds(20,30, 50,20);
        jTextFieldHeight.setBounds(80,30, 50,20);
        jTextFieldSpeed.setBounds(140,30, 50,20);
        jTextFieldGrassNr.setBounds(20,80, 100,20);
        jTextFieldAnimalsNr.setBounds(20,130, 100,20);

        jTextFieldStats.setBounds(20,190, 160,120);

        jButtonContinue.setBounds(140,160,100,20);
        jButtonWriteJson.setBounds(140,80,100,20);
        jButtonStart.setBounds(20,160,80,20);
        jButtonStop.setBounds(140,160,100,20);

        jButtonContinue.setVisible(false);
        jButtonStop.setVisible(false);

        setLayout(null);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        add(jTextFieldWidth);
        add(jTextFieldHeight);
        add(jTextFieldGrassNr);
        add(jTextFieldAnimalsNr);
        add(jTextFieldSpeed);
        add(jTextFieldStats);
        add(jButtonStart);
        add(jButtonContinue);
        add(jButtonWriteJson);
        add(jButtonStart);
        add(jButtonStop);
    }

    private void createTextFields(){
        JTextArea jTextArea = new JTextArea("Width");
        jTextArea.setBounds(20,5,50,20);
        jTextArea.setBackground(null);
        add(jTextArea);

        jTextArea = new JTextArea("Height");
        jTextArea.setBounds(80,5,50,20);
        jTextArea.setBackground(null);
        add(jTextArea);

        jTextArea = new JTextArea("Speed");
        jTextArea.setBounds(140,5,50,20);
        jTextArea.setBackground(null);
        add(jTextArea);

        jTextArea = new JTextArea("Grasses");
        jTextArea.setBounds(20,55,100,20);
        jTextArea.setBackground(null);
        add(jTextArea);

        jTextArea = new JTextArea("Animals");
        jTextArea.setBounds(20,105,100,20);
        jTextArea.setBackground(null);
        add(jTextArea);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if(source == jButtonContinue){
            jButtonContinue.setVisible(false);
            jButtonStop.setVisible(true);
            timer.start();
        }
        else if(source == jButtonWriteJson)
            parser.writeJson();
        else if(source == jButtonStart) {
            this.day = 0;
            jButtonStop.setVisible(true);
            this.fieldHeight = Integer.parseInt(jTextFieldHeight.getText());
            this.fieldWidth = Integer.parseInt(jTextFieldWidth.getText());
            this.grassNumber = Integer.parseInt(jTextFieldGrassNr.getText());
            this.animalsNumber = Integer.parseInt(jTextFieldAnimalsNr.getText());

            timer = new Timer(1000/Integer.parseInt(jTextFieldSpeed.getText()),this);
            startSimulation();
            timer.start();
        }
        else if(source == jButtonStop){
            jButtonContinue.setVisible(true);
            jButtonStop.setVisible(false);
            timer.stop();
        }
        else {
            HashMap<String, Double> stats = new HashMap<>();
            stats.put("Day", (double) this.day);
            stats.put("Animals", (double) gameSimulation.getGrassField().getAnimals().size());
            stats.put("Grasses", (double) gameSimulation.getGrassField().getGrassFields().size());
            stats.put("Gene", (double) gameSimulation.getGrassField().getDominatingGenome());
            stats.put("LifeLength", gameSimulation.getGrassField().getMeanLifeTime());
            stats.put("Children", gameSimulation.getGrassField().getMeanNumberOfChildren());
            stats.put("Energy", gameSimulation.getGrassField().getMeanEnergy());

            parser.setStats(stats);
            parser.addDayToStats();
            jTextFieldStats.setText(parser.toString());
            renderMap();
        }
    }

    private void startSimulation() {
        gameSimulation = new GameSimulation(this.fieldHeight, this.fieldWidth, this.grassNumber, this.animalsNumber);
        gameSimulation.simulateOneDay();day++;
        drawMap(gameSimulation.getGrassField());
    }

    private void renderMap(){
        if(gameSimulation.getGrassField().getAnimals().size() == 0){
            jButtonStop.setVisible(false);
            timer.stop();
            day = 0;
        }else{
            gameSimulation.simulateOneDay();day++;
            mapGraphics = new MapGraphics(fieldWidth, fieldHeight, gameSimulation.getGrassField());
            drawMap(gameSimulation.getGrassField());
        }
    }

    private void drawMap(GrassField grassField) {

        if (mapGraphics != null){
            remove(mapGraphics);
        }
        mapGraphics = new MapGraphics(fieldWidth, fieldHeight, grassField);
        mapGraphics.setBounds(250, 20, 500, 500);

        mapGraphics.setVisible(true);
        add(mapGraphics);
        mapGraphics.repaint();
        mapGraphics.revalidate();
    }
}

