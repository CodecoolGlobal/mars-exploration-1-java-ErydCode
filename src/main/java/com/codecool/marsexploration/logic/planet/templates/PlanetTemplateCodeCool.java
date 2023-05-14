package com.codecool.marsexploration.logic.planet.templates;

import com.codecool.marsexploration.data.Area;
import com.codecool.marsexploration.data.Planet;
import com.codecool.marsexploration.data.Resource;
import com.codecool.marsexploration.io.PlanetImageWriter;
import com.codecool.marsexploration.logic.area.AreasTypeProvider;
import com.codecool.marsexploration.logic.terrain.TerrainProvider;
import com.codecool.marsexploration.ui.Display;
import com.codecool.marsexploration.ui.Input;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;

public class PlanetTemplateCodeCool {
    private final Display display;
    private final Input input;
    private final Random random;
    private final AreasTypeProvider areasTypeProvider;
    private final TerrainProvider terrainProvider;
    private final PlanetImageWriter planetImageWriter;

    public PlanetTemplateCodeCool(Display display, Input input, Random random, AreasTypeProvider areasTypeProvider, TerrainProvider terrainProvider, PlanetImageWriter planetImageWriter) {
        this.display = display;
        this.input = input;
        this.random = random;
        this.areasTypeProvider = areasTypeProvider;
        this.terrainProvider = terrainProvider;
        this.planetImageWriter = planetImageWriter;
    }

    public void getTemplate() {
        int[] rgbMountains = new int[]{102, 51, 0};
        List<Area> presetMountain = areasTypeProvider.getArea("Mountain", 4, 10, 30, "^", rgbMountains, random);
        int[] rgbPits = new int[]{32, 32, 32};
        List<Area> presetPits = areasTypeProvider.getArea("Pit", 2, 5, 15, "#", rgbPits, random);
        List<Area> allAreas = new ArrayList<>();
        allAreas.addAll(presetMountain);
        allAreas.addAll(presetPits);
        List<Resource> allResource = new ArrayList<>();
        int[] rgbMineral = new int[]{160, 160, 160};
        Resource presetMineral = new Resource("Mineral", "*", "^", rgbMineral);
        int[] rgbWater = new int[]{0, 0, 255};
        Resource presetWater = new Resource("Water", "≈", "#", rgbWater);
        allResource.add(presetMineral);
        allResource.add(presetWater);
        Planet exodusPlanet = new Planet("CodeCool", 25, allAreas, allAreas.size(), allResource);
        String wantGenerateNewTerrainsForSamePlanet = "yes";
        while (!containsIgnoreCase("no", wantGenerateNewTerrainsForSamePlanet)) {
            int counterImgName = 0;
            String[][] PlanetTerrain = terrainProvider.getRandomGeneratedTerrainForPlanet(exodusPlanet);
            display.doppleArrayMap(PlanetTerrain, "Your planet look");
            display.printEndLines();
            planetImageWriter.generateImage(exodusPlanet, PlanetTerrain, counterImgName);
            wantGenerateNewTerrainsForSamePlanet = input.getUserInput("Do you want to create a new map?\n" +
                    "Pleaser enter the command \"yes\"/\"y\" or \"no\"/\"n\".");
        }
    }
}