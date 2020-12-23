package agh.cs.project.simulation;

import agh.cs.project.elements.Vector2d;
import agh.cs.project.interfaces.IEngine;
import agh.cs.project.interfaces.IWorldMap;
import agh.cs.project.maps.GrassField;

import java.util.ArrayList;
import java.util.Random;

public class GameSimulation {
    private GrassField grassField;
    private Vector2d[] positions;
    private IEngine simulationEngine;
    private int numberOfAnimals;

    public GameSimulation(int fieldHeight, int fieldWidth, int grassNumber, int numberOfAnimals){
        this.numberOfAnimals = numberOfAnimals;
        this.grassField = new GrassField(fieldHeight,fieldWidth,grassNumber);
        this.positions = generateRandomPositions(numberOfAnimals);
        this.simulationEngine = new SimulationEngine(this.grassField, this.positions);
    }

    private Vector2d getFreePosition(){
        Random random = new Random();
        Vector2d randomPosition = new Vector2d(random.nextInt(grassField.getWidth()),
                random.nextInt(grassField.getHeight()));
        while(grassField.isOcupiedByAnimal(randomPosition)) {
            randomPosition.x = random.nextInt(grassField.getWidth());
            randomPosition.y = random.nextInt(grassField.getHeight());
        }
        return new Vector2d(randomPosition.x, randomPosition.y);
    }

    private Vector2d[] generateRandomPositions(int numberOfPositions){
        ArrayList<Vector2d> positions = new ArrayList<>();

        for (int i = 0; i < numberOfPositions; i++) {
            positions.add(getFreePosition());
        }
        Vector2d [] vector2ds = new Vector2d[positions.size()];
        for (int i = 0; i < positions.size(); i++) {
            vector2ds[i] = positions.get(i);
        }
        return vector2ds;
    }

    public void simulateOneDay(){
        try {
            simulationEngine.run();
        }catch(IllegalArgumentException e) {
            System.out.println(e.toString());
        }
    }

    public GrassField getGrassField() {
        return grassField;
    }
}
