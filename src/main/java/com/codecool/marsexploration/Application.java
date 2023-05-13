package com.codecool.marsexploration;

import com.codecool.marsexploration.data.Area;
import com.codecool.marsexploration.data.Planet;
import com.codecool.marsexploration.data.Resource;
import com.codecool.marsexploration.logic.area.AreasProvider;
import com.codecool.marsexploration.logic.area.AreasTypeProvider;
import com.codecool.marsexploration.logic.planet.PlanetProvider;
import com.codecool.marsexploration.logic.resource.ResourcePlacer;
import com.codecool.marsexploration.logic.resource.ResourcesProvider;
import com.codecool.marsexploration.logic.terrain.TerrainProvider;
import com.codecool.marsexploration.ui.Display;
import com.codecool.marsexploration.ui.Input;
import com.codecool.marsexploration.ui.MapImageGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Application {
    public static void main(String[] args) {
        Display display = new Display();
        Input input = new Input(display);
        Random random = new Random();
        AreasTypeProvider areasTypeProvider = new AreasTypeProvider();
        AreasProvider areas = new AreasProvider(display, input, random, areasTypeProvider);
        ResourcesProvider resources = new ResourcesProvider();
        PlanetProvider planet = new PlanetProvider(display, input, random, areas, resources);
        ResourcePlacer resourcePlacer = new ResourcePlacer(random, display);
        TerrainProvider terrainProvider = new TerrainProvider(display, random, resourcePlacer);
        MapImageGenerator mapImageGenerator = new MapImageGenerator();
        Fake2DArray fake2DArray = new Fake2DArray();

        //TODO: From here on the program runs - new Class
        display.printTitle("Welcome to planet creator - simulate your planet");
        display.printSubtitle("You want create your own Planet or use an already explored Exodus Planet?");
        int userChoice = 0;
        while (userChoice < 1 || userChoice > 2) {
            userChoice = input.getNumericUserInput("Please enter 1 to create your own Planet\n" +
                    "Please enter 2 to use the explored Exodus Planet");
        }
        if (userChoice == 1) {
            Planet createdPlanet = planet.getPlanet();
            display.printTitle(createdPlanet.name());
            String[][] userPlanet = terrainProvider.getRandomGeneratedTerrainForPlanet(createdPlanet);
            //TODO: use your display class and extract this method
            for (String[] strings : userPlanet) {
                System.out.println(Arrays.toString(strings));
            }
        }
        if (userChoice == 2) {
            //TODO: Magic number
            int[] rgbMountains = new int[]{102, 51, 0};
            List<Area> presetMountain = areasTypeProvider.getArea("Mountain", 4, 10, 40, "^", rgbMountains, random);
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
            Planet exploredExodusPlanet = new Planet("CodeCool", 50, allAreas, 8, allResource);
            String[][] randomExodusPlanet =
                    terrainProvider.getRandomGeneratedTerrainForPlanet(exploredExodusPlanet);
            //resourcePlacer.placeInTerrain(fake2DArray.getFakeMap(), exploredExodusPlanet, random, display);
            for (String[] strings : randomExodusPlanet) {
                System.out.println(Arrays.toString(strings));
                mapImageGenerator.generateImage(exploredExodusPlanet, allAreas, allResource, randomExodusPlanet);
            }
            /*
            List<Area> allAreas = areas.getAreas();
            List<Resource> allResource = resources.getResource(display, input, allAreas);
            return new Planet(name, xyLength, allAreas, amountAreas, allResource);
             */
        }
        display.printEndLines();
    }
}