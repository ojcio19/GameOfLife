package agh.cs.project.simulation;

import agh.cs.project.elements.Animal;
import agh.cs.project.elements.Vector2d;
import agh.cs.project.interfaces.IEngine;
import agh.cs.project.interfaces.IWorldMap;

public class SimulationEngine implements IEngine {

    IWorldMap simulationMap;

    SimulationEngine(IWorldMap map, Vector2d[] positions){
        this.simulationMap = map;

        for (Vector2d vector: positions) {
            this.simulationMap.place(new Animal(simulationMap,vector));
        }
    }

    @Override
    public void run() {
        simulationMap.removeDeadAnimals();
        simulationMap.run();
        simulationMap.eatGrass();
        simulationMap.addNewGrass();
    }
}
