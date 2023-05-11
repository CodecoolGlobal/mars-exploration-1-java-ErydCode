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
        PlanetProvider planet = new PlanetProvider(display, input, random);
        display.printTitle("Welcome to planet creator - simulate your planet");
        display.printSubtitle("You want create your own Planet or use an already explored Exodus Planet?");
        int userChoice = 0;
        while (userChoice < 1 || userChoice > 2) {
            userChoice = input.getNumericUserInput("Please enter 1 to create your own Planet\n" +
                    "Please enter 2 to use the explored Exodus Planet");
        }
        if (userChoice == 1) {
            Planet createdPlanet = planet.getPlanet();
            display.printTitle(createdPlanet.name());
            System.out.println("RAW Data from " + createdPlanet.name() + ": " + createdPlanet);
        }
        display.printEndLines();
    }
}