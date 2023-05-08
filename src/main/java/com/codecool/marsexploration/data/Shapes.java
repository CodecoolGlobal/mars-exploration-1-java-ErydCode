package com.codecool.marsexploration.data;

public enum Shapes {
    MOUNTAIN("^",40, 6),
    PIT("#",20, 6),
    MINERAL("*",10, 7),
    WATER("~",10, 5),
    EMPTY(" ",20, 15);

    private final double totalAreaUsed;
    private final String shape;
    private final int size;



    Shapes(String shape, double totalAreaUsed, int size) {
        this.shape = shape;
        this.totalAreaUsed = totalAreaUsed;
        this.size = size;
    }

    public double getTotalAreaUsed(){
        return totalAreaUsed;
    }

    public String getShape() {
        return shape;
    }
}
