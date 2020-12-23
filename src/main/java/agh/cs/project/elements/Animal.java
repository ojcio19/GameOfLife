package agh.cs.project.elements;

import agh.cs.project.maps.AbstractWorldMap;
import agh.cs.project.enums.MapDirection;
import agh.cs.project.interfaces.IWorldMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Animal {

    private Vector2d position;
    private MapDirection mapDirection = MapDirection.NORTH;
    private IWorldMap map;
    private List<AbstractWorldMap.IPositionChangeObserver> observers = new ArrayList<>();
    private double energy;
    private int lifeLength = 0, children = 0;
    private int[] genes = new int[32];

    public Animal(IWorldMap map, Vector2d initialPosition){
        this.map = map;
        if (map.isOcupiedByAnimal(initialPosition)){
            this.position = findRandomFreePosition();
        }else
            this.position = initialPosition;
        this.energy = 12;
        findGenes();
    }

    public Animal(IWorldMap map, Vector2d initialPosition, int[] genes, double energy){
        this.map = map;
        if (map.isOcupiedByAnimal(initialPosition)){
            this.position = findRandomFreePosition();
        }else
            this.position = initialPosition;
        this.energy = energy;
        this.genes = genes;
    }

    private Vector2d findRandomFreePosition(){
        Random random = new Random();
        Vector2d randomPosition = new Vector2d(random.nextInt(map.getWidth()),
                random.nextInt(map.getHeight()));
        while(map.isOcupiedByAnimal(randomPosition)) {
            randomPosition.x = random.nextInt(map.getWidth());
            randomPosition.y = random.nextInt(map.getHeight());
        }
        return new Vector2d(randomPosition.x, randomPosition.y);
    }

    private void findGenes() {
        for (int i = 0; i < 32; i++) {
            Random random = new Random();
            this.genes[i] = random.nextInt(8);
        }
    }

    public int getRandomGen(){
        return genes[new Random().nextInt(32)];
    }

    public void addObserver(AbstractWorldMap.IPositionChangeObserver observer){
        observers.add(observer);
    }

    public void removeObserver(AbstractWorldMap.IPositionChangeObserver observer){
        observers.remove(observer);
    }

    public Vector2d getPosition() {
        return position;
    }

    private void changePositionTo(Vector2d positionNext) {    //warunki brzegowe
        Vector2d old = new Vector2d(this.position.x,this.position.y);
        int moveFlag = 0;
        if(positionNext.x >= map.getWidth()) {
            this.position = new Vector2d(0, getPosition().y);
            moveFlag += 7;
        }
        if(positionNext.x < 0) {
            this.position = new Vector2d(map.getWidth() - 1, getPosition().y);
            moveFlag += 11;
        }
        if(positionNext.y >= map.getHeight()){
            this.position = new Vector2d(getPosition().x,0);
            moveFlag += 13;
        }
        if(positionNext.y < 0){
            this.position = new Vector2d(getPosition().x, map.getHeight() -1);
            moveFlag += 17;
        }

        if(moveFlag < 1){
                this.position = new Vector2d(positionNext.x, positionNext.y);
        }

        if(!old.equals(this.position))  //notify observers
            observers.forEach(observer -> observer.positionChanged(old,this.position));
    }

    public void move(int direction){
        for (int i = 0; i < direction; i++) {
            this.mapDirection = mapDirection.next();
        }
        //System.out.println("map direction:" + this.mapDirection.toString());
        if(map.canMoveTo(getCorrectPosition(this.position.add(mapDirection.toUnitVector())))) {
            changePositionTo(this.position.add(mapDirection.toUnitVector()));
            this.energy -=1;
        }else{
            if(this.energy > 8.0){
                Animal other = (Animal) map.objectAt(getCorrectPosition(this.position.add(mapDirection.toUnitVector())));
                if (other.getEnergy() > 8.0)
                    reproduce(other);

                other.energy = other.energy*1/4;
                this.energy = this.energy*1/4;
            }
        }
        lifeLength++;
    }

    private void reproduce(Animal other){
        int [] genesChild = new int[32];
        for (int i = 0; i < 16; i++) {
            genesChild[i] = this.getRandomGen();
        }
        for (int i = 16; i < 24; i++) {
            genesChild[i] = other.getRandomGen();
        }
        for (int i = 24; i < 32; i++) {
            Random random = new Random();
            genesChild[i] = random.nextInt(8);
        }
        this.children++;
        map.place(new Animal(map,findRandomFreePosition(),genesChild,(this.energy+ other.getEnergy())/2));
    }

    private Vector2d getCorrectPosition(Vector2d position){
        Vector2d correctPosition = new Vector2d(position.x, position.y);
        if(position.x >= map.getWidth()) {
            correctPosition.x = 0;
        }
        if(position.x < 0) {
            correctPosition.x = map.getWidth() - 1;
        }
        if(position.y >= map.getHeight()){
            correctPosition.y = 0;
        }
        if(position.y < 0){
            correctPosition.y = map.getHeight() - 1;
        }
        return correctPosition;
    }

    public int[] getGenes() {
        return genes;
    }

    public int getLifeLength() {
        return this.lifeLength;
    }

    public int getChildrenNumber() {
        return children;
    }

    public String toString(){
        return "O";
    }

    public double getEnergy() {
        return energy;
    }

    public void setEnergy(double energy) {
        this.energy += energy;
    }
}
