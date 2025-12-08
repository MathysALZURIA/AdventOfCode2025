package adventOfCode2025Day2.java;

import tools.FileTools;
import tools.PrintTools;

import java.util.*;

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

        /**
         * Find invalid IDs in the range for part 1
         * An invalid ID is defined as an ID with an even number of digits
         * where the first half of the digits are the same as the second half
         *
         * @return
         */
        public List<Long> getInvalidIDPart1() {
            int minLength = Long.toString(min).length();
            int maxLength = Long.toString(max).length();
            List<Long> invalidIDs = new ArrayList<>();
            long sumOfInvalidIDs = 0;

            // If max has an even number of digits, it possibly contains an invalid ID
            if (minLength % 2 == 0 || maxLength % 2 == 0) {
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
            }
            return invalidIDs;
        }

        /**
         * Find invalid IDs in the range for part 2
         * An invalid ID is defined as an ID where it is composed by repeatable sequences
         * of digits. The length of the sequence must be a divisor of the total length of the ID.
         * For example, 1212 is invalid because it is composed of the sequence "12" repeated twice.
         * 123123 is invalid because it is composed of the sequence "123" repeated twice.
         * 123123123 is invalid because it is composed of the sequence "123" repeated three times.
         * 1234 is valid because it cannot be composed of repeatable sequences.
         * 1231234 is valid because it cannot be composed of repeatable sequences
         *
         * @return
         */
        public Set<Long> getInvalidIDPart2() {
            Set<Long> invalidIDs = new HashSet<>();
            int lastLength = Long.toString(min).length();
            List<Integer> divisors = getDivisors(min);
            Set<Long> potentialInvalids = new HashSet<>();
            for (long id = min; id <= max; id++) {
                if (Long.toString(id).length() > lastLength) {
                    lastLength = Long.toString(id).length();
                    divisors = getDivisors(id);
                }
                String idStr = Long.toString(id);

                for (int divisor : divisors) {
                    String sequence = idStr.substring(0, divisor);
                    StringBuilder repeatedSequence = new StringBuilder();
                    int repeatCount = idStr.length() / divisor;
                    for (int i = 0; i < repeatCount; i++) {
                        repeatedSequence.append(sequence);
                    }
                    potentialInvalids.add(Long.parseLong(repeatedSequence.toString()));
                }
            }
            System.out.println("Potential : " + potentialInvalids);
            for (long potentialInvalid : potentialInvalids) {
                if (contains(potentialInvalid)) {
                    invalidIDs.add(potentialInvalid);
                }
            }
            return invalidIDs;
        }

        public List<Integer> getDivisors(long id) {
            List<Integer> divisors = new ArrayList<>();
            int idLength = Long.toString(id).length();
            for (int i = 1; i <= idLength / 2; i++) {
                if (idLength % i == 0) {
                    divisors.add(i);
                }
            }
            return divisors;
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
                "\\resources\\input");
        List<Range> ranges = Arrays.stream(input.split(",")).map(s -> {
            String[] parts = s.split("-");
            long min = Math.min(Long.parseLong(parts[0].strip()), Long.parseLong(parts[1].strip()));
            long max = Math.max(Long.parseLong(parts[0].strip()), Long.parseLong(parts[1].strip()));
            return new Range(min, max);
        }).toList();

        // vars
        long sumOfInvalidIDsPart1 = 0;
        long sumOfInvalidIDsPart2 = 0;

        System.out.println("Ranges : " + ranges.toString());

        // Algorithm Part 1
        for (Range range : ranges) {
            long somme = range.getInvalidIDPart1().stream().reduce(0L, Long::sum);
            System.out.println("--------- : " + somme);
            sumOfInvalidIDsPart1 += somme;
            System.out.println("----------- sumOfInvalidIDs : " + sumOfInvalidIDsPart1);
        }

        // Algorithm Part 2
        for (Range range : ranges) {
            long somme = range.getInvalidIDPart2().stream().reduce(0L, Long::sum);
            System.out.println("--------- : " + somme);
            sumOfInvalidIDsPart2 += somme;
            System.out.println("----------- sumOfInvalidIDs : " + sumOfInvalidIDsPart2);
        }

        // Output
        PrintTools.printAnswer(2, 1,
                "Gift Shop",
                Long.toString(sumOfInvalidIDsPart1));

        PrintTools.printAnswer(2, 2,
                "Gift Shop",
                Long.toString(sumOfInvalidIDsPart2));


    }
}
