package tools;

public class PrintTools {

    /**
     * Divider for console output
     */
    public static final String DIVIDER = "* . ﹢ ˖ ✦ ¸ . ﹢ ° ¸. ° ˖ ･ ·̩ ｡ ☆ ﾟ ＊ ¸* . ﹢ ˖ ✦ ¸ . ﹢ ° ¸. ° ˖ ･ ·̩ ｡ ☆";

    /**
     * Print the answer in a formatted way.
     *
     * @param day     The day of the problem.
     * @param part    The part of the problem.
     * @param problem The name of the problem.
     * @param answer  The answer to be printed.
     */
    public static void printAnswer(int day, int part, String problem,
                                   String answer) {
        System.out.println(DIVIDER);
        System.out.println("Day " + day + " - " + problem);
        System.out.println("Part " + part + " : " + answer);
        System.out.println(DIVIDER);
    }
}