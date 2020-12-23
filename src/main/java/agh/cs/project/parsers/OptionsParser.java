package agh.cs.project.parsers;

import agh.cs.project.enums.MoveDirection;

import java.util.ArrayList;
import java.util.HashMap;

public class OptionsParser {

    public MoveDirection[] parse(String[] args){

        HashMap<String,MoveDirection> hmap = new HashMap<String,MoveDirection>();
        ArrayList<MoveDirection> list = new ArrayList<MoveDirection>();

        hmap.put("f",MoveDirection.FORWARD);
        hmap.put("b",MoveDirection.BACKWARD);
        hmap.put("r",MoveDirection.RIGHT);
        hmap.put("l",MoveDirection.LEFT);
        hmap.put("forward",MoveDirection.FORWARD);
        hmap.put("backward",MoveDirection.BACKWARD);
        hmap.put("right",MoveDirection.RIGHT);
        hmap.put("left",MoveDirection.LEFT);

        for(int i=0; i < args.length;i++){
            if(hmap.get(args[i]) != null){
                list.add(hmap.get(args[i]));
            }else {
                throw new IllegalArgumentException("'"+args[i] + "' is not legal move exception");
            }
        }

        return list.toArray(new MoveDirection[list.size()]);
    }
}
