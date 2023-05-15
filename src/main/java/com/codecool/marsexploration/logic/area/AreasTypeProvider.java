package com.codecool.marsexploration.logic.area;

import com.codecool.marsexploration.data.Area;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class AreasTypeProvider {

    private final Random random;

    public AreasTypeProvider(Random random) {
        this.random = random;
    }

    public List<Area> getArea(String name, int amount, int minSize, int maxSize, String symbol, int[] rgb) {
        List<Area> areas = new ArrayList<>();
        for (int i = 1; i <= amount; i++) {
            int randomNumber = random.nextInt(minSize, maxSize);
            areas.add(new Area(i + "." + name, randomNumber, symbol, rgb));
        }
        return areas.stream().sorted(Comparator.comparingInt(Area::amountOfSymbols)).toList();
    }
}