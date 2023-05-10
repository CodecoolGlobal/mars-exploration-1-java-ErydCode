package com.codecool.marsexploration.logic.resource;

import com.codecool.marsexploration.data.Area;
import com.codecool.marsexploration.data.Resource;
import com.codecool.marsexploration.ui.Display;
import com.codecool.marsexploration.ui.Input;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;

public class ResourceProvider {
    private String wantNewResource;

    public List<Resource> getResource(Display display, Input input, List<Area> allAreas) {
        List<Resource> allResource = new ArrayList<>();
        Set<String> areaSymbols = new HashSet<>(allAreas.stream().map(Area::symbol).toList());
        display.printSubtitle("Create your resources");
        while (!containsIgnoreCase("no", wantNewResource)) {
            String name = input.getUserInput("Please enter the resource name. For example \"mineral\".");
            String symbol = input.getUserInput("Please enter a symbol for this resource. For example \"≈\".");
            String preferences = input.getUserInput("This resource can be found at which area.\n" +
                    "The Planet have this Type of area: " + areaSymbols);
            int[] rgb = input.getRGB();
            allResource.add(new Resource(name, symbol, preferences, rgb));
            wantNewResource = input.getUserInput("You want to create a new resource?\n" +
                    "Pleaser enter the command \"yes\"/\"y\" or \"no\"/\"n\".");
        }
        return allResource;
    }
}