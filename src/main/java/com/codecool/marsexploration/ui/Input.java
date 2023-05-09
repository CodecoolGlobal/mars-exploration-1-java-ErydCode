package com.codecool.marsexploration.ui;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class Input {

    public String getUserInput(String message) {
        System.out.println(message);
        System.out.print("Your input is: ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public Integer getNumericUserInput(String message) {
        Integer result = null;
        while (result == null) {
            System.out.println(message);
            System.out.print("Your input is: ");
            Scanner scanner = new Scanner(System.in);
            try {
                result = scanner.nextInt();
            } catch (NoSuchElementException e) {
                System.out.println("Invalid input!");
            }
        }
        return result;
    }
}
