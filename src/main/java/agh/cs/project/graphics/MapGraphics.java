package agh.cs.project.graphics;

import agh.cs.project.elements.Vector2d;
import agh.cs.project.maps.GrassField;

import javax.swing.*;
import java.awt.*;

class MapGraphics extends JPanel {
    private final int row;
    private final int column;
    private final GrassField grassField;

    MapGraphics(int row, int column, GrassField grassField){
        this.grassField = grassField;
        this.row = row;
        this.column = column;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int j = 0; j < column; j++) {
            for (int i = 0; i < row; i++) {
                g.setColor(new Color(200,150,0));
                for (Vector2d vector: grassField.getGrassFields().keySet()) {
                    if (i== vector.x && j == row - vector.y - 1){
                        g.setColor(new Color(0,255,0));
                        if(j > (column/4) && j < (3*column/4) && i > (row/4) && i < (3*row/4) ) {
                            g.setColor(new Color(0,100,0));
                        }
                    }

                }
                //animal colors
                for (Vector2d vector: grassField.getAnimals().keySet()) {
                    if (i== vector.x && j == row - vector.y - 1){
                        if(grassField.getAnimals().get(vector) == null){
                            break;
                        }
                        if(grassField.getAnimals().get(vector).getEnergy() < 2)
                            g.setColor(new Color(0,0,0));
                        else if(grassField.getAnimals().get(vector).getEnergy() < 4)
                            g.setColor(new Color(0,0,64));
                        else if(grassField.getAnimals().get(vector).getEnergy() < 7)
                            g.setColor(new Color(0,0,128));
                        else if(grassField.getAnimals().get(vector).getEnergy() < 10)
                            g.setColor(new Color(128,0,192));
                        else if(grassField.getAnimals().get(vector).getEnergy() < 15)
                            g.setColor(new Color(128,0,255));
                        else if(grassField.getAnimals().get(vector).getEnergy() < 22)
                            g.setColor(new Color(0,128,255));
                        else
                            g.setColor(new Color(0,255,255));
                    }
                }

                g.fillRect(17 * i,17 * j,15,15);
            }
        }
    }
}
