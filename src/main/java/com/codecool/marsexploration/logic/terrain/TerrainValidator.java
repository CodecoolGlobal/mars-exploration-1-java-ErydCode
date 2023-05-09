package com.codecool.marsexploration.logic.terrain;

import com.codecool.marsexploration.data.Area;

import java.util.ArrayList;
import java.util.List;

public class TerrainValidator {
    private boolean isEmptySpace = true;

    public List<Integer> getXY(String[][] planetTerrains, Area area) {
        int x;
        int y;
        isEmptySpace = false;
        return new ArrayList<>(List.of());
    }

    public boolean isEmptySpace(){
        return isEmptySpace;
    }
}
