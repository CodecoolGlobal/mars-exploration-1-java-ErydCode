package com.codecool.marsexploration;

public class Application {
    //ToDo still get some times this error:
//    Exception in thread "main" java.lang.ArrayIndexOutOfBoundsException: Index 34 out of bounds for length 34
//    at com.codecool.marsexploration.logic.terrain.TerrainValidator.getValidCoordinatesFromPossibility(TerrainValidator.java:88)
//    at com.codecool.marsexploration.logic.terrain.TerrainValidator.getValidCoordinates(TerrainValidator.java:78)
//    at com.codecool.marsexploration.logic.terrain.TerrainValidator.addValidCoordinateToAreaCoordinates(TerrainValidator.java:55)
//    at com.codecool.marsexploration.logic.terrain.TerrainValidator.getAreaCoordinate(TerrainValidator.java:32)
//    at com.codecool.marsexploration.logic.terrain.TerrainProvider.getRandomGeneratedTerrainForPlanet(TerrainProvider.java:34)
//    at com.codecool.marsexploration.logic.planet.templates.PlanetTemplateCodeCool.getTemplate(PlanetTemplateCodeCool.java:61)
//    at com.codecool.marsexploration.StartCreatingPlanets.run(StartCreatingPlanets.java:50)
//    at com.codecool.marsexploration.Application.main(Application.java:6)
    public static void main(String[] args) {
        PlanetCreator planetCreator = new PlanetCreator();
        planetCreator.createPlanets();
    }
}