package adventOfCode2025Day5.java;

import tools.FileTools;
import tools.PrintTools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Main {

    public static class Range {

        long min;
        long max;

        public Range(long min, long max) {
            this.min = min;
            this.max = max;
        }

        public boolean contains(long id) {
            return id >= min && id <= max;
        }

        public long getMin() {
            return min;
        }

        public long getMax() {
            return max;
        }

        public void setMax(long max) {
            this.max = max;
        }
    }

    public final class RangeComparators {
        static final Comparator<Range> BY_MIN =
                Comparator.comparingLong(Range::getMin)
                        .thenComparingLong(Range::getMax);
    }

    /**
     * Main method for Day 5.
     *
     * @param args
     */
    public static void main(String[] args) {

        // Get input value in ressources folder
        String input = FileTools.readFile("src\\adventOfCode2025Day5" +
                "\\ressources\\input");

        String[] ranges_ids = input.split("\n\n");
        List<Range> ranges = new ArrayList<Range>(ranges_ids[0].lines()
                .map(s -> {
                    String[] rangeTab = s.split("-");
                    return new Range(Long.parseLong(rangeTab[0]),
                            Long.parseLong(rangeTab[1]));
                }).toList());

        List<Long> ids = ranges_ids[1].lines()
                .map(Long::parseLong)
                .toList();

        // Vars
        List<Range> optimizedRanges = new ArrayList<>();
        int countFreshIngredients = 0;
        ranges.sort(RangeComparators.BY_MIN);
        long countPossibleFreshIngredients = 0;

        // Optimisation of the ranges
        for (Range range : ranges) {
            if (optimizedRanges.isEmpty()) {
                optimizedRanges.add(range);
            } else {
                Range lastRange = optimizedRanges.get(optimizedRanges.size() - 1);
                if (range.getMin() <= lastRange.getMax() + 1) {
                    // Merge ranges
                    lastRange.setMax(Math.max(lastRange.getMax(), range.getMax()));
                } else {
                    optimizedRanges.add(range);
                }
            }
        }

        // Algo Part 1
        // Test ids
        for (Long id : ids) {
            boolean freshIngredient = false;
            for (Range range : optimizedRanges) {
                if (range.contains(id)) {
                    freshIngredient = true;
                    break;
                }
            }
            if (freshIngredient) {
                countFreshIngredients++;
            }
        }

        // Algo Part 2
        for (Range range : optimizedRanges) {
            countPossibleFreshIngredients += (range.getMax() - range.getMin() + 1);
        }

        // Output
        PrintTools.printAnswer(5, 1,
                "Cafeteria ",
                Integer.toString(countFreshIngredients));

        PrintTools.printAnswer(5, 2,
                "Cafeteria ",
                Long.toString(countPossibleFreshIngredients));
    }
}
