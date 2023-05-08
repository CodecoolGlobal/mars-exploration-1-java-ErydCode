package com.codecool.marsexploration.logic;

import java.awt.geom.Area;
import java.util.List;

public class MapConfig {

    private String fileName;
    private int mapWidth;
    private int mapHeight;
    private int terrainElement;
    private int resourceElement;
    private char[][] regions;
    private List<Area> mountains;
    private List<Area> pits;



    public MapConfig(String fileName, int mapWidth, int terrainElement, int resourceElement, char[][] regions, List<Area> mountains, List<Area> pits) {
        this.fileName = fileName;
        this.mapWidth = mapWidth;
        this.terrainElement = terrainElement;
        this.resourceElement = resourceElement;
        this.regions = regions;
        this.mapHeight = mapWidth;
        this.mountains = mountains;
        this.pits = pits;
    }

    public String getFileName() {
        return fileName;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public int getTerrainElement() {
        return terrainElement;
    }

    public int getResourceElement() {
        return resourceElement;
    }

    public char[][] getRegions() {
        return regions;
    }

    public List<Area> getMountains() {
        return mountains;
    }

    public List<Area> getPits() {
        return pits;
    }
}