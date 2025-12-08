package adventOfCode2025Day1.java;

import tools.FileTools;
import tools.PrintTools;

import java.util.Arrays;
import java.util.List;

public class Main {

    /**
     * Main method for Day 1.
     *
     * @param args
     */
    public static void main(String[] args) {

        // Get input value in ressources folder
        String input = FileTools.readFile("src\\adventOfCode2025Day1" +
                "\\resources\\input");
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
            int value = Integer.parseInt(step[1]);
            int turns = value / 100;
            int rest = value % 100;
            int newDial = dial;
            counterOf0PassingBy += turns;
            if (step[0].equals("L")) {
                newDial = (dial - rest + 100) % 100;
                counterOf0PassingBy += (newDial > dial || newDial == 0) && dial != 0 ? 1 : 0;
            } else if (step[0].equals("R")) {
                newDial = (dial + rest) % 100;
                counterOf0PassingBy += newDial < dial ? 1 : 0;
            }
            dial = newDial;
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