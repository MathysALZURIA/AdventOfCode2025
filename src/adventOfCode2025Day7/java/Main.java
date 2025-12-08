package adventOfCode2025Day7.java;

import tools.FileTools;
import tools.PrintTools;

import java.util.*;

public class Main {

    public enum Vector {
        UP(new int[]{-1, 0}),
        RIGHT(new int[]{0, 1}),
        DOWN(new int[]{1, 0}),
        LEFT(new int[]{0, -1}),
        ;

        private final int[] vector;

        Vector(int[] vector) {
            this.vector = vector;
        }

        public int[] getVector() {
            return vector;
        }

        public int getX() {
            return vector[0];
        }

        public int getY() {
            return vector[1];
        }

        @Override
        public String toString() {
            return switch (this) {
                case UP -> "↑";
                case RIGHT -> "→";
                case DOWN -> "↓";
                case LEFT -> "←";
            };
        }
    }

    public enum State {
        START("S"),
        SPLITTER("^"),
        BEAM("|"),
        EMPTY("."),
        ;

        private final String symbol;

        State(String symbol) {
            this.symbol = symbol;
        }

        public String getSymbol() {
            return symbol;
        }

        public static State fromSymbol(String s) {
            return switch (s) {
                case "S" -> START;
                case "^" -> SPLITTER;
                case "|" -> BEAM;
                case "." -> EMPTY;
                default -> EMPTY;
            };
        }

        @Override
        public String toString() {
            return symbol;
        }
    }

    public static class Case {

        /**
         * State of the case
         */
        State state;

        /**
         * Quantity of content in the case
         */
        long quantity;

        /**
         * neighbours: list of neighbouring Cases
         */
        Map<Vector, Case> neighbours;

        /**
         * Constructor for Case.
         *
         * @param state
         */
        public Case(State state) {
            this.state = state;
            this.quantity = 1;
            this.neighbours = new HashMap<>();
        }

        /**
         * Adds a neighbouring case.
         *
         * @param neighbour
         */
        public void addNeighbour(Vector vector, Case neighbour) {
            this.neighbours.put(vector, neighbour);
        }

        /**
         * Gets the content of the case.
         *
         * @return
         */
        public State getState() {
            return state;
        }

        public void setState(State state, long quantity) {
            if (this.state == state) {
                this.quantity += quantity;
            } else {
                this.state = state;
                this.quantity = quantity;
            }
        }

        /**
         * Gets the quantity of content in the case.
         */
        public long getQuantity() {
            return quantity;
        }

        /**
         * Gets the neighbouring cases.
         *
         * @return
         */
        public Map<Vector, Case> getNeighbours() {
            return neighbours;
        }

        @Override
        public String toString() {
            String string = "";
            string += "cState=" + state + ", neighbours=[ ";
            for (Vector v : neighbours.keySet()) {
                string += v.toString() + ":" + neighbours.get(v).getState().toString() + " ";
            }
            string += "]";
            return string;
        }
    }

    /**
     * Main method for Day 7.
     *
     * @param args
     */
    public static void main(String[] args) {

        // Print all Vector values and toString representations
        System.out.println("Vectors:");
        for (Vector vector : Vector.values()) {
            System.out.println(vector + " -> " + Arrays.toString(vector.getVector()) + ", toString: " + vector.toString());
        }

        // Print all State values
        System.out.println("\nStates:");
        for (State state : State.values()) {
            System.out.println(state + " -> " + state.getSymbol() + ", toString: " + state.toString());
        }

        // Get input value in ressources folder
        String input = FileTools.readFile("src\\adventOfCode2025Day7" +
                "\\resources\\input");
        List<List<Case>> grid = input.lines()
                .map(s -> Arrays.stream(s.split(""))
                        .map(s2 -> new Case(State.fromSymbol(s2))).toList())
                .toList();


        // Link neighbours and Print grid Graphic representation
        System.out.println("\n---- Grid Graphic Representation ----\n");

        List<List<Case>> grid2 = new ArrayList<>(grid);
        for (int x = 0; x < grid.size(); x++) {
            List<Case> line = grid.get(x);
            for (int y = 0; y < line.size(); y++) {
                Case c = line.get(y);

                // Add neighbours
                for (Vector vector : Vector.values()) {
                    int neighbourX = x + vector.getX();
                    int neighbourY = y + vector.getY();
                    if (neighbourX >= 0
                            && neighbourX < grid.size()
                            && neighbourY >= 0
                            && neighbourY < line.size()) {
                        c.addNeighbour(vector, grid.get(neighbourX).get(neighbourY));
                    }
                }
                System.out.print(c.getState().toString());
            }
            System.out.println();
        }

        // Vars
        int countOfSplit = 0;
        long countOfTimeLines = 0;

        // Algo Part 1
        System.out.println("\n---- Propagation of Beams ----\n");

        // Propagation of beams from START cases for Part 1
        for (int x = 0; x < grid2.size()-1; x++) {
            for (int y = 0; y < grid2.get(x).size(); y++) {
                Case c = grid2.get(x).get(y);
                // If it is the START location or a BEAM, the BEAM goes down
                if (c.getState() == State.START || c.getState() == State.BEAM) {
                    Case downNeighbour = c.getNeighbours().get(Vector.DOWN);

                    // If the down neighbour is a SPLITTER, split the BEAM right and left
                    // Otherwise, just set it to BEAM
                    if (downNeighbour.getState() == State.SPLITTER) {
                        downNeighbour.getNeighbours().get(Vector.RIGHT).setState(State.BEAM, c.getQuantity());
                        downNeighbour.getNeighbours().get(Vector.LEFT).setState(State.BEAM, c.getQuantity());
                        countOfSplit++;
                    } else {
                        downNeighbour.setState(State.BEAM, c.getQuantity());
                    }
                }
                System.out.print(c.getState().toString());
            }
            System.out.println();
        }

        // Algo Part 2
        System.out.println("\n---- Count of TimeLines ----\n");

        // Count the number of TIME LINES (BEAMs in the last line)
        for (Case outputCase : grid2.getLast()) {
            System.out.print(outputCase.getState().toString());
            if (outputCase.getState() == State.BEAM) {
                System.out.print(outputCase.getQuantity());
                countOfTimeLines += outputCase.getQuantity();
            }
            System.out.println();
        }


        // Output
        PrintTools.printAnswer(1, 1,
                "Laboratories",
                Integer.toString(countOfSplit));

        PrintTools.printAnswer(1, 2,
                "Laboratories",
                Long.toString(countOfTimeLines));



    }
}
