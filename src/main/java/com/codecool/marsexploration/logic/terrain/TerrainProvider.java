package com.codecool.marsexploration.logic.terrain;

import com.codecool.marsexploration.data.Area;
import com.codecool.marsexploration.data.Planet;

import java.util.Random;

public class TerrainProvider {
    private final Random random;
    private final TerrainValidator terrainValidator = new TerrainValidator();
    private final TerrainPlacer terrainPlacer = new TerrainPlacer();

    public TerrainProvider(Random random) {
        this.random = random;
    }

    public void randomGenerated(Planet planet) {
        int planetTerrains[][] = new int[planet.xyLength()][planet.xyLength()];

        while (terrainValidator.isEmptySpace()) {
        int areaCounter = 0;
            while (areaCounter < planet.amountAreas()) {
                Area area = planet.areas().get(random.nextInt(planet.areas().size()));
                int areaSize = 0;
                while (areaSize < area.size()) {
                    terrainValidator.getXY(planetTerrains, area);
                    //printer -> print in x and y planet array symbol
                    areaSize++;
                }
            }
        areaCounter++;
        }
        //error Message
    }
}
