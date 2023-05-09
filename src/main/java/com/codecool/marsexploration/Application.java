package com.codecool.marsexploration;

import com.codecool.marsexploration.data.Planet;
import com.codecool.marsexploration.logic.planet.PlanetProvider;
import com.codecool.marsexploration.ui.Display;
import com.codecool.marsexploration.ui.Input;

import java.util.Random;

public class Application {
    public static void main(String[] args) {
        Display display = new Display();
        Input input = new Input(display);
        Random random = new Random();
        display.printTitle("Welcome to planet creator - simulate your planet");
        PlanetProvider planet = new PlanetProvider(display, input, random);
        Planet mars = planet.getPlanet();
        display.printTitle(mars.name());
        System.out.println(mars);
        display.printEndLines();
    }
}