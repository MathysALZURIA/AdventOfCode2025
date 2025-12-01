package adventOfCodeDay1.java;

import tools.FileTools;
import tools.PrintTools;

import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Main {

    /**
     * Main method for Day 1.
     *
     * @param Args
     */
    public static void main(String[] Args) {

        // Get input value in ressources folder
        String input = FileTools.readFile("src\\adventOfCodeDay1" +
                "\\ressources\\input");
        List<String[]> instructions = input.lines().map(s -> new String[]{
                s.substring(0, 1),
                s.substring(1)
        }).toList();

        // Vars
        final int STARTIGN_NUMBER = 50;
        int dial = STARTIGN_NUMBER;
        int counterOf0 = 0;
        int counterOf0PassingBy = 0;

        // Algorithm Part 1
        for (String[] step : instructions) {
            System.out.println(Arrays.toString(step));
            if (step[0].equals("L")) {
                dial -= Integer.parseInt(step[1]);
            } else if (step[0].equals("R")) {
                dial += Integer.parseInt(step[1]);
            }
            counterOf0 += dial % 100 == 0 ? 1 : 0;
        }

        dial = STARTIGN_NUMBER;

        // Algorithm Part 2
        for (String[] step : instructions) {
            System.out.println("Dial : " + dial + " - Instruction : " + Arrays.toString(step));
            if (step[0].equals("L")) {
                int value = Integer.parseInt(step[1]);
                while ((dial - value) < 0) {
                    System.out.println("Before passing by 0: dial = " + dial + ", value = " + value);
                    value -= dial;
                    dial = 100;
                    counterOf0PassingBy++;
                    System.out.println("After passing by 0: dial = " + dial + ", value = " + value);
                }
            } else if (step[0].equals("R")) {
                int value = Integer.parseInt(step[1]);
                while ((dial + value) > 100) {
                    System.out.println("Before passing by 0: dial = " + dial + ", value = " + value);
                    value -= (100 - dial);
                    dial = 0;
                    counterOf0PassingBy++;
                    System.out.println("After passing by 0: dial = " + dial + ", value = " + value);
                }
            }
            System.out.println("Counter of passing by 0: " + counterOf0PassingBy);
        }

        // Output
        PrintTools.printAnswer(1, 1,
                "Secret Entrance",
                String.valueOf(counterOf0));

        PrintTools.printAnswer(1, 2,
                "Secret Entrance",
                String.valueOf(counterOf0PassingBy));

    }
}