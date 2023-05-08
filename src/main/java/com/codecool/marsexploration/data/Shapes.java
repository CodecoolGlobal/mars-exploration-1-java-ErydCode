package com.codecool.marsexploration.data;

public enum Shapes {
    MOUNTAIN("^",40),
    PIT("#",20),
    MINERAL("*",10),
    WATER("~",10),
    EMPTY(" ",20);

    private final double totalAreaUsed;
    private final String shape;

    Shapes(String shape, double totalAreaUsed) {
        this.shape = shape;
        this.totalAreaUsed = totalAreaUsed;
    }

    public double getTotalAreaUsed(){
        return totalAreaUsed;
    }

    public String getShape() {
        return shape;
    }
}
