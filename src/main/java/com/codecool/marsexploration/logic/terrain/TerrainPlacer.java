package com.codecool.marsexploration.logic.terrain;

import com.codecool.marsexploration.data.Planet;

import java.util.Random;

public class TerrainPlacer {
    private final Random random;
    private final TerrainProvider terrainProvider;

    public TerrainPlacer(Random random, TerrainProvider terrainProvider) {
        this.random = random;
        this.terrainProvider = terrainProvider;
    }

    public void randomGenerated(Planet planet) {
        //Map ToDo: make duple array
        int planetX[] = new int[planet.xyLength()];
        int planetY[] = new int[planet.xyLength()];

        int areaCounter = 0;
        while (areaCounter < planet.amountAreas()) {
            //random pick form planet.areas -> return area(name, size, symbol)
            int areaSize = 0;
            while (areaSize < area.size()) {
                //validator([][], areaSize) -> return x,y Coordinate
                //printer -> print in x and y planet array symbol
                areaSize++;
            }
        }
        areaCounter++;
    }
}