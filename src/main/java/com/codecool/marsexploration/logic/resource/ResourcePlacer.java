package com.codecool.marsexploration.logic.resource;

import com.codecool.marsexploration.data.Planet;

import java.util.Random;

public class ResourcePlacer {
    public void place(String[][] planetTerrains, Planet planet, Random random){
        int randomX = random.nextInt(planet.xyLength());
        int randomY = random.nextInt(planet.xyLength());


        /*
        planet.areas().stream().filter(area -> area.symbol().equals(planet.allResource().stream().map(Resource::preferences)));
         */
    }
}
