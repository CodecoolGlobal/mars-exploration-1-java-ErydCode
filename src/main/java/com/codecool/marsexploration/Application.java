package com.codecool.marsexploration;

import com.codecool.marsexploration.data.Area;
import com.codecool.marsexploration.data.Planet;
import com.codecool.marsexploration.logic.area.AreasData;
import com.codecool.marsexploration.ui.Display;
import com.codecool.marsexploration.ui.Input;

import java.util.List;
import java.util.Random;

public class Application {
    public static void main(String[] args) {
        Display display = new Display();
        Input input = new Input(display);
        Random random = new Random();
        display.printTitle("Welcome to planet creator - simulate your planet");
        AreasData areas = new AreasData(input, display, random);
        List<Area> allAreas = areas.getAreas();
        Planet planet = new Planet("mars", 100, allAreas, 10);
    }
}