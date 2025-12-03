package adventOfCode2025Day3.java;

import tools.FileTools;
import tools.PrintTools;

import java.util.Arrays;
import java.util.List;

public class Main {

    public static class Bank {

        private List<Integer> batteries;

        public Bank(List<Integer> batteries) {
            this.batteries = batteries;
        }

        /**
         * Calculate the total energy stored in the bank.
         * <p>
         * Concatenation of 2 max batteries. (keep the order)
         *
         * @return
         */
        public String getTotalJoltage(int nbBatteriesNeeded) {

            int[] batteriesUsed = new int[nbBatteriesNeeded];

            System.out.println(batteries.toString());
            int[] bestCombinaison = getBestBatteriesCombination(batteriesUsed, 0, 0);
            System.out.println(" Best combinaison : " + Arrays.toString(bestCombinaison));

            StringBuilder result = new StringBuilder();
            for (int battery : bestCombinaison) {
                result.append(Integer.toString(battery));
            }

            return result.toString();
        }

        /**
         * Recursive method to get the better batteries combination.
         */
        public int[] getBestBatteriesCombination(int[] batteriesUsed, int indexNextBattery, int indexNextPosition) {
            if (indexNextBattery == batteriesUsed.length) {
                return batteriesUsed;
            } else {
                int nextPos = indexNextPosition;
                for (int i = indexNextPosition; i <= batteries.size() - batteriesUsed.length + indexNextBattery; i++) {
                    System.out.println("batteriesSize : " + batteries.size() + " | batteriesUsed.length : " + batteriesUsed.length + " | indexNextBattery : " + indexNextBattery);
                    System.out.println("range i : " + i + " to " + (batteries.size() - batteriesUsed.length + indexNextBattery));
                    int battery = batteries.get(i);
                    if (battery > batteriesUsed[indexNextBattery]) {
                        System.out.println("Using battery " + battery + " (pos : " + i + ") for position " + indexNextBattery);
                        batteriesUsed[indexNextBattery] = battery;
                        nextPos = i + 1;
                    }
                    }

                System.out.println(indexNextPosition);
                return getBestBatteriesCombination(batteriesUsed, indexNextBattery + 1, nextPos);
            }
        }
    }

    /**
     * Main method for Day 3.
     *
     * @param Args
     */
    public static void main(String[] Args) {

        // Get input value in ressources folder
        String input = FileTools.readFile("src\\adventOfCode2025Day3" +
                "\\ressources\\input");
        List<Bank> banks = input.lines()
                .map(s -> new Bank(
                        Arrays.stream(s.split(""))
                                .map(Integer::parseInt)
                                .toList()))
                .toList();

        // Vars
        long totalJoltagePart1 = 0;
        long totalJoltagePart2 = 0;

        for (Bank bank : banks) {
            totalJoltagePart1 += Long.parseLong(bank.getTotalJoltage(2));
        }

        for (Bank bank : banks) {
            totalJoltagePart2 += Long.parseLong(bank.getTotalJoltage(12));
        }

        // Output
        PrintTools.printAnswer(3, 1,
                "Lobby",
                Long.toString(totalJoltagePart1));

        PrintTools.printAnswer(3, 1,
                "Lobby",
                Long.toString(totalJoltagePart2));


    }
}
