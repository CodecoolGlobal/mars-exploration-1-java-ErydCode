package com.codecool.marsexploration.logic.resource;

import com.codecool.marsexploration.data.Planet;

import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ResourcePlacer {
    public void placeInTerrain(String[][] planetTerrains, Planet planet, Random random) {
        //ToDo: generate Random amount of each resource depends of Amount of the dependency Symbol
        Map<String, Integer> mapOfSymbolAndTotalAmountOfOneValidArea = planet.allResource().stream()
                .flatMap(resource -> planet.areas().stream()
                        .filter(area -> resource.preferences().equals(area.symbol()))
                        .flatMap(area -> Arrays.stream(planetTerrains)
                                .flatMap(Arrays::stream)
                                .filter(terrain -> terrain.equals(area.symbol()))))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.summingInt(e -> 1)));
        System.out.println(mapOfSymbolAndTotalAmountOfOneValidArea);
        System.out.println(mapOfSymbolAndTotalAmountOfOneValidArea.size());


        int counterResourceType = 0;
        while (counterResourceType < mapOfSymbolAndTotalAmountOfOneValidArea.size()) {
            int counterActualResource = 0;
            //ToDo replace 5 with an Array with the same size of Resource Symbols with a random amount. !!Same Order Like Map preferred Symbol
            while (counterActualResource < 5) {
                int randomY = random.nextInt(planet.xyLength());
                int randomX = random.nextInt(planet.xyLength());
                //!!!!ToDo actualSymbol have to be Symbol of Resource not of Area!!!!!
                String actualSymbol = mapOfSymbolAndTotalAmountOfOneValidArea.keySet().toArray()[counterResourceType].toString();
                while (!planetTerrains[randomY][randomX].equals(actualSymbol)) {
                    randomY = random.nextInt(planet.xyLength());
                    randomX = random.nextInt(planet.xyLength());
                }
                //ToDo (Just if is enough time) Think about a better idea because with this switch it can be happen that x,y is jumping forward and backward = loop between 2 Coordinates
                while (planetTerrains[randomY][randomX].equals(actualSymbol)) {
                    int randomPlacer = random.nextInt(1, 4);
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
                    planetTerrains[randomY][randomX] = actualSymbol;
                }
                counterActualResource++;
            }
            counterResourceType++;
        }
    }
}