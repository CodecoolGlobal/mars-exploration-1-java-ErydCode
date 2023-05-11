package com.codecool.marsexploration.logic.terrain;

import com.codecool.marsexploration.data.Area;
import com.codecool.marsexploration.data.Planet;
import com.codecool.marsexploration.ui.Display;

import java.util.List;
import java.util.Random;

public class TerrainProvider {
    private final Random random;
    private final TerrainValidator terrainValidator = new TerrainValidator();

    public TerrainProvider(Random random) {
        this.random = random;
    }

    public String[][] randomGenerated(Planet planet, Display display) {
            //[y][x]
        String[][] planetTerrains = new String[planet.xyLength()][planet.xyLength()];

        while (terrainValidator.isEmptySpace()) {
            int areaCounter = 0;
            while (areaCounter < planet.amountAreas()) {
                Area area = planet.areas().get(random.nextInt(planet.areas().size()));
                int areaSize = 0;
                while (areaSize < area.size()) {
                    List<Integer> placeForSymbolXY = terrainValidator.getXY(planetTerrains, area);
                    int x = placeForSymbolXY.get(0);
                    int y = placeForSymbolXY.get(1);
                    planetTerrains[y][x] = area.symbol();
                    areaSize++;
                }
                areaCounter++;
            }
        }
        display.errorMessage("The planet does not have enough space for all areas, please reduce your number of areas");
        return planetTerrains;
    }
}