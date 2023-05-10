package com.codecool.marsexploration.logic.area;

import com.codecool.marsexploration.data.Area;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class AreasTypeProvider {

    public List<Area> getTerrain(String name, int amount, int minSize, int maxSize, String symbol, int[] rgb, Random random) {
        List<Area> areas = new ArrayList<>();
        for (int i = 1; i <= amount; i++) {
            int randomNumber = random.nextInt(minSize, maxSize);
            areas.add(new Area(i + ". " + name, randomNumber, symbol, rgb));
        }
        return areas.stream().sorted(Comparator.comparingInt(Area::size)).toList();
    }
}