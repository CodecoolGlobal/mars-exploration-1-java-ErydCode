package com.codecool.marsexploration.logic.area;

import com.codecool.marsexploration.data.Area;
import com.codecool.marsexploration.ui.Display;
import com.codecool.marsexploration.ui.Input;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;

public class AreasProvider {
    private final Display display;
    private final Input input;
    private final Random random;
    private final AreasTypeProvider areasTypeProvider;
    private final List<String> usedSymbols = new ArrayList<>();
    private String wantNewArea;

    public AreasProvider(Display display, Input input, Random random, AreasTypeProvider areasTypeProvider) {
        this.display = display;
        this.input = input;
        this.random = random;
        this.areasTypeProvider = areasTypeProvider;
    }

    public List<Area> getAreas() {
        List<Area> allAreas = new ArrayList<>();
        display.printSubtitle("Create your areas");
        while (!containsIgnoreCase("no", wantNewArea)) {
            String name = input.getUserInput("Please enter the area name. For example \"mountain\"");
            int minSize = 0;
            while (minSize <= 0) {
                minSize = input.getNumericUserInput("Please enter the minimum size of this area.");
            }
            int maxSize = 0;
            while (maxSize <= minSize) {
                maxSize = input.getNumericUserInput("Please enter the maximum size of this area.\n" +
                        "It have to be bigger than " + minSize + ".");
            }
            int amount = input.getNumericUserInput("How many different sizes can this area have?");
            String symbol = input.getUserInput("Please enter a symbol. For example \"^\"" +
                    (usedSymbols.isEmpty() ? "" : "\nAlready used Symbols: " + usedSymbols));
            usedSymbols.add(symbol);
            int[] rgb = input.getRGB();
            List<Area> areas = areasTypeProvider.getArea(name, amount, minSize, maxSize, symbol, rgb, random);
            allAreas.addAll(areas);
            wantNewArea = input.getUserInput("You want to create a new Area?\n" +
                    "Pleaser enter the command \"yes\"/\"y\" or \"no\"/\"n\"");
        }
        return allAreas;
    }
}