package agh.cs.project.maps;

import agh.cs.project.elements.Animal;
import agh.cs.project.elements.Grass;
import agh.cs.project.elements.Vector2d;
import agh.cs.project.maps.MapVisualizer;
import agh.cs.project.interfaces.IWorldMap;

import java.util.*;

abstract public class AbstractWorldMap implements IWorldMap {

    protected int width = 10;
    protected int height = 10;
    protected Map<Vector2d, Grass> grassFields = new HashMap<>();
    protected Map<Vector2d, Animal> animals = new HashMap<>();
    protected int totalLifeTime = 0;
    protected int deadAnimals = 0;

    public interface IPositionChangeObserver {
        void positionChanged(Vector2d oldPosition, Vector2d newPosition);
    }

    public AbstractWorldMap() {}

    @Override
    public boolean canMoveTo(Vector2d position) {
        return !isOcupiedByAnimal(position);
    }

    @Override
    public boolean place(Animal animal) {
        if(isOcupiedByAnimal(animal.getPosition())){
            throw new IllegalArgumentException("animal cannot be placed on the: " + animal.getPosition());
        }else{
            animals.put(animal.getPosition(),animal);

            animal.addObserver((oldPosition, newPosition) -> {
                Animal current = (Animal)objectAt(oldPosition);
                animals.remove(oldPosition);
                animals.put(newPosition, current);
            });
            return true;
        }
    }
    @Override
    public boolean remove(Animal animal){
        animals.remove(animal.getPosition());
        return true;
    }
    @Override
    public boolean isOcupiedByAnimal(Vector2d position){
        for(Vector2d example: animals.keySet()){
            if(example.equals(position)){
                return true;
            }
        }
        return false;
    }
    @Override
    public void removeDeadAnimals(){
        ArrayList<Animal> deadAnimals = new ArrayList<>();
        for (Animal animal:animals.values()) {
            if(animal.getEnergy() < 0)
                deadAnimals.add(animal);
        }
        for (Animal animal:deadAnimals) {
            this.totalLifeTime += animal.getLifeLength();
            animals.remove(animal.getPosition());
            this.deadAnimals++;
        }
    }

    @Override
    public void run() {
        //todo why animal is gone without reason
        ArrayList<Animal> animalsToMove = new ArrayList<>(animals.values());
        for (Animal animal: animalsToMove) {
            animal.move(animal.getRandomGen());
        }
    }

    public void eatGrass(){
        for (Animal animal:animals.values()) {
            Vector2d rememberedGrassPosition = null;
            for (Vector2d grassPosition: grassFields.keySet()) {
                if(animal != null) {
                    if (grassPosition.equals(animal.getPosition())) {
                        rememberedGrassPosition = grassPosition;
                        animal.setEnergy(2.0);
                        animal.setEnergy(2.0);
                    }
                }
            }
            if(rememberedGrassPosition != null){
                grassFields.remove(rememberedGrassPosition);
            }
        }
    }

    public void addNewGrass(){
        if(grassFields.size() == width*height){return;}
        Random random = new Random();
        Vector2d randomPosition = new Vector2d(random.nextInt(getWidth()),
                random.nextInt(getHeight()));
        while(isOccupied(randomPosition)) {
            randomPosition.x = random.nextInt(getWidth());
            randomPosition.y = random.nextInt(getHeight());
        }
        grassFields.put(randomPosition, new Grass(randomPosition));
    }

    public int getDominatingGenome(){
        int [] genome = new int[]{0,0,0,0,0,0,0,0};
        for (Animal animal: animals.values()) {
            for (int i = 0; i < 32; i++) {
                genome[animal.getGenes()[i]]++;
            }
        }

        int maxValue = 0, index = 0;
        for(int i=0; i < genome.length; i++){
            if(genome[i] > maxValue){
                maxValue = genome[i];
                index = i;
            }
        }
        return index;
    }

    public Double getMeanEnergy(){
        double sumEnergy = 0;
        for (Animal animal: animals.values()) {
            sumEnergy +=animal.getEnergy();
        }
        double result = sumEnergy/ animals.size();
        return Math.round(result*100.0)/100.0;
    }

    public double getMeanLifeTime(){
        if(deadAnimals != 0 ){
            double result = (double) totalLifeTime / (double) deadAnimals;
            return Math.round(result*100.0)/100.0;

        }else{
            return 0.0;
        }
    }

    public double getMeanNumberOfChildren(){
        double sum = 0.0;
        for (Animal animal: animals.values()) {
            sum += animal.getChildrenNumber();
        }

        double result = sum / (double) animals.size();
        return Math.round(result*100.0)/100.0;
    }

    public Map<Vector2d, Grass> getGrassFields() {
        return grassFields;
    }

    public Map<Vector2d, Animal> getAnimals() {
        return animals;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    @Override
    public int getWidth() {
        return this.width;
    }


    @Override
    public boolean isOccupied(Vector2d position) {
        for(Vector2d example: animals.keySet()){
            return example.equals(position);
        }
        //todo remove, probably not needed
        for(Vector2d example: grassFields.keySet()){
            return example.equals(position);
        }
        return false;
    }

    @Override
    public Object objectAt(Vector2d position) {
        for (Vector2d vector: animals.keySet()) {
            if(vector.equals(position)){
                return animals.get(vector);
            }
        }
        return null;
    }

    @Override
    public String toString(){
        return new MapVisualizer(this).draw(new Vector2d(0,0),new Vector2d(this.width-1,this.height));
    }
}
