package adventOfCode2025Day8.java;

import tools.FileTools;
import tools.PrintTools;

import java.util.*;
import java.util.stream.Stream;

public class Main {

    public static class Circuit implements Comparable<Circuit> {

        private List<JunctionBox> junctionBoxList;

        public Circuit() {
            this.junctionBoxList = new LinkedList<>();
        }

        public void addJunctionBox(JunctionBox junctionBox) {
            this.junctionBoxList.add(junctionBox);
            junctionBox.addCircuit(this);
        }

        public int getNumberOfJunctionBoxes() {
            return this.junctionBoxList.size();
        }

        public List<JunctionBox> getJunctionBoxList() {
            return junctionBoxList;
        }

        public void setJunctionBoxList(List<JunctionBox> junctionBoxList) {
            this.junctionBoxList = junctionBoxList;
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
        Map<JunctionBox[], Long> distances = new HashMap<>();


        // Calculate all the distances between the JunctionBoxes
        for (int i = 0; i < lonelyJunctionBoxes.size(); i++) {
            JunctionBox junctionBox = lonelyJunctionBoxes.get(i);
            for (int j = i+1; j < lonelyJunctionBoxes.size()-1; j++) {
                JunctionBox junctionBox2 = lonelyJunctionBoxes.get(j);
                if (junctionBox != junctionBox2
                        && !distances.containsKey(new JunctionBox[]{junctionBox2, junctionBox})) {
                    distances.put(new JunctionBox[]{junctionBox, junctionBox2}, junctionBox.getDistanceTo(junctionBox2));
                }
            }
        }

        // Trie de la Map
        Map<JunctionBox[], Long> distancesSorted = distances.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(java.util.stream.Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b) -> a,
                        java.util.LinkedHashMap::new
                ));

        // Algo Part 1

        // For the 1000 firsts shortest distances
        Set<JunctionBox[]> junctionBoxPairs = distancesSorted.keySet();
//        for (int i = 0; i < 1000; i++) {
//            System.out.println("Circuits size: " + circuits.size() + " | Processing pair " + (i+1) + "/1000");
//            Circuit circuit = new Circuit();
//            JunctionBox jb1 = junctionBoxPairs.stream().toList().get(i)[0];
//            JunctionBox jb2 = junctionBoxPairs.stream().toList().get(i)[1];
//
//            // If both JunctionBoxes don't have a Circuit yet
//            if (!jb1.hasCircuit() && !jb2.hasCircuit()) {
//                circuit.addJunctionBox(jb1);
//                circuit.addJunctionBox(jb2);
//                circuits.add(circuit);
//            } else if (jb1.hasCircuit() && !jb2.hasCircuit()) {
//                // If only the first JunctionBox has a Circuit
//                jb1.getCircuit().addJunctionBox(jb2);
//            } else if (!jb1.hasCircuit() && jb2.hasCircuit()) {
//                // If only the second JunctionBox has a Circuit
//                jb2.getCircuit().addJunctionBox(jb1);
//            } else if (jb1.hasCircuit() && jb2.hasCircuit() && jb1.getCircuit() != jb2.getCircuit()) {
//                // If both JunctionBoxes have a Circuit and they are different
//                Circuit circuit1 = jb1.getCircuit();
//                Circuit circuit2 = jb2.getCircuit();
//                // Merge circuit2 into circuit1
//                for (JunctionBox jb : circuit2.getJunctionBoxList()) {
//                    circuit1.addJunctionBox(jb);
//                }
//                circuits.remove(circuit2);
//            }
//        }
//
//        // Sort circuits by number of JunctionBoxes
//        Collections.sort(circuits);
//
//        // multiply the 3 largest circuits' sizes
//        int resultPart1 =
//                circuits.getLast().getNumberOfJunctionBoxes() *
//                circuits.get(circuits.size() - 2).getNumberOfJunctionBoxes() *
//                circuits.get(circuits.size() - 3).getNumberOfJunctionBoxes();

        // Part 2

        // Keep continue adding the shortest distances until all JunctionBoxes are connected
        List<Circuit> circuits2 = new ArrayList<>();
        JunctionBox[] lastPair = new JunctionBox[2];
        for (int i = 0; i < junctionBoxPairs.size(); i++) {
            Circuit circuit = new Circuit();
            JunctionBox jb1 = junctionBoxPairs.stream().toList().get(i)[0];
            JunctionBox jb2 = junctionBoxPairs.stream().toList().get(i)[1];

            // If both JunctionBoxes don't have a Circuit yet
            if (!jb1.hasCircuit() && !jb2.hasCircuit()) {
                circuit.addJunctionBox(jb1);
                circuit.addJunctionBox(jb2);
                circuits2.add(circuit);
            } else if (jb1.hasCircuit() && !jb2.hasCircuit()) {
                // If only the first JunctionBox has a Circuit
                jb1.getCircuit().addJunctionBox(jb2);
            } else if (!jb1.hasCircuit() && jb2.hasCircuit()) {
                // If only the second JunctionBox has a Circuit
                jb2.getCircuit().addJunctionBox(jb1);
            } else if (jb1.hasCircuit() && jb2.hasCircuit() && jb1.getCircuit() != jb2.getCircuit()) {
                // If both JunctionBoxes have a Circuit and they are different
                Circuit circuit1 = jb1.getCircuit();
                Circuit circuit2 = jb2.getCircuit();
                // Merge circuit2 into circuit1
                for (JunctionBox jb : circuit2.getJunctionBoxList()) {
                    circuit1.addJunctionBox(jb);
                }
                circuits2.remove(circuit2);
            }

            if (circuits2.size() == 1 && circuits2.getFirst() != circuit) {
                // All JunctionBoxes are connected
                lastPair = new JunctionBox[]{jb1, jb2};
                System.out.println("Circuits size: " + circuits2.size() + " | Processing distance index: " + i + " size of the first circuits" + circuits2.getFirst().getNumberOfJunctionBoxes());
                break;
            }
            System.out.println("Circuits size: " + circuits2.size() + " | Processing distance index: " + i);
        }


        System.out.println(circuits2);
        System.out.println(Arrays.toString(lastPair));


        // Multiply the X values of the last pair of JunctionBoxes added
        JunctionBox jb1 = lastPair[0];
        JunctionBox jb2 = lastPair[1];
        long resultPart2 = jb1.getX() * jb2.getX();


        // Output Part 1
//        PrintTools.printAnswer(8, 1,
//                "Playground ",
//                Integer.toString(resultPart1));

        PrintTools.printAnswer(8, 2,
                "Playground ",
                Long.toString(resultPart2));
    }
}
