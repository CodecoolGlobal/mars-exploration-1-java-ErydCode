package com.codecool.marsexploration.ui;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class Input {
    private final Display display;

    public Input(Display display) {
        this.display = display;
    }

    public String getUserInput(String message) {
        System.out.println(message);
        display.inputMessage();
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        display.printLines();
        return input;
    }

    public Integer getNumericUserInput(String message) {
        Integer result = null;
        while (result == null) {
            System.out.println(message);
            display.inputMessage();
            Scanner scanner = new Scanner(System.in);
            try {
                result = scanner.nextInt();
            } catch (NoSuchElementException e) {
                System.out.println("Invalid input!");
            }
        }
        display.printLines();
        return result;
    }

    public int[] getRGB() {
        display.printSubtitle("Please enter in the next inputs one RGB Color.\n" +
                "https://htmlcolorcodes.com");
        int[] rgb = new int[3];
        getColorValue("Red", rgb, 0);
        getColorValue("Green", rgb, 1);
        getColorValue("Blue", rgb, 2);
        return rgb;
    }

    public void getColorValue(String colorStr, int[] rgb, int rgbIndex) {
        int color = -1;
        while (color < 0 || color > 255) {
            color = getNumericUserInput("Please enter the \"" + colorStr + " Color\". Just use a number between 0-255.");
        }
        rgb[rgbIndex] = color;
    }
}