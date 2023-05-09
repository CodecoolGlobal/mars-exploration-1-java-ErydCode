package com.codecool.marsexploration;

import com.codecool.marsexploration.data.Area;
import com.codecool.marsexploration.data.Planet;
import com.codecool.marsexploration.logic.area.AreasInput;
import com.codecool.marsexploration.logic.planet.PlanetProvider;
import com.codecool.marsexploration.ui.Display;
import com.codecool.marsexploration.ui.Input;

import java.util.List;
import java.util.Random;

public class Application {
    public static void main(String[] args) {
        Display display = new Display();
        Input input = new Input(display);
        Random random = new Random();
        display.printTitle("Welcome to planet creator - simulate your planet");
        AreasInput areas = new AreasInput(input, display, random);
        List<Area> allAreas = areas.getAreas();
        //PlanetProvider planet = new PlanetProvider();
        //Planet planet = new Planet("mars", 100, allAreas, 10);
    }
}