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
    public void placeInTerrain(String[][] planetTerrains, Planet planet, Random random, Display display) {
        //ToDo Slap
        Map<String, Integer> mapOfAreaSymbolAndAmount = planet.resources().stream()
                .flatMap(resource -> planet.areas().stream()
                        .filter(area -> resource.preferences().equals(area.symbol()))
                        .flatMap(area -> Arrays.stream(planetTerrains)
                                .flatMap(Arrays::stream)
                                .filter(terrain -> terrain.equals(area.symbol()))))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.summingInt(e -> 1)));
        System.out.println(mapOfAreaSymbolAndAmount);
        System.out.println(mapOfAreaSymbolAndAmount.size());

        //ToDo Slap
        Map<String, Integer> mapOfResourcesAndAmount = new HashMap<>();
        for (int i = 0; i < planet.resources().size(); i++) {
            String actualAreaSymbol = mapOfAreaSymbolAndAmount.keySet().toArray()[i].toString();
            int totalAmountOfArea = mapOfResourcesAndAmount.get(actualAreaSymbol);
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
            mapOfResourcesAndAmount.put(actualAreaSymbol, resourceAmount);
        }
        int counterResource = 0;
        while (counterResource < mapOfResourcesAndAmount.size()) {
            int counterAmountOfResource = 0;
            String actualKeyOfResource = mapOfResourcesAndAmount.keySet().toArray()[counterAmountOfResource].toString();
            while (counterAmountOfResource < mapOfResourcesAndAmount.get(actualKeyOfResource)) {
                int randomY = random.nextInt(planet.xyLength());
                int randomX = random.nextInt(planet.xyLength());
                String actualKeyOfArea = mapOfAreaSymbolAndAmount.keySet().toArray()[counterResource].toString();
                while (!planetTerrains[randomY][randomX].equals(actualKeyOfArea)) {
                    randomY = random.nextInt(planet.xyLength());
                    randomX = random.nextInt(planet.xyLength());
                }

                //ToDo Slap
                int randomPlacer = random.nextInt(1, 4);
                while (planetTerrains[randomY][randomX].equals(actualKeyOfArea)) {
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
                if (planetTerrains[randomY][randomX].isEmpty()) {
                    planetTerrains[randomY][randomX] = actualKeyOfResource;
                }
                counterAmountOfResource++;
            }
            counterResource++;
        }
    }
}