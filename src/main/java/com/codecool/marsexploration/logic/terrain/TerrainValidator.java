package com.codecool.marsexploration.logic.terrain;

import com.codecool.marsexploration.data.Area;
import com.codecool.marsexploration.ui.Display;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class TerrainValidator {

    private final Map<Integer, Integer> areaTerrainsCoordinatesMap = new HashMap<>();
    private final Map<Integer, Integer> possibleNextTerrainsCoordinatesMap = new HashMap<>();
    private final Map<Integer, Integer> validNextTerrainsCoordinatesMap = new HashMap<>();
    private final Map<Integer, Map<Integer, Integer>> steps = new HashMap<>();
    private final Map<Integer, Integer> stepCoordinate = new HashMap<>();

    public Map<Integer, Integer> getXY(String[][] planetTerrains, Area area, Random random, Display display) {
        int maxPlanetLength = planetTerrains.length;
        int startY = random.nextInt(1, maxPlanetLength - 1);
        int startX = random.nextInt(1, maxPlanetLength - 1);
        int nextY = -1;
        int nextX = -1;
        int counterTrysFindStartCoordinates = 0;
        int trysToFindStartCoordinates = planetTerrains.length;
        boolean isPossibleToProvideArea = true;
        while (counterTrysFindStartCoordinates <= trysToFindStartCoordinates) {
            getValidStartYXCoordinate(planetTerrains, startY, startX, random);
            possibleNextTerrainsCoordinatesMap.clear();
            getPossibleNextTerrainsCoordinatesMap(maxPlanetLength, startY, startX);
            int possibilities = possibleNextTerrainsCoordinatesMap.size();
            if (possibilities < 8) {
                counterTrysFindStartCoordinates++;
            }
            isPossibleToProvideArea = false;
        }
        display.errorMessage("It is not possible to find a starting point for the area");

        while (isPossibleToProvideArea) {
            int areaSizeCounter = 0;
            while (areaSizeCounter < area.size()) {
                if (nextY != -1 && nextX != -1) {
                    getPossibleNextTerrainsCoordinatesMap(maxPlanetLength, nextY, nextX);
                    getValidNextTerrainsCoordinatesMap(planetTerrains);
                }
                if (!steps.isEmpty() && validNextTerrainsCoordinatesMap.isEmpty()) {
                    int bestPossibility = 8;
                    while (bestPossibility >= 0) {
                        for (Map.Entry<Integer, Map<Integer, Integer>> tryBestOldXY : steps.entrySet()) {
                            if (tryBestOldXY.getKey() == bestPossibility) {
                                for (Map.Entry<Integer, Integer> coordinate : tryBestOldXY.getValue().entrySet()) {
                                    nextY = coordinate.getKey();
                                    nextX = coordinate.getValue();
                                }
                            }
                        }
                        bestPossibility--;
                    }
                    isPossibleToProvideArea = false;
                }
                int randomNextCoordinate = random.nextInt(validNextTerrainsCoordinatesMap.size());
                nextY = (int) validNextTerrainsCoordinatesMap.keySet().toArray()[randomNextCoordinate];
                nextX = validNextTerrainsCoordinatesMap.get(nextY);
                areaTerrainsCoordinatesMap.put(nextY, nextX);
                stepCoordinate.put(nextY, nextX);
                steps.put(validNextTerrainsCoordinatesMap.size(), stepCoordinate);
                stepCoordinate.clear();
                possibleNextTerrainsCoordinatesMap.clear();
                validNextTerrainsCoordinatesMap.clear();
                areaSizeCounter++;
            }
        }
        return areaTerrainsCoordinatesMap;
    }

    private void getValidNextTerrainsCoordinatesMap(String[][] planetTerrains) {
        for (Map.Entry<Integer, Integer> next : possibleNextTerrainsCoordinatesMap.entrySet()) {
            for (Map.Entry<Integer, Integer> past : areaTerrainsCoordinatesMap.entrySet()) {
                if (planetTerrains[next.getKey()][next.getValue()].equals(" ")
                        && next.getKey() != past.getKey() && next.getValue() != past.getValue()) {
                    validNextTerrainsCoordinatesMap.put(next.getKey(), next.getValue());
                }
            }
        }
    }

    private void getValidStartYXCoordinate(String[][] planetTerrains, int startY, int startX, Random random) {
        while (!planetTerrains[startY][startX].equals(" ")) {
            startY = random.nextInt(1, planetTerrains.length - 1);
            startX = random.nextInt(1, planetTerrains.length - 1);
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
        if (yCoordinate > 0 && yCoordinate < maxPlanetLength && xCoordinate > 0 && xCoordinate < maxPlanetLength) {
            possibleNextTerrainsCoordinatesMap.put(yCoordinate - 1, xCoordinate);
            possibleNextTerrainsCoordinatesMap.put(yCoordinate - 1, xCoordinate - 1);
            possibleNextTerrainsCoordinatesMap.put(yCoordinate - 1, xCoordinate + 1);
            possibleNextTerrainsCoordinatesMap.put(yCoordinate + 1, xCoordinate);
            possibleNextTerrainsCoordinatesMap.put(yCoordinate + 1, xCoordinate - 1);
            possibleNextTerrainsCoordinatesMap.put(yCoordinate + 1, xCoordinate + 1);
            possibleNextTerrainsCoordinatesMap.put(yCoordinate, xCoordinate - 1);
            possibleNextTerrainsCoordinatesMap.put(yCoordinate, xCoordinate + 1);
        }
    }

    private void fromLeftUpCorner(int maxPlanetLength, int yCoordinate, int xCoordinate) {
        if (yCoordinate == 0 && xCoordinate == 0) {
            possibleNextTerrainsCoordinatesMap.put(0, 1);
            possibleNextTerrainsCoordinatesMap.put(1, 1);
            possibleNextTerrainsCoordinatesMap.put(1, 0);
            possibleNextTerrainsCoordinatesMap.put(0, maxPlanetLength);
            possibleNextTerrainsCoordinatesMap.put(1, maxPlanetLength);
            possibleNextTerrainsCoordinatesMap.put(maxPlanetLength, 0);
            possibleNextTerrainsCoordinatesMap.put(maxPlanetLength, 1);
            possibleNextTerrainsCoordinatesMap.put(maxPlanetLength, maxPlanetLength);
        }
    }

    private void fromFirstEdge(int maxPlanetLength, int yCoordinate, int xCoordinate) {
        if (yCoordinate == 0) {
            possibleNextTerrainsCoordinatesMap.put(0, xCoordinate - 1);
            possibleNextTerrainsCoordinatesMap.put(0, xCoordinate + 1);
            possibleNextTerrainsCoordinatesMap.put(1, xCoordinate - 1);
            possibleNextTerrainsCoordinatesMap.put(1, xCoordinate);
            possibleNextTerrainsCoordinatesMap.put(1, xCoordinate + 1);
            possibleNextTerrainsCoordinatesMap.put(maxPlanetLength, xCoordinate - 1);
            possibleNextTerrainsCoordinatesMap.put(maxPlanetLength, xCoordinate);
            possibleNextTerrainsCoordinatesMap.put(maxPlanetLength, xCoordinate + 1);
        }
    }

    private void fromRightUpCorner(int maxPlanetLength, int yCoordinate, int xCoordinate) {
        if (yCoordinate == 0 && xCoordinate == maxPlanetLength) {
            possibleNextTerrainsCoordinatesMap.put(0, maxPlanetLength - 1);
            possibleNextTerrainsCoordinatesMap.put(0, 0);
            possibleNextTerrainsCoordinatesMap.put(1, maxPlanetLength - 1);
            possibleNextTerrainsCoordinatesMap.put(1, maxPlanetLength);
            possibleNextTerrainsCoordinatesMap.put(1, 0);
            possibleNextTerrainsCoordinatesMap.put(maxPlanetLength, maxPlanetLength);
            possibleNextTerrainsCoordinatesMap.put(maxPlanetLength, maxPlanetLength - 1);
            possibleNextTerrainsCoordinatesMap.put(maxPlanetLength, 0);
        }
    }

    private void fromRightEdge(int maxPlanetLength, int yCoordinate, int xCoordinate) {
        if (xCoordinate == maxPlanetLength) {
            possibleNextTerrainsCoordinatesMap.put(yCoordinate, 0);
            possibleNextTerrainsCoordinatesMap.put(yCoordinate - 1, 0);
            possibleNextTerrainsCoordinatesMap.put(yCoordinate + 1, 0);
            possibleNextTerrainsCoordinatesMap.put(yCoordinate, maxPlanetLength - 1);
            possibleNextTerrainsCoordinatesMap.put(yCoordinate - 1, maxPlanetLength - 1);
            possibleNextTerrainsCoordinatesMap.put(yCoordinate + 1, maxPlanetLength - 1);
            possibleNextTerrainsCoordinatesMap.put(yCoordinate - 1, maxPlanetLength);
            possibleNextTerrainsCoordinatesMap.put(yCoordinate + 1, maxPlanetLength);
        }
    }

    private void fromRightDownCorner(int maxPlanetLength, int yCoordinate, int xCoordinate) {
        if (yCoordinate == maxPlanetLength && xCoordinate == maxPlanetLength) {
            possibleNextTerrainsCoordinatesMap.put(maxPlanetLength, maxPlanetLength - 1);
            possibleNextTerrainsCoordinatesMap.put(maxPlanetLength, maxPlanetLength + 1);
            possibleNextTerrainsCoordinatesMap.put(maxPlanetLength - 1, maxPlanetLength);
            possibleNextTerrainsCoordinatesMap.put(maxPlanetLength - 1, maxPlanetLength - 1);
            possibleNextTerrainsCoordinatesMap.put(maxPlanetLength - 1, maxPlanetLength + 1);
            possibleNextTerrainsCoordinatesMap.put(0, maxPlanetLength);
            possibleNextTerrainsCoordinatesMap.put(0, maxPlanetLength - 1);
            possibleNextTerrainsCoordinatesMap.put(0, 0);
        }
    }

    private void fromBottomEdge(int maxPlanetLength, int yCoordinate, int xCoordinate) {
        if (yCoordinate == maxPlanetLength) {
            possibleNextTerrainsCoordinatesMap.put(maxPlanetLength, xCoordinate - 1);
            possibleNextTerrainsCoordinatesMap.put(maxPlanetLength, xCoordinate + 1);
            possibleNextTerrainsCoordinatesMap.put(maxPlanetLength - 1, xCoordinate - 1);
            possibleNextTerrainsCoordinatesMap.put(maxPlanetLength - 1, xCoordinate);
            possibleNextTerrainsCoordinatesMap.put(maxPlanetLength - 1, xCoordinate + 1);
            possibleNextTerrainsCoordinatesMap.put(0, xCoordinate - 1);
            possibleNextTerrainsCoordinatesMap.put(0, xCoordinate);
            possibleNextTerrainsCoordinatesMap.put(0, xCoordinate + 1);
        }
    }

    private void fromLeftDownCorner(int maxPlanetLength, int yCoordinate, int xCoordinate) {
        if (yCoordinate == maxPlanetLength && xCoordinate == 0) {
            possibleNextTerrainsCoordinatesMap.put(maxPlanetLength, maxPlanetLength);
            possibleNextTerrainsCoordinatesMap.put(maxPlanetLength, 1);
            possibleNextTerrainsCoordinatesMap.put(maxPlanetLength - 1, 0);
            possibleNextTerrainsCoordinatesMap.put(maxPlanetLength - 1, 1);
            possibleNextTerrainsCoordinatesMap.put(maxPlanetLength - 1, maxPlanetLength);
            possibleNextTerrainsCoordinatesMap.put(0, 0);
            possibleNextTerrainsCoordinatesMap.put(0, 1);
            possibleNextTerrainsCoordinatesMap.put(0, maxPlanetLength);
        }
    }

    private void fromLeftEdge(int maxPlanetLength, int yCoordinate, int xCoordinate) {
        if (xCoordinate == 0) {
            possibleNextTerrainsCoordinatesMap.put(yCoordinate - 1, 1);
            possibleNextTerrainsCoordinatesMap.put(yCoordinate, 1);
            possibleNextTerrainsCoordinatesMap.put(yCoordinate + 1, 1);
            possibleNextTerrainsCoordinatesMap.put(yCoordinate - 1, 0);
            possibleNextTerrainsCoordinatesMap.put(yCoordinate + 1, 0);
            possibleNextTerrainsCoordinatesMap.put(yCoordinate - 1, maxPlanetLength);
            possibleNextTerrainsCoordinatesMap.put(yCoordinate, maxPlanetLength);
            possibleNextTerrainsCoordinatesMap.put(yCoordinate + 1, maxPlanetLength);
        }
    }
}