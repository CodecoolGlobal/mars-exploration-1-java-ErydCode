package com.codecool.marsexploration.logic.resource;

import com.codecool.marsexploration.data.Area;
import com.codecool.marsexploration.data.Constants;
import com.codecool.marsexploration.data.Resource;
import com.codecool.marsexploration.ui.Display;
import com.codecool.marsexploration.ui.Input;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;

public class ResourcesProvider {
    private final Set<String> usedSymbols = new HashSet<>();
    private String wantNewResource;

    public List<Resource> getResource(Display display, Input input, List<Area> allAreas) {
        List<Resource> allResource = new ArrayList<>();
        Set<String> areaSymbols = new HashSet<>(allAreas.stream().map(Area::symbol).toList());
        usedSymbols.addAll(areaSymbols);
        usedSymbols.add(Constants.EMPTY_SYMBOL);
        display.printSubtitle("Create your resources");
        while (!containsIgnoreCase("no", wantNewResource)) {
            String name = input.getUserInput("Please enter the resource name. For example \"mineral\".");
            String symbol = getSymbol(input);
            String preferences = getPreferences(input, areaSymbols);
            int[] rgb = input.getRGB();
            allResource.add(new Resource(name, symbol, preferences, rgb));
            wantNewResource = input.getUserInput("Do you want to create a new resource?\n" +
                    "Pleaser enter the command \"yes\"/\"y\" or \"no\"/\"n\".");
        }
        return allResource;
    }

    private String getSymbol(Input input) {
        String symbol = " ";
        boolean isSymbolValid = false;
        while (!isSymbolValid) {
            if (usedSymbols.contains(symbol)) {
                symbol = input.getUserInput("Please enter a symbol for this resource. For example \"≈\".\n" +
                        "Already used Symbols: " + usedSymbols);
            } else {
                usedSymbols.add(symbol);
                isSymbolValid = true;
            }
        }
        return symbol;
    }

    private String getPreferences(Input input, Set<String> areaSymbols) {
        String preferences = "";
        boolean isPreferenceValid = false;
        while (!isPreferenceValid) {
            if (!areaSymbols.contains(preferences)) {
                preferences = input.getUserInput("This resource can be found at which area.\n" +
                        "The Planet have this Type of area: " + areaSymbols);
            } else {
                isPreferenceValid = true;
            }
        }
        return preferences;
    }
}