package com.codecool.marsexploration.logic.terrain;

import com.codecool.marsexploration.data.Area;

import java.util.ArrayList;
import java.util.List;

public class TerrainValidator {
    private boolean isEmptySpace = true;

    public List<Integer> getXY(String[][] planetTerrains, Area area) {
        int x =0;
        int y=0;
        isEmptySpace = false;
        return new ArrayList<>(List.of(x,y));
    }

    public boolean isEmptySpace(){
        return isEmptySpace;
    }
}
