package adventOfCode2025Day6.java;

import tools.FileTools;
import tools.PrintTools;

import java.util.*;

public class Main {

    public static class Problem {

        List<List<String>> problem;
        int maxLength;
        String operation;

        public Problem(List<List<String>> problem, String operation) {
            this.problem = problem;
            this.maxLength = problem.getFirst().size();
            this.operation = operation;
        }

        public Long resolve(String method) {
            List<Long> numbers = getNumbersByMethod(method);
            System.out.print(method + " numbers: " + numbers);

            Long result = switch (operation) {
                case "+" -> numbers.stream().mapToLong(Long::longValue).sum();
                case "*" -> numbers.stream().reduce(1L, (a, b) -> a * b);
                default -> throw new IllegalArgumentException("Unknown operation: " + operation);
            };
            System.out.println(" result: " + result);
            return result;
        }

        public List<Long> getNumbersByMethod(String method) {
            List<Long> numbers = new ArrayList<>();
            if (method.equals("normal")) {
                for (List<String> line : problem) {
                    String number = "";
                    for (String integer : line) {
                        number = number.concat(integer);
                    }
                    numbers.add(Long.parseLong(number.strip()));
                }
            } else if (method.equals("cephalopod")) {
                for (int i = 0; i < maxLength; i++) {
                    String number = "";
                    for (int j = 0; j < problem.size(); j++) {
                        String digit = problem.get(j).get(i);
                        if (!digit.equals("0")) {
                            number = number.concat(problem.get(j).get(i));
                        }
                    }
                    numbers.add(Long.parseLong(number.strip()));
                }
            } else {
                throw new IllegalArgumentException("Unknown method: " + method);
            }

            return numbers;
        }

        @Override
        public String toString() {
            return "Problem{" +
                    "problem=" + problem +
                    ", operation='" + operation + '\'' +
                    '}';
        }
    }

    /**
     * Main method for Day 6.
     *
     * @param args
     */
    public static void main(String[] args) {

        // Get input value in ressources folder
        String input = FileTools.readFile("src\\adventOfCode2025Day6" +
                "\\resources\\input");
        List<String> inputLines = input.lines().toList();

        // Separate digit lines and operation line
        List<String> digitLinesStr = inputLines.subList(0, inputLines.size() - 1);
        String operationLineStr = inputLines.getLast();
        System.out.println("\n---- Lines (String) (lenMax digit : " + digitLinesStr.getFirst().length() + ") ( lenMax op : " + operationLineStr.length() + ") ----");
        digitLinesStr.forEach(System.out::println);
        System.out.println(operationLineStr);

        // Parse operation line and get operation indices
        List<String> operationLine = new ArrayList<>();
        List<Integer> operationIndices = new ArrayList<>();
        for (int i = 0; i < operationLineStr.length(); i++) {
            if (operationLineStr.charAt(i) == '+' || operationLineStr.charAt(i) == '*') {
                operationIndices.add(i);
                operationLine.add(Character.toString(operationLineStr.charAt(i)));
            }
        }
        System.out.println("\n---- Operation Line (List<String>) (length : " + operationLine.size() + ") ----\n"
                + operationLine);
        System.out.println("\n---- Operation Indice (List<Integer>) (length : " + operationIndices.size() + ") ----\n"
                + operationIndices);

        // Split input lines by operation indices
        List<Problem> problems = new ArrayList<>();
        int previousIndex = operationIndices.getFirst();
        for (int i = 0; i < operationIndices.size(); i++) {
            int nextIndex = previousIndex == operationIndices.getLast()
                    ? digitLinesStr.getFirst().length() + 1
                    : operationIndices.get(i+1);

            List<List<String>> digitsProblem = new ArrayList<>();

            for (String line : digitLinesStr) {
                digitsProblem.add(List.of(line.substring(previousIndex, nextIndex - 1).split("")));
            }

            problems.add(new Problem(digitsProblem, operationLine.get(i)));
            previousIndex = nextIndex;
        }
        System.out.println("\n---- Digit List Split (List<List<String>>) ----\n"
                + problems);

        // Vars
        Long resultPart1 = 0L;
        Long resultPart2 = 0L;

        // Algo Part 1
        for (Problem problem : problems) {
            resultPart1 += problem.resolve("normal");
        }

        // Algo Part 2
        for (Problem problem : problems) {
            resultPart2 += problem.resolve("cephalopod");
        }

        // Output
        PrintTools.printAnswer(6, 1,
                "Trash Compactor",
                Long.toString(resultPart1));

        PrintTools.printAnswer(6, 2,
                "Trash Compactor",
                Long.toString(resultPart2));


    }
}
