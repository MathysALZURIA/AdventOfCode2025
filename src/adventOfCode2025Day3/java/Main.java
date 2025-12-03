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
        public String getTotalJoltage() {

            int battery1 = 0;
            int battery2 = 0;

            for (int i = 0; i < batteries.size(); i++) {
                int battery = batteries.get(i);
                if (battery1 == 9 && battery2 == 9) {
                    break;
                } else {
                    if (battery > battery1 && i < batteries.size() - 1) {
                        battery1 = battery;
                        battery2 = 0;
                    } else if (battery > battery2) {
                        battery2 = battery;
                    }
                }
            }
            System.out.println(batteries);
            System.out.println("battery 1 : " + battery1
                    +  " - battery 2 : " + battery2
                    + " - Concat : " + Integer.toString(battery1).concat(Integer.toString(battery2)));
            return Integer.toString(battery1).concat(Integer.toString(battery2));
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
        int totalJoltage = 0;

        for (Bank bank : banks) {
            totalJoltage += Integer.parseInt(bank.getTotalJoltage());
        }

        // Output
        PrintTools.printAnswer(3, 1,
                "Lobby",
                Integer.toString(totalJoltage));


    }
}
