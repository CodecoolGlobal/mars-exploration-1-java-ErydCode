package com.codecool.marsexploration;

import com.codecool.marsexploration.data.Planet;
import com.codecool.marsexploration.data.Area;
import com.codecool.marsexploration.data.TerrainTypes;
import com.codecool.marsexploration.logic.area.AreasData;
import com.codecool.marsexploration.logic.area.AreasProvider;
import com.codecool.marsexploration.ui.Display;
import com.codecool.marsexploration.ui.Input;

import java.util.List;
import java.util.Random;

public class Application {
    public static void main(String[] args) {
        Input input = new Input();
        Display display = new Display();
        Random random = new Random();
        display.printTitle("Welcome to planet creator - simulate your planet");
        AreasData areas = new AreasData(input, display, random);
        Planet planet = new Planet("mars", 100, areas.getAreas(), 10);
        System.out.println(mountains);
        System.out.println(pits);
        
    }
}
