package com.codecool.marsexploration.ui;

import java.util.Map;

public class Display {
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_DEFAULT = "\u001B[0m";
    private final int displayLength = 82;

    public void printMenu(Map<Integer, String> menuItems) {
        for (Map.Entry<Integer, String> item : menuItems.entrySet()) {
            String itemName = item.getValue().trim();
            if (item.getValue().contains(".txt") || item.getValue().contains("-")) {
                String convertItemName = convertFileName(itemName);
                itemName = convertItemName.substring(0, 1).toUpperCase() + convertItemName.substring(1);
            }
            System.out.println("Enter " + item.getKey() + " to get " + "\"" + itemName + "\".");
        }
    }

    public String convertFileName(String fileName) {
        String bookNameWithoutTxt = getFileNameWithoutTxt(fileName);
        String[] bookNameSplit = bookNameWithoutTxt.split("-");
        return String.join(" ", bookNameSplit);
    }

    public String getFileNameWithoutTxt(String fileName) {
        return fileName.replace(".txt", "");
    }

    public void message(String message) {
        System.out.println(message);
    }

    public void printTitle(String title) {
        System.out.println();
        String fillWithSpace = " ".repeat((displayLength - title.length()) / 2);
        System.out.println("✧".repeat(displayLength));
        System.out.println(fillWithSpace + title.toUpperCase() + fillWithSpace);
        System.out.println("✧".repeat(displayLength));
    }

    public void printSubtitle(String subtitle) {
        String fillWithSpace = " ".repeat((displayLength - subtitle.length()) / 2);
        System.out.println(fillWithSpace + subtitle.toUpperCase() + fillWithSpace);
        System.out.println("✧".repeat(displayLength));
    }

    public void printLines() {
        System.out.println("-".repeat(displayLength));
    }

    public void printEndLines() {
        System.out.println("✧".repeat(displayLength));
    }

    public void errorMessage(String message) {
        System.out.println(ANSI_RED + message + ANSI_DEFAULT);
    }
}