package com.codecool.marsexploration.logic.resource;

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
        //ToDo Slap
        Map<String, Integer> mapOfAreaSymbolAndAmount = planet.resources().stream()
                .flatMap(resource -> planet.areas().stream()
                        .filter(area -> resource.preferences().equals(area.symbol()))
                        .flatMap(area -> Arrays.stream(planetTerrains)
                                .flatMap(Arrays::stream)
                                .filter(terrain -> terrain.equals(area.symbol()))))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.summingInt(e -> 1)));

        //ToDo Slap
        Map<String, Integer> mapOfResourcesAndAmount = new HashMap<>();
        for (int i = 0; i < planet.resources().size(); i++) {
            String actualAreaSymbol = mapOfAreaSymbolAndAmount.keySet().toArray()[i].toString();
            int totalAmountOfArea = mapOfAreaSymbolAndAmount.get(actualAreaSymbol);
            String actualResourceSymbol = planet.resources().get(i).symbol();
            int resourceAmount = 0;
            if (actualResourceSymbol.equals(actualAreaSymbol)) {
                int minimumDivisor = 4;
                if (minimumDivisor < totalAmountOfArea) {
                    resourceAmount = totalAmountOfArea / random.nextInt(4, totalAmountOfArea);
                } else {
                    while (minimumDivisor > 0) {
                        minimumDivisor--;
                    }
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
            mapOfResourcesAndAmount.put(actualResourceSymbol, resourceAmount);
        }
        int counterResource = 0;
        while (counterResource < mapOfResourcesAndAmount.size()) {
            int counterAmountOfResource = 0;
            String actualKeyOfResource = mapOfResourcesAndAmount.keySet().toArray()[counterAmountOfResource].toString();
            while (counterAmountOfResource < mapOfResourcesAndAmount.get(actualKeyOfResource)) {
                int randomY = random.nextInt(planetTerrains.length);
                int randomX = random.nextInt(planetTerrains[0].length);
                String actualKeyOfArea = mapOfAreaSymbolAndAmount.keySet().toArray()[counterResource].toString();
                while (!planetTerrains[randomY][randomX].equals(actualKeyOfArea)) {
                    randomY = random.nextInt(planetTerrains.length);
                    randomX = random.nextInt(planetTerrains[0].length);
                }

                //ToDo Slap
                System.out.println("\nRandomY: " + randomY);
                System.out.println("RandomX: " + randomX);
                String actualTerrain = planetTerrains[randomY][randomX];
                System.out.println("Actual Terrain: " + actualTerrain);
                System.out.println("Actual Area Symbol: " + actualKeyOfArea);
                while (actualTerrain.equals(actualKeyOfArea) &&
                        !actualTerrain.equals("")) {
                    int randomPlacer = random.nextInt(1, 4);
                    actualTerrain = planetTerrains[randomY][randomX];
                    switch (randomPlacer) {
                        case 1 -> {
                            if (randomX < planet.xyLength()) {
                                randomX++;
                            }
                        }
                        case 2 -> {
                            if (randomX > 0) {
                                randomX--;
                            }
                        }
                        case 3 -> {
                            if (randomY < planet.xyLength()) {
                                randomY++;
                            }
                        }
                        case 4 -> {
                            if (randomY > 0) {
                                randomY--;
                            }
                        }
                    }
                }
                System.out.println("\nTerrainXY: " + planetTerrains[randomY][randomX]);
                System.out.println(planetTerrains[randomY][randomX].equals(""));
                if (planetTerrains[randomY][randomX].equals("")) {
                    System.out.println("Resource: " + actualKeyOfResource);
                    planetTerrains[randomY][randomX] = actualKeyOfResource;
                }
                counterAmountOfResource++;
            }
            counterResource++;
        }
        return planetTerrains;
    }
}