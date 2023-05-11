package com.codecool.marsexploration.logic.area;

import com.codecool.marsexploration.data.Area;
import com.codecool.marsexploration.ui.Display;
import com.codecool.marsexploration.ui.Input;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;

public class AreasProvider {
    private final AreasTypeProvider areasTypeProvider = new AreasTypeProvider();
    private final List<String> usedSymbols = new ArrayList<>();
    private String wantNewArea;

    public List<Area> getAreas(Display display, Input input, Random random) {
        List<Area> allAreas = new ArrayList<>();
        display.printSubtitle("Create your areas");
        while (!containsIgnoreCase("no", wantNewArea)) {
            String name = input.getUserInput("Please enter the area name. For example \"mountain\"");
            Integer minSize = input.getNumericUserInput("Please enter the minimum size of this area.");
            Integer maxSize = input.getNumericUserInput("Please enter the maximum size of this area.\n" +
                    "It have to be bigger than " + minSize + ".");
            Integer amount = input.getNumericUserInput("How many different sizes can this area have?");
            String symbol = input.getUserInput("Please enter a symbol. For example \"^\"" +
                    (usedSymbols.isEmpty() ? "" : "\nAlready used Symbols: " + usedSymbols));
            usedSymbols.add(symbol);
            int[] rgb = input.getRGB();
            List<Area> areas = areasTypeProvider.getTerrain(name, amount, minSize, maxSize, symbol, rgb, random);
            allAreas.addAll(areas);
            wantNewArea = input.getUserInput("You want to create a new Area?\n" +
                    "Pleaser enter the command \"yes\"/\"y\" or \"no\"/\"n\"");
        }
        return allAreas;
    }
}