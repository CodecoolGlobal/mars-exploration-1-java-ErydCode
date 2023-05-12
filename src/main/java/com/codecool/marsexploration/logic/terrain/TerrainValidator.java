package com.codecool.marsexploration.logic.terrain;

import com.codecool.marsexploration.data.Area;
import com.codecool.marsexploration.ui.Display;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class TerrainValidator {

    private final Map<Integer, Integer> areaTerrainsCoordinatesMap = new HashMap<>();
    private final Map<Integer, Map<Integer, Integer>> possibleNextTerrainsCoordinatesMap = new HashMap<>();
    private final Map<Integer, Integer> actualPossibleCoordinates = new HashMap<>();
    private final Map<Integer, Integer> validNextTerrainsCoordinatesMap = new HashMap<>();
    private final Map<Integer, Map<Integer, Integer>> steps = new HashMap<>();
    private final Map<Integer, Integer> stepCoordinate = new HashMap<>();

    public Map<Integer, Integer> getXY(String[][] planetTerrains, Area area, Random random, Display display) {
        System.out.println(Arrays.deepToString(planetTerrains));
        int maxPlanetLength = planetTerrains.length;
        System.out.println(maxPlanetLength);
        int startY = random.nextInt(maxPlanetLength);
        int startX = random.nextInt(maxPlanetLength);
        System.out.println(startY + " " + startX);
        int nextY = -1;
        int nextX = -1;
        int counterTrysFindStartCoordinates = 0;
        boolean isPossibleToProvideArea = true;
        boolean isStartPointFound = false;
        while (!isStartPointFound) {
            //counterTrysFindStartCoordinates < 5
            getValidStartYXCoordinate(planetTerrains, maxPlanetLength, startY, startX, random);
            System.out.println(startY + " " + startX);

            possibleNextTerrainsCoordinatesMap.clear();
            getPossibleNextTerrainsCoordinatesMap(maxPlanetLength, startY, startX);
            System.out.println("PossibleNext: " + possibleNextTerrainsCoordinatesMap);
            int possibilities = possibleNextTerrainsCoordinatesMap.size();
            System.out.println("PossibleNext: " + possibilities);
            if(possibilities == 8){
                isStartPointFound = true;
            } else if (possibilities < 8) {
                counterTrysFindStartCoordinates++;
            } else {
                isPossibleToProvideArea = false;
            }
        }
        if (!isPossibleToProvideArea) {
            display.errorMessage("It is not possible to find a starting point for the area");
        }

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
        for (Map.Entry<Integer, Map<Integer, Integer>> set : possibleNextTerrainsCoordinatesMap.entrySet()) {
            for (Map.Entry<Integer, Integer> next : set.getValue().entrySet()) {
                for (Map.Entry<Integer, Integer> past : areaTerrainsCoordinatesMap.entrySet()) {
                    if (planetTerrains[next.getKey()][next.getValue()].equals(" ")
                            && !next.getKey().equals(past.getKey()) && !next.getValue().equals(past.getValue())) {
                        validNextTerrainsCoordinatesMap.put(next.getKey(), next.getValue());
                    }
                }
            }
        }
    }

    private void getValidStartYXCoordinate(String[][] planetTerrains, int maxPlanetLength, int startY, int startX, Random random) {
        while (!planetTerrains[startY][startX].equals(" ")) {
            startY = random.nextInt(1, maxPlanetLength);
            startX = random.nextInt(1, maxPlanetLength);
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
            //actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(yCoordinate - 1, xCoordinate - 1);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            //actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(yCoordinate - 1, xCoordinate + 1);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            //actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(yCoordinate + 1, xCoordinate);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            //actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(yCoordinate + 1, xCoordinate - 1);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            //actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(yCoordinate + 1, xCoordinate + 1);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            //actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(yCoordinate, xCoordinate - 1);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            //actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(yCoordinate, xCoordinate + 1);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            //actualPossibleCoordinates.clear();
        }
    }

    private void fromLeftUpCorner(int maxPlanetLength, int yCoordinate, int xCoordinate) {
        int counter = 1;
        if (yCoordinate == 0 && xCoordinate == 0) {
            actualPossibleCoordinates.put(0, 1);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            //actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(1, 1);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            //actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(1, 0);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            //actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(0, maxPlanetLength);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            //actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(1, maxPlanetLength);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            //actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(maxPlanetLength, 0);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            //actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(maxPlanetLength, 1);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            //actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(maxPlanetLength, maxPlanetLength);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            //actualPossibleCoordinates.clear();
        }
    }

    private void fromFirstEdge(int maxPlanetLength, int yCoordinate, int xCoordinate) {
        int counter = 1;
        if (yCoordinate == 0) {
            actualPossibleCoordinates.put(0, xCoordinate - 1);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            //actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(0, xCoordinate + 1);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            //actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(1, xCoordinate - 1);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            //actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(1, xCoordinate);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            //actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(1, xCoordinate + 1);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            //actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(maxPlanetLength, xCoordinate - 1);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            //actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(maxPlanetLength, xCoordinate);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            //actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(maxPlanetLength, xCoordinate + 1);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            //actualPossibleCoordinates.clear();
        }
    }

    private void fromRightUpCorner(int maxPlanetLength, int yCoordinate, int xCoordinate) {
        int counter = 1;
        if (yCoordinate == 0 && xCoordinate == maxPlanetLength) {
            actualPossibleCoordinates.put(0, maxPlanetLength - 1);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            //actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(0, 0);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            //actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(1, maxPlanetLength - 1);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            //actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(1, maxPlanetLength);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            //actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(1, 0);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            //actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(maxPlanetLength, maxPlanetLength);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            //actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(maxPlanetLength, maxPlanetLength - 1);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            //actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(maxPlanetLength, 0);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            //actualPossibleCoordinates.clear();
        }
    }

    private void fromRightEdge(int maxPlanetLength, int yCoordinate, int xCoordinate) {
        int counter = 1;
        if (xCoordinate == maxPlanetLength) {
            actualPossibleCoordinates.put(yCoordinate, 0);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            //actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(yCoordinate - 1, 0);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            //actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(yCoordinate + 1, 0);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            //actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(yCoordinate, maxPlanetLength - 1);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            //actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(yCoordinate - 1, maxPlanetLength - 1);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            //actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(yCoordinate + 1, maxPlanetLength - 1);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            //actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(yCoordinate - 1, maxPlanetLength);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            //actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(yCoordinate + 1, maxPlanetLength);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            //actualPossibleCoordinates.clear();
        }
    }

    private void fromRightDownCorner(int maxPlanetLength, int yCoordinate, int xCoordinate) {
        int counter = 1;
        if (yCoordinate == maxPlanetLength && xCoordinate == maxPlanetLength) {
            actualPossibleCoordinates.put(maxPlanetLength, maxPlanetLength - 1);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            //actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(maxPlanetLength, maxPlanetLength + 1);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            //actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(maxPlanetLength - 1, maxPlanetLength);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            //actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(maxPlanetLength - 1, maxPlanetLength - 1);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            //actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(maxPlanetLength - 1, maxPlanetLength + 1);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            //actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(0, maxPlanetLength);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            //actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(0, maxPlanetLength - 1);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            //actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(0, 0);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            //actualPossibleCoordinates.clear();
        }
    }

    private void fromBottomEdge(int maxPlanetLength, int yCoordinate, int xCoordinate) {
        int counter = 1;
        if (yCoordinate == maxPlanetLength) {
            actualPossibleCoordinates.put(maxPlanetLength, xCoordinate - 1);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            //actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(maxPlanetLength, xCoordinate + 1);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            //actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(maxPlanetLength - 1, xCoordinate - 1);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            //actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(maxPlanetLength - 1, xCoordinate);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            //actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(maxPlanetLength - 1, xCoordinate + 1);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            //actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(0, xCoordinate - 1);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            //actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(0, xCoordinate);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            //actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(0, xCoordinate + 1);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            //actualPossibleCoordinates.clear();
        }
    }

    private void fromLeftDownCorner(int maxPlanetLength, int yCoordinate, int xCoordinate) {
        int counter = 1;
        if (yCoordinate == maxPlanetLength && xCoordinate == 0) {
            actualPossibleCoordinates.put(maxPlanetLength, maxPlanetLength);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            //actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(maxPlanetLength, 1);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            //actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(maxPlanetLength - 1, 0);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            //actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(maxPlanetLength - 1, 1);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            //actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(maxPlanetLength - 1, maxPlanetLength);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            //actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(0, 0);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            //actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(0, 1);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            //actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(0, maxPlanetLength);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            //actualPossibleCoordinates.clear();
        }
    }

    private void fromLeftEdge(int maxPlanetLength, int yCoordinate, int xCoordinate) {
        int counter = 1;
        if (xCoordinate == 0) {
            actualPossibleCoordinates.put(yCoordinate - 1, 1);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            //actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(yCoordinate, 1);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            //actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(yCoordinate + 1, 1);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            //actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(yCoordinate - 1, 0);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            //actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(yCoordinate + 1, 0);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            //actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(yCoordinate - 1, maxPlanetLength);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            //actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(yCoordinate, maxPlanetLength);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            //actualPossibleCoordinates.clear();
            counter++;
            actualPossibleCoordinates.put(yCoordinate + 1, maxPlanetLength);
            possibleNextTerrainsCoordinatesMap.put(counter, actualPossibleCoordinates);
            //actualPossibleCoordinates.clear();
        }
    }
}