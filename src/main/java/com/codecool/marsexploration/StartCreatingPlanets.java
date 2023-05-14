package com.codecool.marsexploration;

import com.codecool.marsexploration.io.PlanetImageWriter;
import com.codecool.marsexploration.logic.area.AreasProvider;
import com.codecool.marsexploration.logic.area.AreasTypeProvider;
import com.codecool.marsexploration.logic.planet.PlanetProvider;
import com.codecool.marsexploration.logic.planet.PlanetTypeProvider;
import com.codecool.marsexploration.logic.planet.templates.PlanetTemplateCodeCool;
import com.codecool.marsexploration.logic.resource.ResourcePlacer;
import com.codecool.marsexploration.logic.resource.ResourcesProvider;
import com.codecool.marsexploration.logic.terrain.TerrainProvider;
import com.codecool.marsexploration.ui.Display;
import com.codecool.marsexploration.ui.Input;

import java.util.Random;

import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;


public class StartCreatingPlanets {
    private final Display display = new Display();
    private final Input input = new Input(display);
    private final Random random = new Random();
    private final AreasTypeProvider areasTypeProvider = new AreasTypeProvider();
    private final AreasProvider areas = new AreasProvider(display, input, random, areasTypeProvider);
    private final ResourcesProvider resources = new ResourcesProvider();
    private final PlanetTypeProvider planetTypeProvider = new PlanetTypeProvider(display, input, random, areas, resources);
    private final ResourcePlacer resourcePlacer = new ResourcePlacer(random, display);
    private final TerrainProvider terrainProvider = new TerrainProvider(display, random, resourcePlacer);
    private final PlanetImageWriter planetImageWriter = new PlanetImageWriter();
    private final PlanetProvider planetProvider = new PlanetProvider(display, input, planetTypeProvider, terrainProvider, planetImageWriter);
    private final PlanetTemplateCodeCool planetTemplateCodeCool = new PlanetTemplateCodeCool(display, input, random, areasTypeProvider, terrainProvider, planetImageWriter);

    public void run() {
        display.printTitle("Welcome to planet creator - simulate your planet");
        String wantNewPlanet = "yes";
        while (!containsIgnoreCase("no", wantNewPlanet)) {
            display.printSubtitle("Would you like to create your own planet or use a template?");
            int userChoice = 0;
            while (userChoice < 1 || userChoice > 2) {
                userChoice = input.getNumericUserInput("Please enter 1 to create your own Planet.\n" +
                        "Please enter 2 to use a template.");
            }
            if (userChoice == 1) {
                planetProvider.userCustomized();
            }
            if (userChoice == 2) {
                planetTemplateCodeCool.getTemplate();
            }
            wantNewPlanet = input.getUserInput("Do you want to create a new planet?\n" +
                    "Pleaser enter the command \"yes\"/\"y\" or \"no\"/\"n\".");
        }
    }
}