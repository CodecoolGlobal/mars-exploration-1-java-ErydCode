package com.codecool.marsexploration.logic.planet;

import com.codecool.marsexploration.data.Area;
import com.codecool.marsexploration.data.Planet;
import com.codecool.marsexploration.data.Resource;
import com.codecool.marsexploration.logic.area.AreasProvider;
import com.codecool.marsexploration.logic.resource.ResourcesProvider;
import com.codecool.marsexploration.ui.Display;
import com.codecool.marsexploration.ui.Input;

import java.util.List;
import java.util.Random;

public class PlanetProvider {

    private final Display display;
    private final Input input;
    private final Random random;
    private final AreasProvider areas;
    private final ResourcesProvider resource;

    public PlanetProvider(Display display, Input input, Random random, AreasProvider areas, ResourcesProvider resource) {
        this.display = display;
        this.input = input;
        this.random = random;
        this.areas = areas;
        this.resource = resource;
    }

    public Planet getPlanet() {
        display.printTitle("Create your planet");
        String name = input.getUserInput("Please enter the name of the planet.");
        int xyLength = input.getNumericUserInput("Please enter length of the planet.");
        int amountAreas = input.getNumericUserInput("Please enter how many areas the planet have.");
        List<Area> allAreas = areas.getAreas();
        List<Resource> allResource = resource.getResource(display, input, allAreas);
        return new Planet(name, xyLength, allAreas, amountAreas, allResource);
    }
}