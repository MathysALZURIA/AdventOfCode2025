package adventOfCode2025Day9.java;

import tools.FileTools;
import tools.PrintTools;

import java.util.Arrays;
import java.util.List;

public class Main {

    /**
     * Main method for Day 9.
     *
     * @param args
     */
    public static void main(String[] args) {

        // Get input value in ressources folder
        String input = FileTools.readFile("src\\adventOfCode2025Day9" +
                "\\resources\\input");
        List<long[]> coords = input.lines()
                .map(s -> Arrays.stream(s.split(","))
                        .mapToLong(Long::parseLong)
                        .toArray())
                .toList();

        // Vars
        long maxArea = 0;

        // Algo

        // Part 1
        for (int i = 0; i < coords.size(); i++) {
            for (int j = 0; j < coords.size(); j++) {
                if (i != j) {
                    long area = calculArea(coords.get(i), coords.get(j));
                    if (area > maxArea) {
                        maxArea = area;
                    }
                }
            }
        }

        // Output
        PrintTools.printAnswer(9, 1,
                "Movie Theater",
                Long.toString(maxArea));
    }

    public static long calculArea(long[] coord1, long[] coord2) {
        return Math.abs(coord1[0] - coord2[0] + 1) * Math.abs(coord1[1] - coord2[1] + 1);
    }
}
