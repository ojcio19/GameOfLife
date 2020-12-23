package agh.cs.project.elements;

public class Grass {
    private Vector2d position;

    public Vector2d getPosition() {
        return position;
    }

    public void setPosition(Vector2d position) {
        this.position = position;
    }

    public Grass(Vector2d position) {
        this.position = position;
    }
    @Override
    public String toString() {
        return "*";
    }
}
