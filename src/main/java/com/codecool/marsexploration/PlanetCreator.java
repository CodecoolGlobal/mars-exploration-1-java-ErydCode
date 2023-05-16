package com.codecool.marsexploration;

import com.codecool.marsexploration.io.PlanetImageWriter;
import com.codecool.marsexploration.logic.CoordinateCreator;
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
import com.codecool.marsexploration.ui.choice.UserChoice;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;


public class PlanetCreator {
    private final Display display = new Display();
    private final Input input = new Input(display);
    private final Random random = new Random();
    private final AreasTypeProvider areasTypeProvider = new AreasTypeProvider(random);
    private final AreasProvider areas = new AreasProvider(display, input, areasTypeProvider);
    private final ResourcesProvider resources = new ResourcesProvider();
    private final CoordinateCreator coordinateCreator = new CoordinateCreator();
    private final ResourcePlacer resourcePlacer = new ResourcePlacer(random, coordinateCreator);
    private final TerrainProvider terrainProvider = new TerrainProvider(display, random, coordinateCreator, resourcePlacer);
    private final PlanetImageWriter planetImageWriter = new PlanetImageWriter();
    private final PlanetTypeProvider planetTypeProvider = new PlanetTypeProvider(display, input, random, areas, resources);
    private final PlanetProvider planetProvider = new PlanetProvider(display, input, planetTypeProvider, terrainProvider, planetImageWriter);
    private final PlanetTemplateCodeCool planetTemplateCodeCool = new PlanetTemplateCodeCool(display, input, random, areasTypeProvider, terrainProvider, planetImageWriter);
    private final List<UserChoice> userChoices = List.of(
            new UserChoice(1, "Create your own planet", planetProvider::userCustomized),
            new UserChoice(2, "Use a planet template", planetTemplateCodeCool::getTemplate)
    );

    public void createPlanets() {
        display.printTitle("Welcome to planet creator - simulate your planet");
        String wantNewPlanet = "yes";
        while (!containsIgnoreCase("no", wantNewPlanet)) {
            display.printSubtitle("Would you like to create your own planet or use a template?");
            handleUserChoice();
            wantNewPlanet = input.getUserInput("Do you want to create a new planet?\n" +
                    "Pleaser enter the command \"yes\"/\"y\" or \"no\"/\"n\".");
        }
    }

    private void handleUserChoice() {
        Optional<UserChoice> oUserChoice = Optional.empty();
        while (oUserChoice.isEmpty()) {
            oUserChoice = getUserChoice();
        }
        oUserChoice.get().action().perform();
    }

    private Optional<UserChoice> getUserChoice() {
        display.message("Enter your choice:");
        String message = userChoices.stream()
                .map(choice -> choice.number() + ". - " + choice.description() + "\n")
                .collect(Collectors.joining());
        int number = input.getNumericUserInput(message);
        return userChoices.stream()
                .filter(choice -> choice.number() == number)
                .findFirst();
    }
}