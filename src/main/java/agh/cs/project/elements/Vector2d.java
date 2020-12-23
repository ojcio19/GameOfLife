package agh.cs.project.elements;

public class Vector2d {
    public int x = 0;
    public int y = 0;

    public Vector2d(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean precedes(Vector2d other){
        if(other.getX() >= this.getX() && other.getY() >= this.getY()) return true;

        return false;
    }

    public boolean follows(Vector2d other){
        if(other.getX() <= this.getX() && other.getY() <= this.getY()) return true;

        return false;
    }

    public Vector2d upperRight(Vector2d other){
        return new Vector2d(Math.max(this.x, other.getX()),
                Math.max(this.y, other.getY()));
    }

    public Vector2d lowerLeft(Vector2d other){
        return new Vector2d(Math.min(this.x, other.getX()),
                Math.min(this.y, other.getY()));
    }

    public Vector2d add(Vector2d other){
        return new Vector2d(this.getX() + other.getX(),this.getY() + other.getY());
    }

    public Vector2d subtract(Vector2d other){
        return new Vector2d(this.getX() - other.getX(),this.getY() - other.getY());
    }

    public Vector2d opposite(){
        return new Vector2d(-this.getX(),-this.getY());
    }

    public String toString(){
        return new String("(" + x + "," + y + ")");
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Vector2d vector2d = (Vector2d) other;
        return x == vector2d.x &&
                y == vector2d.y;
    }

    @Override
    public int hashCode() {
        int hash = 13;
        hash += this.x * 31;
        hash += this.y * 17;
        return hash;
    }
}
