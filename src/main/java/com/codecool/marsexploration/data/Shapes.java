package com.codecool.marsexploration.data;

public enum Shapes {
    MOUNTAIN("^"),
    PIT("#"),
    MINERAL("*"),
    WATER("~"),
    EMPTY(" ");

    private final String shape;

    Shapes(String shape) {
        this.shape = shape;
    }

    public String getShape() {
        return shape;
    }
}