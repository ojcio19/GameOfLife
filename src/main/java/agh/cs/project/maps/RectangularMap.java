package agh.cs.project.maps;

import agh.cs.project.elements.Vector2d;

public class RectangularMap extends AbstractWorldMap {

    public RectangularMap(int width, int height){
        this.width = Math.max(width, 2);
        this.height = Math.max(height, 2);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return super.canMoveTo(position);
    }

    @Override
    public String toString(){
        return new MapVisualizer(this).draw(new Vector2d(0,0),new Vector2d(this.width-1,this.height-1));
    }
}
