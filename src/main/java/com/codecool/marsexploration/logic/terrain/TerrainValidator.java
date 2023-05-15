package com.codecool.marsexploration.logic.terrain;

import com.codecool.marsexploration.data.Area;
import com.codecool.marsexploration.data.Constants;
import com.codecool.marsexploration.data.Coordinate;
import com.codecool.marsexploration.logic.validator.CoordinateValidator;
import com.codecool.marsexploration.ui.Display;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class TerrainValidator {
    private final Display display;
    private final Random random;
    private final CoordinateValidator coordinateValidator;
    private final Set<Coordinate> areaCoordinates = new HashSet<>();
    private final Set<Coordinate> possibleCoordinatesAroundCheckCoordinate = new HashSet<>();
    private final Set<Coordinate> validCoordinatesAroundCheckCoordinate = new HashSet<>();
    private Coordinate checkCoordinate;
    private boolean isPossibleToProvideArea = true;

    public TerrainValidator(Display display, Random random, CoordinateValidator coordinateValidator) {
        this.display = display;
        this.random = random;
        this.coordinateValidator = coordinateValidator;
    }

    public Set<Coordinate> getAreaCoordinate(String[][] planetTerrains, Area area) {
        int maxPlanetLength = planetTerrains.length - 1;
        getValidStartCoordinate(planetTerrains, maxPlanetLength, area);
        addValidCoordinateToAreaCoordinates(planetTerrains, area, maxPlanetLength);
        return areaCoordinates;
    }

    private void getValidStartCoordinate(String[][] areaTerrain, int maxPlanetLength, Area area) {
        int trys = 0;
        do {
            int startY = random.nextInt(maxPlanetLength);
            int startX = random.nextInt(maxPlanetLength);
            if (areaTerrain[startY][startX].equals(Constants.EMPTY_SYMBOL)) {
                checkCoordinate = new Coordinate(startY, startX);
                areaCoordinates.add(checkCoordinate);
            }
            trys++;
        } while (checkCoordinate == null && trys < maxPlanetLength);
        if (checkCoordinate == null) {
            display.errorMessage("Can't find valid Start Coordinate for " + area.name() + " after " + trys + "trys");
            isPossibleToProvideArea = false;
        }
    }

    private void addValidCoordinateToAreaCoordinates(String[][] planetTerrains, Area area, int maxPlanetLength) {
        while (isPossibleToProvideArea && areaCoordinates.size() < area.amountOfSymbols()) {
            getValidCoordinates(planetTerrains, maxPlanetLength, area);
            Coordinate randomValidCoordinate = validCoordinatesAroundCheckCoordinate.stream()
                    .skip(random.nextInt(validCoordinatesAroundCheckCoordinate.size()))
                    .findFirst().orElse(null);
            assert randomValidCoordinate != null;
            checkCoordinate = new Coordinate(randomValidCoordinate.y(), randomValidCoordinate.x());
            areaCoordinates.add(checkCoordinate);
        }
    }

    private void getValidCoordinates(String[][] areaTerrain, int maxPlanetLength, Area area) {
        Set<Coordinate> checkedCoordinates = new HashSet<>();
        Set<Coordinate> restOfCheckedCoordinate = new HashSet<>(areaCoordinates);
        do {
            clearAllMapsUsedToValidate();
            Coordinate randomCoordinateToValidate = restOfCheckedCoordinate.stream()
                    .skip(random.nextInt(restOfCheckedCoordinate.size()))
                    .findFirst().orElse(null);
            assert randomCoordinateToValidate != null;
            checkCoordinate = new Coordinate(randomCoordinateToValidate.y(), randomCoordinateToValidate.x());
            checkedCoordinates.add(checkCoordinate);
            restOfCheckedCoordinate.removeAll(checkedCoordinates);
            coordinateValidator.getPossibleCoordinatesAroundCheckCoordinate(possibleCoordinatesAroundCheckCoordinate, maxPlanetLength, checkCoordinate);
            getValidCoordinatesFromPossibility(areaTerrain);
        } while (validCoordinatesAroundCheckCoordinate.isEmpty() && !restOfCheckedCoordinate.isEmpty());
        if (validCoordinatesAroundCheckCoordinate.isEmpty()) {
            isPossibleToProvideArea = false;
            display.errorMessage("Can't find valid Coordinate for " + area.name() + " after " + areaCoordinates.size() + "trys");
        }
    }

    private void getValidCoordinatesFromPossibility(String[][] areaTerrain) {
        for (Coordinate possibleCoordinate : possibleCoordinatesAroundCheckCoordinate) {
            String actualTerrain = areaTerrain[possibleCoordinate.y()][possibleCoordinate.x()];
            if (actualTerrain.equals(Constants.EMPTY_SYMBOL)) {
                validCoordinatesAroundCheckCoordinate.add(possibleCoordinate);
            }
        }
        validCoordinatesAroundCheckCoordinate.removeAll(areaCoordinates);
    }

    private void clearAllMapsUsedToValidate() {
        possibleCoordinatesAroundCheckCoordinate.clear();
        validCoordinatesAroundCheckCoordinate.clear();
    }
}