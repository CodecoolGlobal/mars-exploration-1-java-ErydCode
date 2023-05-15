package com.codecool.marsexploration.logic.terrain;

import com.codecool.marsexploration.data.Area;
import com.codecool.marsexploration.data.Constants;
import com.codecool.marsexploration.data.Coordinate;
import com.codecool.marsexploration.data.Planet;
import com.codecool.marsexploration.logic.CoordinateCreator;
import com.codecool.marsexploration.logic.resource.ResourcePlacer;
import com.codecool.marsexploration.ui.Display;

import java.util.Arrays;
import java.util.Random;
import java.util.Set;

public class TerrainProvider {
    private final Display display;
    private final Random random;
    private final CoordinateCreator coordinateCreator;
    private final ResourcePlacer resourcePlacer;

    public TerrainProvider(Display display, Random random, CoordinateCreator coordinateCreator, ResourcePlacer resourcePlacer) {
        this.display = display;
        this.random = random;
        this.coordinateCreator = coordinateCreator;
        this.resourcePlacer = resourcePlacer;
    }

    public String[][] getRandomGeneratedTerrainForPlanet(Planet planet) {
        String[][] planetTerrains = new String[planet.xyLength()][planet.xyLength()];
        initializeTerrainWithEmptySymbols(planetTerrains);
        for (int i = 0; i < planet.areas().size(); i++) {
            Area actualArea = planet.areas().get(i);
            TerrainValidator terrainValidator = new TerrainValidator(display, random, coordinateCreator);
            Set<Coordinate> coordinates = terrainValidator.getAreaCoordinate(planetTerrains, actualArea);
            if (!coordinates.isEmpty()) {
                for (Coordinate coordinate : coordinates) {
                    planetTerrains[coordinate.y()][coordinate.x()] = actualArea.symbol();
                }
            } else {
                display.errorMessage("The planet does not have enough space for all areas, please reduce your number of areas");
            }
        }
        return resourcePlacer.placeInTerrain(planetTerrains, planet);
    }

    private void initializeTerrainWithEmptySymbols(String[][] planetTerrains) {
        for (String[] planetTerrain : planetTerrains) {
            Arrays.fill(planetTerrain, Constants.EMPTY_SYMBOL);
        }
    }
}