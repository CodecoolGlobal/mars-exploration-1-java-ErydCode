package com.codecool.marsexploration.logic;

import com.codecool.marsexploration.data.Coordinate;

import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

public class CoordinateCreator {
    public Set<Coordinate> create(Coordinate current, int mapSize) {
        Coordinate zero = new Coordinate(0, 0);
        return IntStream.rangeClosed(-1, 1)
                .mapToObj(x -> IntStream.rangeClosed(-1, 1).mapToObj(y -> new Coordinate(y, x)).collect(toList()))
                .flatMap(List::stream)
                .filter(transformation -> !transformation.equals(zero))
                .map(transformation -> new Coordinate(transformation.y() + current.y(), transformation.x() + current.x()))
                .map(position -> adaptToSphericalPlanet(position, mapSize))
                .collect(toSet());
    }

    Coordinate adaptToSphericalPlanet(Coordinate position, int mapSize) {
        return new Coordinate(
                adaptToSphericalPlanet(position.y(), mapSize),
                adaptToSphericalPlanet(position.x(), mapSize)
        );
    }

    private int adaptToSphericalPlanet(int axis, int mapSize) {
        if (axis < 0) {
            return mapSize - 1;
        }
        return axis % mapSize;
    }

}
