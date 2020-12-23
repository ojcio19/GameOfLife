package agh.cs.project.enums;

import agh.cs.project.elements.Vector2d;

public enum MapDirection {
    NORTH(0),NORTH_EAST(1),EAST(2),SOUTH_EAST(3), SOUTH(4), SOUTH_WEST(5),WEST(6),NORTH_WEST(7);

    MapDirection(int i) {
    }

    public MapDirection next(){
         switch(this) {
             case NORTH: return NORTH_EAST;
             case NORTH_EAST: return EAST;
             case EAST: return SOUTH_EAST;
             case SOUTH_EAST: return SOUTH;
             case SOUTH: return SOUTH_WEST;
             case SOUTH_WEST: return WEST;
             case WEST: return NORTH_WEST;
             case NORTH_WEST: return NORTH;
             default: return null;
         }
     };

    public MapDirection previous(){
        switch(this) {
            case NORTH: return NORTH_WEST;
            case NORTH_WEST: return WEST;
            case WEST: return SOUTH_WEST;
            case SOUTH_WEST: return SOUTH;

            case SOUTH: return SOUTH_EAST;
            case SOUTH_EAST: return EAST;
            case EAST: return NORTH_EAST;
            case NORTH_EAST: return NORTH;
            default: return null;
        }
    };

    public Vector2d toUnitVector(){
        switch(this) {
            case NORTH: return new Vector2d(0,1);
            case NORTH_EAST: return new Vector2d(1,1);
            case EAST: return new Vector2d(1,0);
            case SOUTH_EAST: return new Vector2d(1,-1);
            case SOUTH: return new Vector2d(0,-1);
            case SOUTH_WEST: return new Vector2d(-1,-1);
            case WEST: return new Vector2d(-1,0);
            case NORTH_WEST: return new Vector2d(-1,1);
            default: return null;
        }
    };
}
