package com.codecool.marsexploration.data;

import java.util.List;
public record Planet(String name, int xyLength, List<Area> areas, int amountAreas) {
}