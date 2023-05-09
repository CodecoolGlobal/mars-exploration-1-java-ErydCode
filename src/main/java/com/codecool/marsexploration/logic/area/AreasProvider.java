package com.codecool.marsexploration.logic.area;

import com.codecool.marsexploration.data.Area;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class AreasProvider {

    public List<Area> getTerrain(String name, int amount, int minSize, int maxSize, String symbol, Random random) {
        List<Area> areas = new ArrayList<>();
        for (int i = 1; i <= amount; i++) {
            int randomNumber = random.nextInt(minSize, maxSize);
            areas.add(new Area(name + randomNumber, randomNumber, symbol));
        }
        return areas.stream().sorted(Comparator.comparingInt(Area::size)).toList();
    }
}