package com.codecool.marsexploration.data;

public enum ShapeTypes {
    MOUNTAIN("^"),
    PIT("#"),
    MINERAL("*"),
    WATER("~"),
    EMPTY(" ");

    private final String shape;

    ShapeTypes(String shape) {
        this.shape = shape;
    }

    public String getShape() {
        return shape;
    }
}