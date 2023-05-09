package com.codecool.marsexploration.data;

public enum TerrainTypes {
    MOUNTAIN("^"),
    PIT("#"),
    MINERAL("*"),
    WATER("~"),
    EMPTY(" ");

    private final String symbol;

    TerrainTypes(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}