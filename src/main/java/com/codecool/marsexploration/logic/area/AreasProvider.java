package com.codecool.marsexploration.logic.area;

import com.codecool.marsexploration.data.Area;
import com.codecool.marsexploration.data.Constants;
import com.codecool.marsexploration.ui.Display;
import com.codecool.marsexploration.ui.Input;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;

public class AreasProvider {
    private final Display display;
    private final Input input;
    private final AreasTypeProvider areasTypeProvider;
    private final Set<String> usedSymbols = new HashSet<>();
    private String wantNewArea;

    public AreasProvider(Display display, Input input, AreasTypeProvider areasTypeProvider) {
        this.display = display;
        this.input = input;
        this.areasTypeProvider = areasTypeProvider;
    }

    public List<Area> getAreas(int totalTerrains) {
        List<Area> allAreas = new ArrayList<>();
        usedSymbols.add(Constants.EMPTY_SYMBOL);
        display.printSubtitle("Create your areas");
        while (!containsIgnoreCase("no", wantNewArea)) {
            String name = input.getUserInput("Please enter the area name. For example \"mountain\"");
            //ToDo calculate how many Terrains are left just let enter the user a Number that match!!!
            int totalAreaSize = allAreas.stream().mapToInt(Area::amountOfSymbols).sum();
            int leftOverTerrains = totalTerrains - totalAreaSize;
            int minSize = getAmount(0, leftOverTerrains - 1, "Please enter the minimum size of this area.\n" +
                    "It must be greater than 0 and less than " + (leftOverTerrains - 1) + ".");
            int maxSize = getAmount(minSize, leftOverTerrains, "Please enter the maximum size of this area.\n" +
                    "It must be greater than " + minSize + " and less than " + leftOverTerrains + ".");
            int amount = getAmount(0, leftOverTerrains, "How many different sizes can this area have?\n" +
                    "Don't forget you have " + leftOverTerrains + " terrains left");
            String symbol = getSymbol();
            int[] rgb = input.getRGB();
            List<Area> areas = areasTypeProvider.getArea(name, amount, minSize, maxSize, symbol, rgb);
            allAreas.addAll(areas);
            wantNewArea = input.getUserInput("Do you want to create a new area?\n" +
                    "Pleaser enter the command \"yes\"/\"y\" or \"no\"/\"n\"");
        }
        return allAreas;
    }

    private int getAmount(int minimumSize, int leftOverTerrains, String message) {
        int amount = 0;
        while (amount <= minimumSize || amount > leftOverTerrains) {
            amount = input.getNumericUserInput(message);
        }
        return amount;
    }

    private String getSymbol() {
        String symbol = Constants.EMPTY_SYMBOL;
        boolean isSymbolValid = false;
        while (!isSymbolValid) {
            if (usedSymbols.contains(symbol)) {
                symbol = input.getUserInput("Please enter a symbol. For example \"^\"\n" +
                        "Already used Symbols: " + usedSymbols);
            } else {
                usedSymbols.add(symbol);
                isSymbolValid = true;
            }
        }
        return symbol;
    }
}