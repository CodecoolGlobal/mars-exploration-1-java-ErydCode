package com.codecool.marsexploration.logic;

import com.codecool.marsexploration.data.Coordinate;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.of;

class CoordinateCreatorTest {
    CoordinateCreator creator = new CoordinateCreator();
    int mapSize = 3;

    public static Stream<Arguments> parameters() {
        return Stream.of(
                of(new Coordinate(0, 0), new Coordinate(0, 0)),
                of(new Coordinate(2, 2), new Coordinate(-1, -1)),
                of(new Coordinate(0, 0), new Coordinate(3, 3)),
                of(new Coordinate(2, 0), new Coordinate(-1, 3)),
                of(new Coordinate(0, 2), new Coordinate(3, -1)),
                of(new Coordinate(2, 0), new Coordinate(-1, 0)),
                of(new Coordinate(0, 1), new Coordinate(3, 1)),
                of(new Coordinate(1,2), new Coordinate(1,-1)),
                of(new Coordinate(0, 0), new Coordinate(0, 3))
        );
    }

    @ParameterizedTest
    @MethodSource("parameters")
    void adaptToSphericalPlanet(Coordinate expected, Coordinate current) {
        Coordinate result = creator.adaptToSphericalPlanet(current, mapSize);

        assertEquals(expected, result);
    }
}