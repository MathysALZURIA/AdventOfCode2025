package adventOfCode2025Day4.java;

import tools.FileTools;
import tools.PrintTools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Main {

    public static class Case {

        /**
         * content of the case
         */
        String content;

        /**
         * neighbours: list of neighbouring Cases
         */
        List<Case> neighbours;

        /**
         * Constructor for Case.
         *
         * @param content
         */
        public Case(String content) {
            this.content = content;
            this.neighbours = new ArrayList<>();
        }

        /**
         * Adds a neighbouring case.
         *
         * @param neighbour
         */
        public void addNeighbour(Case neighbour) {
            this.neighbours.add(neighbour);
        }

        /**
         * Checks if the case has content.
         *
         * @return
         */
        public boolean hasContent() {
            return content.equals("@");
        }

        /**
         * Gets the content of the case.
         *
         * @return
         */
        public String getContent() {
            return content;
        }

        /**
         * Removes the content of the case.
         */
        public void removeContent() {
            this.content = ".";
        }

        /**
         * Gets the neighbouring cases.
         *
         * @return
         */
        public List<Case> getNeighbours() {
            return neighbours;
        }

        /**
         * Counts the number of neighbouring cases that have content.
         *
         * @return
         */
        public int countNeighboursWithContent() {
            int count = 0;
            for (Case neighbour : neighbours) {
                count += neighbour.hasContent()
                        ? 1
                        : 0;
            }
            return count;
        }

        public String toString() {
            return "Case(content=" + content + ")";
        }
    }

    /**
     * Main method for Day 3.
     *
     * @param Args
     */
    public static void main(String[] Args) {

        // Get input value in ressources folder
        String input = FileTools.readFile("src\\adventOfCode2025Day4" +
                "\\ressources\\input");
        List<List<Case>> grid = input.lines()
                .map(s -> Arrays.stream(s.split(""))
                        .map(Case::new).toList())
                .toList();

        // Vars
        int countOfRollsPart1 = 0;
        int countOfRollsPart2 = 0;


        // Algo
        for (int x = 0; x < grid.size(); x++) {
            List<Case> line = grid.get(x);
            for (int y = 0; y < line.size(); y++) {
                Case c = line.get(y);

                // Add neighbours
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        if (i != 0 || j != 0) {
                            int neighbourX = x + i;
                            int neighbourY = y + j;
                            if (neighbourX >= 0
                                    && neighbourX < line.size()
                                    && neighbourY >= 0
                                    && neighbourY < grid.size()) {
                                c.addNeighbour(grid.get(neighbourX).get(neighbourY));
                            }
                        }
                    }
                }

                // If I have content and I have fewer than 4 neighbours with content
                countOfRollsPart1 += c.hasContent() && c.countNeighboursWithContent() < 4
                        ? 1
                        : 0;

                // Print grid
                System.out.print((c.hasContent() && c.countNeighboursWithContent() < 4) ? "x" : c.getContent());
            }
            System.out.println();
        }



        // Output
        PrintTools.printAnswer(4, 1,
                "Printing Department,",
                        Integer.toString(countOfRollsPart1));
    }
}
