package com.codecool.marsexploration.logic.terrain;

import com.codecool.marsexploration.data.Area;
import com.codecool.marsexploration.data.Planet;
import com.codecool.marsexploration.logic.resource.ResourcePlacer;
import com.codecool.marsexploration.ui.Display;

import java.util.Map;
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
        String[][] planetTerrains = new String[planet.xyLength()][planet.xyLength()];
        for (int y = 0; y < planet.xyLength(); y++) {
            for (int x = 0; x < planet.xyLength(); x++) {
                planetTerrains[y][x] = " ";
            }
        }
        for (int i = 0; i < planet.areas().size(); i++) {
            Area actualArea = planet.areas().get(i);
            Map<Integer, Integer> actualAreaXYs = terrainValidator.getXY(planetTerrains, actualArea, random, display);
            System.out.println("TERRAINPROVIDER actualAreaXYs: " + actualAreaXYs);
            if (!actualAreaXYs.isEmpty()) {
                for (Map.Entry<Integer, Integer> set : actualAreaXYs.entrySet()) {
                    planetTerrains[set.getKey()][set.getValue()] = actualArea.symbol();
                }
            } else {
                display.errorMessage("The planet does not have enough space for all areas, please reduce your number of areas");
            }
        }
        return resourcePlacer.placeInTerrain(planetTerrains, planet, random, display);
    }
}