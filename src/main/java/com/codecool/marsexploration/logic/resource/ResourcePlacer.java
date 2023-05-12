package com.codecool.marsexploration.logic.resource;

import com.codecool.marsexploration.data.Constants;
import com.codecool.marsexploration.data.Planet;
import com.codecool.marsexploration.ui.Display;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ResourcePlacer {
    public String[][] placeInTerrain(String[][] planetTerrains, Planet planet, Random random, Display display) {
        //ToDo Maybe Small Bug: by creating Map preference Symbol from Resource have to be equal to Area Symbol with the Same Index!
        Map<String, Integer> mapOfAreaSymbolAndAmount = getMapOfAreasAndTotalSymbolsOnPlanet(planetTerrains, planet);
        Map<String, Integer> mapOfResourcesAndAmount = getMapOfResourcesAndAmount(planet, random, display, mapOfAreaSymbolAndAmount);
        getRandomStartCoordinateToPlaceResource(planetTerrains, random, mapOfAreaSymbolAndAmount, mapOfResourcesAndAmount);
        return planetTerrains;
    }


    private Map<String, Integer> getMapOfResourcesAndAmount(Planet planet, Random random, Display display, Map<String, Integer> mapOfAreaSymbolAndAmount) {
        Map<String, Integer> mapOfResourcesAndAmount = new HashMap<>();
        for (int i = 0; i < planet.resources().size(); i++) {
            String actualAreaSymbol = mapOfAreaSymbolAndAmount.keySet().toArray()[i].toString();
            int totalAmountOfArea = mapOfAreaSymbolAndAmount.get(actualAreaSymbol);
            String actualResourceSymbol = planet.resources().get(i).symbol();
            String actualPreferenceSymbol = planet.resources().get(i).preferences();
            int resourceAmount = 0;
            //resourceAmount = getResourceAmount(planet, random, display, actualAreaSymbol, totalAmountOfArea, actualPreferenceSymbol, resourceAmount);
            //TODO: test
            resourceAmount = totalAmountOfArea > 4?
                    totalAmountOfArea / random.nextInt(4, (totalAmountOfArea / 2)) :
                    0;
            mapOfResourcesAndAmount.put(actualResourceSymbol, resourceAmount);
        }
        return mapOfResourcesAndAmount;
    }

    private int getResourceAmount(Planet planet, Random random, Display display, String actualAreaSymbol, int totalAmountOfArea, String actualPreferenceSymbol, int resourceAmount) {
        if (!actualPreferenceSymbol.equals(actualAreaSymbol)) {
            int minimumDivisor = 4;
            if (minimumDivisor < totalAmountOfArea) {
                resourceAmount = totalAmountOfArea / random.nextInt(minimumDivisor, (totalAmountOfArea / 2));
            } else {
                /*while (minimumDivisor > 0) {
                    minimumDivisor--;
                }*/
                display.errorMessage("Your Size of this Area is 0! Please make a new Planet with minim Area Size of 1");
            }
        } else {
            int areasAmount = planet.amountAreas();
            if (areasAmount > 0) {
                resourceAmount = planet.amountAreas() / random.nextInt(1, planet.amountAreas());
            } else {
                display.errorMessage("Your Planet hase no Areas! Please create a new Planet with minim 1 Area");
            }
        }
        return resourceAmount;
    }

    private void getRandomStartCoordinateToPlaceResource(String[][] planetTerrains, Random random, Map<String, Integer> mapOfAreaSymbolAndAmount, Map<String, Integer> mapOfResourcesAndAmount) {
        int counterResource = 0;
        while (counterResource < mapOfResourcesAndAmount.size()) {
            int counterAmountOfResource = 0;
            String actualKeyOfResource = mapOfResourcesAndAmount.keySet().toArray()[counterResource].toString();
            while (counterAmountOfResource < mapOfResourcesAndAmount.get(actualKeyOfResource)) {
                int randomY = random.nextInt(planetTerrains.length);
                int randomX = random.nextInt(planetTerrains.length);
                String actualKeyOfArea = mapOfAreaSymbolAndAmount.keySet().toArray()[counterResource].toString();
                while (!planetTerrains[randomY][randomX].equals(actualKeyOfArea)) {
                    randomY = random.nextInt(planetTerrains.length);
                    randomX = random.nextInt(planetTerrains.length);
                }
                getValidatedResourceCoordinate(planetTerrains, random, actualKeyOfResource, randomY, randomX, actualKeyOfArea);
                counterAmountOfResource++;
            }
            counterResource++;
        }
    }

    private Map<String, Integer> getMapOfAreasAndTotalSymbolsOnPlanet(String[][] planetTerrains, Planet planet) {
        return planet.resources().stream()
                .flatMap(resource -> planet.areas().stream()
                        .filter(area -> resource.preferences().equals(area.symbol()))
                        .flatMap(area -> Arrays.stream(planetTerrains)
                                .flatMap(Arrays::stream)
                                .filter(terrain -> terrain.equals(area.symbol()))))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.summingInt(e -> 1)));
    }

    private void getValidatedResourceCoordinate(String[][] planetTerrains, Random random, String actualKeyOfResource, int randomY, int randomX, String actualKeyOfArea) {
        String actualTerrain;
        do {
            int randomPlacer = random.nextInt(1, 4);
            actualTerrain = planetTerrains[randomY][randomX];
            switch (randomPlacer) {
                case 1 -> {
                    if (randomX < planetTerrains.length - 1) {
                        randomX++;
                    }
                }
                case 2 -> {
                    if (randomX > 0) {
                        randomX--;
                    }
                }
                case 3 -> {
                    if (randomY < planetTerrains.length - 1) {
                        randomY++;
                    }
                }
                case 4 -> {
                    if (randomY > 0) {
                        randomY--;
                    }
                }
            }
            if (planetTerrains[randomY][randomX].equals(Constants.EMPTY_SYMBOL)) {
                planetTerrains[randomY][randomX] = actualKeyOfResource;
            }
        } while (actualTerrain.equals(actualKeyOfArea) && !actualTerrain.equals(Constants.EMPTY_SYMBOL));
    }
}