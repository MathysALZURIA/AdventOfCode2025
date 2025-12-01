package adventOfCodeDay1.java;

import tools.FileTools;

import java.nio.file.Files;
import java.util.ArrayList;

public class Main {

    /**
     * Main method for Day 1.
     *
     * @param Args
     */
    public static void main(String[] Args) {

        // Get input value in ressources folder
        String input = FileTools.readFile("../ressources/input");

        // Vars
        final Integer STARTIGN_NUMBER = 50;
        Integer dial = 50;

        String[] instructions = input.lines().map(s -> new String[] {
                        s.substring(0, 1),
                        s.substring(2)
                }).toArray(String[]::new);
    }
}