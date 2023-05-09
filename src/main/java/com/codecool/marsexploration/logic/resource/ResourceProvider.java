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
            String name = input.getUserInput("Please enter the resource name. For example \"mineral\"");
            String preferences = input.getUserInput("This resource can be found at which area\n" +
                    "The Planet have this Type of area: " + areaSymbols);
            display.message("Please enter in the next inputs one RGB Color\n" +
                    "https://htmlcolorcodes.com");
            int[] rgb = new int[3];
            int red = input.getNumericUserInput("Please enter the \"Red Color\". Just use a number between 0-255");
            rgb[0] = red;
            int green = input.getNumericUserInput("Please enter the \"Green Color\". Just use a number between 0-255");
            rgb[0] = green;
            int blue = input.getNumericUserInput("Please enter the \"Blue Color\". Just use a number between 0-255");
            rgb[0] = blue;
            allResource.add(new Resource(name, preferences, rgb));
            wantNewResource = input.getUserInput("You want to create a new resource?\n" +
                    "Pleaser enter the command \"yes\"/\"y\" or \"no\"/\"n\"");
        }
        System.out.println(allResource);
        return allResource;
    }
}