package com.codecool.marsexploration.logic.resource;

import com.codecool.marsexploration.data.Constants;
import com.codecool.marsexploration.data.Coordinate;
import com.codecool.marsexploration.data.Planet;
import com.codecool.marsexploration.logic.CoordinateCreator;

import java.util.*;

public class ResourcePlacer {
    private final Random random;
    private final CoordinateCreator coordinateCreator;

    public ResourcePlacer(Random random, CoordinateCreator coordinateCreator) {
        this.random = random;
        this.coordinateCreator = coordinateCreator;
    }

    public String[][] placeInTerrain(String[][] planetTerrains, Planet planet) {
        int actualEmpty = freeSpaces(planetTerrains);
        Map<String, Integer> mapOfAreaSymbolAndAmount = getMapOfAreasAndTotalSymbolsOnPlanet(planetTerrains, planet);
        Map<String, Integer> mapOfResourcesAndAmount = getMapOfResourcesAndAmount(planet, mapOfAreaSymbolAndAmount, actualEmpty);
        placeAllResources(planet, planetTerrains, planetTerrains.length, mapOfResourcesAndAmount, mapOfAreaSymbolAndAmount);
        return planetTerrains;
    }

    private void placeAllResources(Planet planet, String[][] planetTerrains, int maxPlanetLength, Map<String, Integer> mapOfResourcesAndAmount, Map<String, Integer> mapOfAreaSymbolAndAmount) {
        for (int i = 0; i < planet.resources().size(); i++) {
            String preferenceSymbol = planet.resources().get(i).preferences();
            //ToDo find i way how to use the Code below without destroying the Methode!
//            if (mapOfAreaSymbolAndAmount.get(preferenceSymbol) != null) {
//                preferenceSymbol = Constants.EMPTY_SYMBOL;
//            }
            String resourceSymbol = planet.resources().get(i).symbol();
            int amountResource = mapOfResourcesAndAmount.get(resourceSymbol);
            int counterPlacedResource = 0;
            while (counterPlacedResource < amountResource) {
                Coordinate checkCoordinate = getStartCoordinate(planetTerrains, maxPlanetLength, preferenceSymbol);
                findValidTerrainForResource(planetTerrains, maxPlanetLength, resourceSymbol, checkCoordinate);
                counterPlacedResource++;
            }
        }
    }

    private Map<String, Integer> getMapOfAreasAndTotalSymbolsOnPlanet(String[][] planetTerrains, Planet planet) {
        Map<String, Integer> totalSymbolsOfAreas = new HashMap<>();
        for (int i = 0; i < planet.areas().size(); i++) {
            String areaSymbol = planet.areas().get(i).symbol();
            int totalAmount = (int) Arrays.stream(planetTerrains)
                    .flatMap(Arrays::stream)
                    .filter(terrain -> terrain.equals(areaSymbol))
                    .count();
            totalSymbolsOfAreas.put(areaSymbol, totalAmount);
        }
        return totalSymbolsOfAreas;
    }

    private Map<String, Integer> getMapOfResourcesAndAmount(Planet planet, Map<String, Integer> mapOfAreaSymbolAndAmount, int actualEmpty) {
        Map<String, Integer> mapOfResourcesAndAmount = new HashMap<>();
        for (int i = 0; i < planet.resources().size(); i++) {
            String preferenceSymbol = planet.resources().get(i).preferences();
            int randomAmount;
            if (mapOfAreaSymbolAndAmount.get(preferenceSymbol) != null) {
                int preferenceAmount = mapOfAreaSymbolAndAmount.get(preferenceSymbol);
                randomAmount = preferenceAmount / random.nextInt(1, preferenceAmount);
            } else {
                int maxResourceWithoutValidPreference = 20;
                if (actualEmpty >= maxResourceWithoutValidPreference) {
                    randomAmount = actualEmpty / random.nextInt(actualEmpty - maxResourceWithoutValidPreference, actualEmpty);
                } else {
                    randomAmount = actualEmpty / random.nextInt(1, actualEmpty);
                }
            }
            mapOfResourcesAndAmount.put(planet.resources().get(i).symbol(), randomAmount);
        }
        return mapOfResourcesAndAmount;
    }

    private void findValidTerrainForResource(String[][] planetTerrains, int maxPlanetLength, String resourceSymbol, Coordinate checkCoordinate) {
        boolean isResourcePlaced = false;
        do {
            Set<Coordinate> possibleCoordinates = coordinateCreator.create(checkCoordinate, maxPlanetLength);
            for (Coordinate coordinate : possibleCoordinates) {
                String actualTerrain = planetTerrains[coordinate.y()][coordinate.x()];
                if (actualTerrain.equals(Constants.EMPTY_SYMBOL)) {
                    planetTerrains[coordinate.y()][coordinate.x()] = resourceSymbol;
                    isResourcePlaced = true;
                }
            }
            int randomNext = random.nextInt(possibleCoordinates.size() - 1);
            checkCoordinate = (Coordinate) possibleCoordinates.toArray()[randomNext];
        } while (!isResourcePlaced);
    }

    private int freeSpaces(String[][] planetTerrains) {
        return (int) Arrays.stream(planetTerrains)
                .flatMap(Arrays::stream)
                .filter(terrain -> terrain.equals(Constants.EMPTY_SYMBOL))
                .count();
    }

    private Coordinate getStartCoordinate(String[][] planetTerrains, int maxPlanetLength, String areaSymbol) {
        Coordinate startCoordinate = getRandomCoordinate(maxPlanetLength);
        while (!planetTerrains[startCoordinate.y()][startCoordinate.x()].equals(areaSymbol)) {
            startCoordinate = getRandomCoordinate(maxPlanetLength);
        }
        return startCoordinate;
    }

    private Coordinate getRandomCoordinate(int maxPlanetLength) {
        int randomY = random.nextInt(maxPlanetLength);
        int randomX = random.nextInt(maxPlanetLength);
        return new Coordinate(randomY, randomX);
    }
}