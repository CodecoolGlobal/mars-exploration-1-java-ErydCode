package com.codecool.marsexploration.logic.planet;

import com.codecool.marsexploration.data.Planet;
import com.codecool.marsexploration.io.PlanetImageWriter;
import com.codecool.marsexploration.logic.terrain.TerrainProvider;
import com.codecool.marsexploration.ui.Display;
import com.codecool.marsexploration.ui.Input;

import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;


public class PlanetProvider {
    private final Display display;
    private final Input input;
    private final PlanetTypeProvider planetTypeProvider;
    private final TerrainProvider terrainProvider;
    private final PlanetImageWriter planetImageWriter;

    public PlanetProvider(Display display, Input input, PlanetTypeProvider planetTypeProvider, TerrainProvider terrainProvider, PlanetImageWriter planetImageWriter) {
        this.display = display;
        this.input = input;
        this.planetTypeProvider = planetTypeProvider;
        this.terrainProvider = terrainProvider;
        this.planetImageWriter = planetImageWriter;
    }

    public void userCustomized() {
        String wantCreateOwnNewPlanet = "yes";
        while (!containsIgnoreCase("no", wantCreateOwnNewPlanet)) {
            Planet createdPlanet = planetTypeProvider.getPlanet();
            String wantGenerateNewTerrainsForSamePlanet = "yes";
            while (!containsIgnoreCase("no", wantGenerateNewTerrainsForSamePlanet)) {
                int counterImgName = 0;
                display.printTitle(createdPlanet.name());
                String[][] PlanetTerrain = terrainProvider.getRandomGeneratedTerrainForPlanet(createdPlanet);
                display.doppleArrayMap(PlanetTerrain, "Your Planet look");
                display.printEndLines();
                planetImageWriter.generateImage(createdPlanet, PlanetTerrain, counterImgName);
                wantGenerateNewTerrainsForSamePlanet = input.getUserInput("Do you want to create a new map?\n" +
                        "Pleaser enter the command \"yes\"/\"y\" or \"no\"/\"n\".");
            }
            wantCreateOwnNewPlanet = input.getUserInput("Do you want to create a new costume planet?\n" +
                    "Pleaser enter the command \"yes\"/\"y\" or \"no\"/\"n\".");
        }
    }
}