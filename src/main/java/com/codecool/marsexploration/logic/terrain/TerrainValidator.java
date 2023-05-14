package com.codecool.marsexploration.logic.terrain;

import com.codecool.marsexploration.data.Area;
import com.codecool.marsexploration.data.Constants;
import com.codecool.marsexploration.data.Coordinate;
import com.codecool.marsexploration.ui.Display;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class TerrainValidator {
    private final Display display;
    private final Random random;
    private final Set<Coordinate> areaCoordinates = new HashSet<>();
    private final Set<Coordinate> possibleCoordinatesAroundCheckCoordinate = new HashSet<>();
    private final Set<Coordinate> validCoordinatesAroundCheckCoordinate = new HashSet<>();
    private Coordinate checkCoordinate;
    private boolean isPossibleToProvideArea = true;

    public TerrainValidator(Display display, Random random) {
        this.display = display;
        this.random = random;
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
            getPossibleCoordinatesAroundCheckCoordinate(maxPlanetLength, checkCoordinate);
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

    private void getPossibleCoordinatesAroundCheckCoordinate(int maxPlanetLength, Coordinate proofCoordinate) {
        fromAllTerrainsExceptEdges(maxPlanetLength, proofCoordinate);
        fromLeftUpCorner(maxPlanetLength, proofCoordinate);
        fromFirstEdge(maxPlanetLength, proofCoordinate);
        fromRightUpCorner(maxPlanetLength, proofCoordinate);
        fromRightEdge(maxPlanetLength, proofCoordinate);
        fromLeftDownCorner(maxPlanetLength, proofCoordinate);
        fromBottomEdge(maxPlanetLength, proofCoordinate);
        fromRightDownCorner(maxPlanetLength, proofCoordinate);
        fromLeftEdge(maxPlanetLength, proofCoordinate);
    }

    private void fromAllTerrainsExceptEdges(int maxPlanetLength, Coordinate proof) {
        if (proof.y() > 0 && proof.y() < maxPlanetLength && proof.x() > 0 && proof.x() < maxPlanetLength) {
            possibleCoordinatesAroundCheckCoordinate.add(new Coordinate(proof.y() - 1, proof.x()));
            possibleCoordinatesAroundCheckCoordinate.add(new Coordinate(proof.y() - 1, proof.x() - 1));
            possibleCoordinatesAroundCheckCoordinate.add(new Coordinate(proof.y() - 1, proof.x() + 1));
            possibleCoordinatesAroundCheckCoordinate.add(new Coordinate(proof.y() + 1, proof.x()));
            possibleCoordinatesAroundCheckCoordinate.add(new Coordinate(proof.y() + 1, proof.x() - 1));
            possibleCoordinatesAroundCheckCoordinate.add(new Coordinate(proof.y() + 1, proof.x() + 1));
            possibleCoordinatesAroundCheckCoordinate.add(new Coordinate(proof.y(), proof.x() - 1));
            possibleCoordinatesAroundCheckCoordinate.add(new Coordinate(proof.y(), proof.x() + 1));
        }
    }

    private void fromLeftUpCorner(int maxPlanetLength, Coordinate proof) {
        if (proof.y() == 0 && proof.x() == 0) {
            possibleCoordinatesAroundCheckCoordinate.add(new Coordinate(0, 1));
            possibleCoordinatesAroundCheckCoordinate.add(new Coordinate(1, 1));
            possibleCoordinatesAroundCheckCoordinate.add(new Coordinate(1, 0));
            possibleCoordinatesAroundCheckCoordinate.add(new Coordinate(0, maxPlanetLength));
            possibleCoordinatesAroundCheckCoordinate.add(new Coordinate(1, maxPlanetLength));
            possibleCoordinatesAroundCheckCoordinate.add(new Coordinate(maxPlanetLength, 0));
            possibleCoordinatesAroundCheckCoordinate.add(new Coordinate(maxPlanetLength, 1));
            possibleCoordinatesAroundCheckCoordinate.add(new Coordinate(maxPlanetLength, maxPlanetLength));
        }
    }

    private void fromFirstEdge(int maxPlanetLength, Coordinate proof) {
        if (proof.y() == 0) {
            possibleCoordinatesAroundCheckCoordinate.add(new Coordinate(0, proof.x() - 1));
            possibleCoordinatesAroundCheckCoordinate.add(new Coordinate(0, proof.x() + 1));
            possibleCoordinatesAroundCheckCoordinate.add(new Coordinate(1, proof.x() - 1));
            possibleCoordinatesAroundCheckCoordinate.add(new Coordinate(1, proof.x()));
            possibleCoordinatesAroundCheckCoordinate.add(new Coordinate(1, proof.x() + 1));
            possibleCoordinatesAroundCheckCoordinate.add(new Coordinate(maxPlanetLength, proof.x() - 1));
            possibleCoordinatesAroundCheckCoordinate.add(new Coordinate(maxPlanetLength, proof.x()));
            possibleCoordinatesAroundCheckCoordinate.add(new Coordinate(maxPlanetLength, proof.x() + 1));
        }
    }

    private void fromRightUpCorner(int maxPlanetLength, Coordinate proof) {
        if (proof.y() == 0 && proof.x() == maxPlanetLength) {
            possibleCoordinatesAroundCheckCoordinate.add(new Coordinate(0, maxPlanetLength - 1));
            possibleCoordinatesAroundCheckCoordinate.add(new Coordinate(0, 0));
            possibleCoordinatesAroundCheckCoordinate.add(new Coordinate(1, maxPlanetLength - 1));
            possibleCoordinatesAroundCheckCoordinate.add(new Coordinate(1, maxPlanetLength));
            possibleCoordinatesAroundCheckCoordinate.add(new Coordinate(1, 0));
            possibleCoordinatesAroundCheckCoordinate.add(new Coordinate(maxPlanetLength, maxPlanetLength));
            possibleCoordinatesAroundCheckCoordinate.add(new Coordinate(maxPlanetLength, maxPlanetLength - 1));
            possibleCoordinatesAroundCheckCoordinate.add(new Coordinate(maxPlanetLength, 0));
        }
    }

    private void fromRightEdge(int maxPlanetLength, Coordinate proof) {
        if (proof.x() == maxPlanetLength) {
            possibleCoordinatesAroundCheckCoordinate.add(new Coordinate(proof.y(), 0));
            possibleCoordinatesAroundCheckCoordinate.add(new Coordinate(proof.y() - 1, 0));
            possibleCoordinatesAroundCheckCoordinate.add(new Coordinate(proof.y() + 1, 0));
            possibleCoordinatesAroundCheckCoordinate.add(new Coordinate(proof.y(), maxPlanetLength - 1));
            possibleCoordinatesAroundCheckCoordinate.add(new Coordinate(proof.y() - 1, maxPlanetLength - 1));
            possibleCoordinatesAroundCheckCoordinate.add(new Coordinate(proof.y() + 1, maxPlanetLength - 1));
            possibleCoordinatesAroundCheckCoordinate.add(new Coordinate(proof.y() - 1, maxPlanetLength));
            possibleCoordinatesAroundCheckCoordinate.add(new Coordinate(proof.y() + 1, maxPlanetLength));
        }
    }

    private void fromRightDownCorner(int maxPlanetLength, Coordinate proof) {
        if (proof.y() == maxPlanetLength && proof.x() == maxPlanetLength) {
            possibleCoordinatesAroundCheckCoordinate.add(new Coordinate(maxPlanetLength, maxPlanetLength - 1));
            possibleCoordinatesAroundCheckCoordinate.add(new Coordinate(maxPlanetLength, maxPlanetLength + 1));
            possibleCoordinatesAroundCheckCoordinate.add(new Coordinate(maxPlanetLength - 1, maxPlanetLength));
            possibleCoordinatesAroundCheckCoordinate.add(new Coordinate(maxPlanetLength - 1, maxPlanetLength - 1));
            possibleCoordinatesAroundCheckCoordinate.add(new Coordinate(maxPlanetLength - 1, maxPlanetLength + 1));
            possibleCoordinatesAroundCheckCoordinate.add(new Coordinate(0, maxPlanetLength));
            possibleCoordinatesAroundCheckCoordinate.add(new Coordinate(0, maxPlanetLength - 1));
            possibleCoordinatesAroundCheckCoordinate.add(new Coordinate(0, 0));
        }
    }

    private void fromBottomEdge(int maxPlanetLength, Coordinate proof) {
        if (proof.y() == maxPlanetLength) {
            possibleCoordinatesAroundCheckCoordinate.add(new Coordinate(maxPlanetLength, proof.x() - 1));
            possibleCoordinatesAroundCheckCoordinate.add(new Coordinate(maxPlanetLength, proof.x() + 1));
            possibleCoordinatesAroundCheckCoordinate.add(new Coordinate(maxPlanetLength - 1, proof.x() - 1));
            possibleCoordinatesAroundCheckCoordinate.add(new Coordinate(maxPlanetLength - 1, proof.x()));
            possibleCoordinatesAroundCheckCoordinate.add(new Coordinate(maxPlanetLength - 1, proof.x() + 1));
            possibleCoordinatesAroundCheckCoordinate.add(new Coordinate(0, proof.x() - 1));
            possibleCoordinatesAroundCheckCoordinate.add(new Coordinate(0, proof.x()));
            possibleCoordinatesAroundCheckCoordinate.add(new Coordinate(0, proof.x() + 1));
        }
    }

    private void fromLeftDownCorner(int maxPlanetLength, Coordinate proof) {
        if (proof.y() == maxPlanetLength && proof.x() == 0) {
            possibleCoordinatesAroundCheckCoordinate.add(new Coordinate(maxPlanetLength, maxPlanetLength));
            possibleCoordinatesAroundCheckCoordinate.add(new Coordinate(maxPlanetLength, 1));
            possibleCoordinatesAroundCheckCoordinate.add(new Coordinate(maxPlanetLength - 1, 0));
            possibleCoordinatesAroundCheckCoordinate.add(new Coordinate(maxPlanetLength - 1, 1));
            possibleCoordinatesAroundCheckCoordinate.add(new Coordinate(maxPlanetLength - 1, maxPlanetLength));
            possibleCoordinatesAroundCheckCoordinate.add(new Coordinate(0, 0));
            possibleCoordinatesAroundCheckCoordinate.add(new Coordinate(0, 1));
            possibleCoordinatesAroundCheckCoordinate.add(new Coordinate(0, maxPlanetLength));
        }
    }

    private void fromLeftEdge(int maxPlanetLength, Coordinate proof) {
        if (proof.x() == 0) {
            possibleCoordinatesAroundCheckCoordinate.add(new Coordinate(proof.y() - 1, 1));
            possibleCoordinatesAroundCheckCoordinate.add(new Coordinate(proof.y(), 1));
            possibleCoordinatesAroundCheckCoordinate.add(new Coordinate(proof.y() + 1, 1));
            possibleCoordinatesAroundCheckCoordinate.add(new Coordinate(proof.y() - 1, 0));
            possibleCoordinatesAroundCheckCoordinate.add(new Coordinate(proof.y() + 1, 0));
            possibleCoordinatesAroundCheckCoordinate.add(new Coordinate(proof.y() - 1, maxPlanetLength));
            possibleCoordinatesAroundCheckCoordinate.add(new Coordinate(proof.y(), maxPlanetLength));
            possibleCoordinatesAroundCheckCoordinate.add(new Coordinate(proof.y() + 1, maxPlanetLength));
        }
    }

    private void clearAllMapsUsedToValidate() {
        possibleCoordinatesAroundCheckCoordinate.clear();
        validCoordinatesAroundCheckCoordinate.clear();
    }
}