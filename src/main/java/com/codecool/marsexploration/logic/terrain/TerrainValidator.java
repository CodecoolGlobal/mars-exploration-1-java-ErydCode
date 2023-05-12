package com.codecool.marsexploration.logic.terrain;

import com.codecool.marsexploration.data.Area;
import com.codecool.marsexploration.ui.Display;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class TerrainValidator {

    private final Map<Integer, Map<Integer, Integer>> areaTerrainsCoordinatesMap = new HashMap<>();
    private final Map<Integer, Map<Integer, Integer>> possibleNextTerrainsCoordinatesMap = new HashMap<>();
    private final Map<Integer, Map<Integer, Integer>> validNextTerrainsCoordinatesMap = new HashMap<>();
    private final Map<Integer, Integer> actualPossibleCoordinates = new HashMap<>();

    public Map<Integer, Map<Integer, Integer>> getXY(String[][] planetTerrains, Area area, Random random, Display display) {
        int maxPlanetLength = planetTerrains.length;
        int startY = random.nextInt(maxPlanetLength);
        int startX = random.nextInt(maxPlanetLength);
        actualPossibleCoordinates.put(startY, startX);
        areaTerrainsCoordinatesMap.put(0, actualPossibleCoordinates);
        System.out.println(areaTerrainsCoordinatesMap);
        int nextY = startY;
        int nextX = startX;
        boolean isPossibleToProvideArea = true;

        while (isPossibleToProvideArea) {
            clearAllMapsUsedToValidate();
            int areaSizeCounter = 1;
            while (areaSizeCounter < area.size() + 1) {
                clearAllMapsUsedToValidate();
                getPossibleNextTerrainsCoordinatesMap(maxPlanetLength, nextY, nextX);
                System.out.println(possibleNextTerrainsCoordinatesMap);
                getValidNextTerrainsCoordinatesMap(planetTerrains);
                System.out.println(validNextTerrainsCoordinatesMap);
                System.out.println(validNextTerrainsCoordinatesMap.isEmpty());
                int indexLastSteps = 0;
                while (validNextTerrainsCoordinatesMap.isEmpty()) {
                    possibleNextTerrainsCoordinatesMap.clear();
                    validNextTerrainsCoordinatesMap.clear();
                    int pastSteps = (int) areaTerrainsCoordinatesMap.keySet().toArray()[indexLastSteps];
                    Map<Integer, Integer> pastCoordinate = areaTerrainsCoordinatesMap.get(pastSteps);
                    for (Map.Entry<Integer, Integer> lastStepSet : pastCoordinate.entrySet()) {
                        nextY = lastStepSet.getKey();
                        nextX = lastStepSet.getValue();
                    }
                    getPossibleNextTerrainsCoordinatesMap(maxPlanetLength, nextY, nextX);
                    getValidNextTerrainsCoordinatesMap(planetTerrains);
                    System.out.println(validNextTerrainsCoordinatesMap);
                    indexLastSteps++;
                    if (areaTerrainsCoordinatesMap.size() < indexLastSteps) {
                        isPossibleToProvideArea = false;
                    }
                }
                int randomNextCoordinate = random.nextInt(validNextTerrainsCoordinatesMap.size());

            }
            /*
            nextY = (int) validNextTerrainsCoordinatesMap.keySet().toArray()[randomNextCoordinate];
            nextX = validNextTerrainsCoordinatesMap.get(nextY);
            areaTerrainsCoordinatesMap.put(nextY, nextX);
            actualPossibleCoordinates.put(nextY, nextX);
            steps.put(validNextTerrainsCoordinatesMap.size(), stepCoordinate);
            possibleNextTerrainsCoordinatesMap.clear();
            validNextTerrainsCoordinatesMap.clear();
            areaSizeCounter++;
            */
        }
        return areaTerrainsCoordinatesMap;
    }

    private void getValidNextTerrainsCoordinatesMap(String[][] planetTerrains) {
        int counter = 0;
        for (Map.Entry<Integer, Map<Integer, Integer>> nextMain : possibleNextTerrainsCoordinatesMap.entrySet()) {
            counter++;
            for (Map.Entry<Integer, Integer> next : nextMain.getValue().entrySet()) {
                for (Map.Entry<Integer, Map<Integer, Integer>> pastMain : areaTerrainsCoordinatesMap.entrySet()) {
                    for (Map.Entry<Integer, Integer> past : pastMain.getValue().entrySet()) {
                        if (planetTerrains[next.getKey()][next.getValue()].equals(" ")
                                && !next.getKey().equals(past.getKey()) && !next.getValue().equals(past.getValue())) {
                            actualPossibleCoordinates.put(next.getKey(), next.getValue());
                            validNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
                            actualPossibleCoordinates.clear();
                        }
                    }
                }
            }
        }
    }

    private void getPossibleNextTerrainsCoordinatesMap(int maxPlanetLength, int yCoordinate, int xCoordinate) {
        fromAllTerrainsExceptEdges(maxPlanetLength, yCoordinate, xCoordinate);
        fromLeftUpCorner(maxPlanetLength, yCoordinate, xCoordinate);
        fromFirstEdge(maxPlanetLength, yCoordinate, xCoordinate);
        fromRightUpCorner(maxPlanetLength, yCoordinate, xCoordinate);
        fromRightEdge(maxPlanetLength, yCoordinate, xCoordinate);
        fromLeftDownCorner(maxPlanetLength, yCoordinate, xCoordinate);
        fromBottomEdge(maxPlanetLength, yCoordinate, xCoordinate);
        fromRightDownCorner(maxPlanetLength, yCoordinate, xCoordinate);
        fromLeftEdge(maxPlanetLength, yCoordinate, xCoordinate);
    }

    private void fromAllTerrainsExceptEdges(int maxPlanetLength, int yCoordinate, int xCoordinate) {
        int counter = 1;
        if (yCoordinate > 0 && yCoordinate < maxPlanetLength && xCoordinate > 0 && xCoordinate < maxPlanetLength) {
            actualPossibleCoordinates.put(yCoordinate - 1, xCoordinate);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(yCoordinate - 1, xCoordinate - 1);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(yCoordinate - 1, xCoordinate + 1);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(yCoordinate + 1, xCoordinate);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(yCoordinate + 1, xCoordinate - 1);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(yCoordinate + 1, xCoordinate + 1);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(yCoordinate, xCoordinate - 1);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(yCoordinate, xCoordinate + 1);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            actualPossibleCoordinates.clear();
        }
    }

    private void fromLeftUpCorner(int maxPlanetLength, int yCoordinate, int xCoordinate) {
        int counter = 1;
        if (yCoordinate == 0 && xCoordinate == 0) {
            actualPossibleCoordinates.put(0, 1);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(1, 1);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(1, 0);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(0, maxPlanetLength);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(1, maxPlanetLength);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(maxPlanetLength, 0);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(maxPlanetLength, 1);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(maxPlanetLength, maxPlanetLength);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            actualPossibleCoordinates.clear();
        }
    }

    private void fromFirstEdge(int maxPlanetLength, int yCoordinate, int xCoordinate) {
        int counter = 1;
        if (yCoordinate == 0) {
            actualPossibleCoordinates.put(0, xCoordinate - 1);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(0, xCoordinate + 1);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(1, xCoordinate - 1);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(1, xCoordinate);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(1, xCoordinate + 1);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(maxPlanetLength, xCoordinate - 1);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(maxPlanetLength, xCoordinate);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(maxPlanetLength, xCoordinate + 1);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            actualPossibleCoordinates.clear();
        }
    }

    private void fromRightUpCorner(int maxPlanetLength, int yCoordinate, int xCoordinate) {
        int counter = 1;
        if (yCoordinate == 0 && xCoordinate == maxPlanetLength) {
            actualPossibleCoordinates.put(0, maxPlanetLength - 1);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(0, 0);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(1, maxPlanetLength - 1);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(1, maxPlanetLength);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(1, 0);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(maxPlanetLength, maxPlanetLength);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(maxPlanetLength, maxPlanetLength - 1);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(maxPlanetLength, 0);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            actualPossibleCoordinates.clear();
        }
    }

    private void fromRightEdge(int maxPlanetLength, int yCoordinate, int xCoordinate) {
        int counter = 1;
        if (xCoordinate == maxPlanetLength) {
            actualPossibleCoordinates.put(yCoordinate, 0);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(yCoordinate - 1, 0);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(yCoordinate + 1, 0);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(yCoordinate, maxPlanetLength - 1);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(yCoordinate - 1, maxPlanetLength - 1);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(yCoordinate + 1, maxPlanetLength - 1);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(yCoordinate - 1, maxPlanetLength);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(yCoordinate + 1, maxPlanetLength);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            actualPossibleCoordinates.clear();
        }
    }

    private void fromRightDownCorner(int maxPlanetLength, int yCoordinate, int xCoordinate) {
        int counter = 1;
        if (yCoordinate == maxPlanetLength && xCoordinate == maxPlanetLength) {
            actualPossibleCoordinates.put(maxPlanetLength, maxPlanetLength - 1);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(maxPlanetLength, maxPlanetLength + 1);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(maxPlanetLength - 1, maxPlanetLength);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(maxPlanetLength - 1, maxPlanetLength - 1);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(maxPlanetLength - 1, maxPlanetLength + 1);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(0, maxPlanetLength);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(0, maxPlanetLength - 1);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(0, 0);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            actualPossibleCoordinates.clear();
        }
    }

    private void fromBottomEdge(int maxPlanetLength, int yCoordinate, int xCoordinate) {
        int counter = 1;
        if (yCoordinate == maxPlanetLength) {
            actualPossibleCoordinates.put(maxPlanetLength, xCoordinate - 1);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(maxPlanetLength, xCoordinate + 1);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(maxPlanetLength - 1, xCoordinate - 1);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(maxPlanetLength - 1, xCoordinate);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(maxPlanetLength - 1, xCoordinate + 1);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(0, xCoordinate - 1);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(0, xCoordinate);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(0, xCoordinate + 1);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            actualPossibleCoordinates.clear();
        }
    }

    private void fromLeftDownCorner(int maxPlanetLength, int yCoordinate, int xCoordinate) {
        int counter = 1;
        if (yCoordinate == maxPlanetLength && xCoordinate == 0) {
            actualPossibleCoordinates.put(maxPlanetLength, maxPlanetLength);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(maxPlanetLength, 1);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(maxPlanetLength - 1, 0);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(maxPlanetLength - 1, 1);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(maxPlanetLength - 1, maxPlanetLength);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(0, 0);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(0, 1);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(0, maxPlanetLength);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            actualPossibleCoordinates.clear();
        }
    }

    private void fromLeftEdge(int maxPlanetLength, int yCoordinate, int xCoordinate) {
        int counter = 1;
        if (xCoordinate == 0) {
            actualPossibleCoordinates.put(yCoordinate - 1, 1);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(yCoordinate, 1);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(yCoordinate + 1, 1);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(yCoordinate - 1, 0);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(yCoordinate + 1, 0);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(yCoordinate - 1, maxPlanetLength);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(yCoordinate, maxPlanetLength);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(yCoordinate + 1, maxPlanetLength);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            actualPossibleCoordinates.clear();
        }
    }

    private void clearAllMapsUsedToValidate() {
        possibleNextTerrainsCoordinatesMap.clear();
        validNextTerrainsCoordinatesMap.clear();
        actualPossibleCoordinates.clear();
    }
}