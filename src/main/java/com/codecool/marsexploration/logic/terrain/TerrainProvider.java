package com.codecool.marsexploration.logic.terrain;

import com.codecool.marsexploration.data.Area;
import com.codecool.marsexploration.data.Planet;
import com.codecool.marsexploration.logic.resource.ResourcePlacer;
import com.codecool.marsexploration.ui.Display;

import java.util.List;
import java.util.Random;

public class TerrainProvider {
    private final Random random;
    //ToDo initial this classes down below just one time put all in Application and get it per Parameter or Constructor!
    private final TerrainValidator terrainValidator = new TerrainValidator();
    private final ResourcePlacer resourcePlacer = new ResourcePlacer();

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
            resourcePlacer.placeInTerrain(planetTerrains, planet, random, display);
        }
        display.errorMessage("The planet does not have enough space for all areas, please reduce your number of areas");
        return planetTerrains;
    }
}