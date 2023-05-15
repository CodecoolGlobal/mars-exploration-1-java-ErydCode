package com.codecool.marsexploration.logic.validator;

import com.codecool.marsexploration.data.Coordinate;

import java.util.Set;

public class CoordinateValidator {
    public void populatePossibleCoordinatesAroundCheckCoordinate(Set<Coordinate> possibleCoordinatesAroundCheckCoordinate, int maxPlanetLength, Coordinate current) {
        fromAllTerrainsExceptEdges(possibleCoordinatesAroundCheckCoordinate, maxPlanetLength, current);
        fromLeftUpCorner(possibleCoordinatesAroundCheckCoordinate, maxPlanetLength, current);
        fromFirstEdge(possibleCoordinatesAroundCheckCoordinate, maxPlanetLength, current);
        fromRightUpCorner(possibleCoordinatesAroundCheckCoordinate, maxPlanetLength, current);
        fromRightEdge(possibleCoordinatesAroundCheckCoordinate, maxPlanetLength, current);
        fromLeftDownCorner(possibleCoordinatesAroundCheckCoordinate, maxPlanetLength, current);
        fromBottomEdge(possibleCoordinatesAroundCheckCoordinate, maxPlanetLength, current);
        fromRightDownCorner(possibleCoordinatesAroundCheckCoordinate, maxPlanetLength, current);
        fromLeftEdge(possibleCoordinatesAroundCheckCoordinate, maxPlanetLength, current);
    }

    private void fromAllTerrainsExceptEdges(Set<Coordinate> possibleCoordinatesAroundCheckCoordinate, int maxPlanetLength, Coordinate proof) {
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

    private void fromLeftUpCorner(Set<Coordinate> possibleCoordinatesAroundCheckCoordinate, int maxPlanetLength, Coordinate proof) {
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

    private void fromFirstEdge(Set<Coordinate> possibleCoordinatesAroundCheckCoordinate, int maxPlanetLength, Coordinate proof) {
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

    private void fromRightUpCorner(Set<Coordinate> possibleCoordinatesAroundCheckCoordinate, int maxPlanetLength, Coordinate proof) {
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

    private void fromRightEdge(Set<Coordinate> possibleCoordinatesAroundCheckCoordinate, int maxPlanetLength, Coordinate proof) {
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

    private void fromRightDownCorner(Set<Coordinate> possibleCoordinatesAroundCheckCoordinate, int maxPlanetLength, Coordinate proof) {
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

    private void fromBottomEdge(Set<Coordinate> possibleCoordinatesAroundCheckCoordinate, int maxPlanetLength, Coordinate proof) {
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

    private void fromLeftDownCorner(Set<Coordinate> possibleCoordinatesAroundCheckCoordinate, int maxPlanetLength, Coordinate proof) {
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

    private void fromLeftEdge(Set<Coordinate> possibleCoordinatesAroundCheckCoordinate, int maxPlanetLength, Coordinate proof) {
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
}
