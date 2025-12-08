package adventOfCode2025Day8.java;

import tools.FileTools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static class Circuit implements Comparable<Circuit> {

        private List<JunctionBox> junctionBoxList;

        public Circuit() {
            this.junctionBoxList = new ArrayList<>();
        }

        public void addJunctionBox(JunctionBox junctionBox) {
            this.junctionBoxList.add(junctionBox);
            junctionBox.addCircuit(this);
        }

        public int getNumberOfJunctionBoxes() {
            return this.junctionBoxList.size();
        }

        @Override
        public int compareTo(Circuit o) {
            return Integer.compare(this.getNumberOfJunctionBoxes(), o.getNumberOfJunctionBoxes());
        }
    }

    public static class JunctionBox {

        private long x;
        private long y;
        private long z;
        private Circuit circuit;

        public JunctionBox(long x, long y, long z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public long getX() {
            return x;
        }

        public long getY() {
            return y;
        }

        public long getZ() {
            return z;
        }

        public Circuit getCircuit() {
            return circuit;
        }

        public boolean hasCircuit() {
            return this.circuit != null;
        }

        public long getDistanceTo(JunctionBox other) {
            return Double.doubleToLongBits(Math.sqrt(
                    Math.pow(this.x - other.x, 2) +
                            Math.pow(this.y - other.y, 2) +
                            Math.pow(this.z - other.z, 2)
            ));
        }

        public void addCircuit(Circuit circuit) {
            this.circuit = circuit;
        }

        @Override
        public String toString() {
            return "[X=" + x + ", Y=" + y + ", Z=" + z + "]";
        }
    }

    /**
     * Main method for Day 8.
     *
     * @param args
     */
    public static void main(String[] args) {
        // Get input value in ressources folder
        String input = FileTools.readFile("src\\adventOfCode2025Day8" + "\\resources\\input");
        List<JunctionBox> lonelyJunctionBoxes = input.lines().map(s -> {
            String[] coords = s.split(",");
            return new JunctionBox(Long.parseLong(coords[0]), Long.parseLong(coords[1]), Long.parseLong(coords[2]));
        }).toList();

        // Vars
        List<Circuit> circuits = new ArrayList<>();

        // Algo Part 1
        while (!lonelyJunctionBoxes.isEmpty()) {
            JunctionBox currentJunctionBox = lonelyJunctionBoxes.get(0);
            Circuit newCircuit = new Circuit();
            newCircuit.addJunctionBox(currentJunctionBox);
            lonelyJunctionBoxes.remove(currentJunctionBox);

            boolean foundNew;
            do {
                foundNew = false;
                List<JunctionBox> toAdd = new ArrayList<>();
                for (JunctionBox jb1 : newCircuit.junctionBoxList) {
                    for (JunctionBox jb2 : lonelyJunctionBoxes) {
                        if (jb1.getDistanceTo(jb2) <= 10000) {
                            toAdd.add(jb2);
                            foundNew = true;
                        }
                    }
                }
                for (JunctionBox jb : toAdd) {
                    newCircuit.addJunctionBox(jb);
                    lonelyJunctionBoxes.remove(jb);
                }
            } while (foundNew);

            circuits.add(newCircuit);


        }
    }
