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
}