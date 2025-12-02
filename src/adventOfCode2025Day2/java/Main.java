package adventOfCode2025Day2.java;

import tools.FileTools;
import tools.PrintTools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static class Range {
        long min;
        long max;

        public Range(long min, long max) {
            this.min = min;
            this.max = max;
        }

        public boolean contains(long value) {
            return value >= min && value <= max;
        }

        public List<Long> getInvalidID() {
            int minLength = Long.toString(min).length();
            int maxLength = Long.toString(max).length();
            List<Long> invalidIDs = new ArrayList<>();
            long sumOfInvalidIDs = 0;

            // If max has an even number of digits, it possibly contains an invalid ID
            if (maxLength % 2 == 0) {
                long minimum = min;
                System.out.println("min : " + minimum + " | max : " + max);

                // If min has an odd number of digits, we start testing from the next unit of measurement
                if (minLength % 2 != 0) {
                    minimum = (long) Math.pow(10, minLength);
                    System.out.println("Adjusting minimum to : " + minimum);
                }
                System.out.println("min : " + minimum + " | max : " + max);

                // algorithm to find invalid IDs
                String halfMinStr = Long.toString(minimum).substring(0, maxLength / 2);
                long halfMinInt = Long.parseLong(halfMinStr);
                long testingId = Long.parseLong(halfMinStr.concat(halfMinStr));

                System.out.println("testingId : " + testingId);
                if (contains(testingId)) {
                    System.out.println("Adding invalid ID : " + testingId);
                    invalidIDs.add(testingId);
                }
                halfMinInt++;
                halfMinStr = Long.toString(halfMinInt);
                testingId = Long.parseLong(halfMinStr.concat(halfMinStr));
                System.out.println("testingId : " + testingId);
                while (contains(testingId)) {
                    System.out.println("Adding invalid ID : " + testingId);
                    invalidIDs.add(testingId);
                    halfMinInt++;
                    halfMinStr = Long.toString(halfMinInt);
                    testingId = Long.parseLong(halfMinStr.concat(halfMinStr));
                    System.out.println("testingId : " + testingId);
                }
            } else {
                System.out.println("min : " + min + " | max : " + max);
                System.out.println("No invalid IDs in range (odd) : " + this.toString());
            }

            return invalidIDs;
        }

        @Override
        public String toString() {
            return "{" +
                    "min=" + min +
                    ", max=" + max +
                    '}';
        }
    }

    /**
     * Main method for Day 2
     *
     * @param args
     */
    public static void main(String[] args) {

        // Get input value in ressources folder
        String input = FileTools.readFile("src\\adventOfCode2025Day2" +
                "\\ressources\\input");
        List<Range> ranges = Arrays.stream(input.split(",")).map(s -> {
            String[] parts = s.split("-");
            long min = Math.min(Long.parseLong(parts[0].strip()), Long.parseLong(parts[1].strip()));
            long max = Math.max(Long.parseLong(parts[0].strip()), Long.parseLong(parts[1].strip()));
            return new Range(min, max);
        }).toList();

        // vars
        long sumOfInvalidIDs = 0;

        System.out.println("Ranges : " + ranges.toString());

        // Algorithm Part 1
        for (Range range : ranges) {
            long somme = range.getInvalidID().stream().reduce(0L, Long::sum);
            System.out.println("--------- : " + somme);
            sumOfInvalidIDs += somme;
            System.out.println("----------- sumOfInvalidIDs : " + sumOfInvalidIDs);
        }

        // Output
        PrintTools.printAnswer(2, 1,
                "Gift Shop",
                Long.toString(sumOfInvalidIDs));


    }
}
