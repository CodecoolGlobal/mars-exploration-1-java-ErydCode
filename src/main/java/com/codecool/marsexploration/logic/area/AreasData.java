package com.codecool.marsexploration.logic.area;

import com.codecool.marsexploration.data.Area;
import com.codecool.marsexploration.data.TerrainTypes;
import com.codecool.marsexploration.ui.Display;
import com.codecool.marsexploration.ui.Input;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;

public class AreasData {
    private final Input input;
    private final Display display;
    private final Random random;
    private final AreasProvider areasProvider = new AreasProvider();
    private String userStrInput;
    private Integer userIntInput;

    public AreasData(Input input, Display display, Random random) {
        this.input = input;
        this.display = display;
        this.random = random;
    }

    public List<Area> getAreas() {
        List<Area> allAreas = new ArrayList<>();
        display.printSubtitle("Create your areas\n" +
                "You can quit with the command: \"quit\", \"finish\" or \"0\"");
        while (!containsIgnoreCase("finish", userStrInput) || !containsIgnoreCase("quit", userStrInput) || 0 != userIntInput) {
            String name = input.getUserInput("Please enter the area name. For example \"mountain\"");
            userStrInput = name;
            Integer amount = input.getNumericUserInput("Please enter the amount ")
            List<Area> areas = areasProvider.getTerrain(name, 3, 10, 30, TerrainTypes.MOUNTAIN.getSymbol(), random);
            allAreas.addAll(areas);
        }
        System.out.println(allAreas);
        return allAreas;
    }
}
