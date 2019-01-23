package no.bekk.sommerskole.logic;

import java.util.Arrays;

public class Levensthein {

    public static int calculate(String x, String y) {
        if(x == null && y == null) return 0;
        if (x == null || x.isEmpty()) {
            return y.length();
        }

        if (y == null || y.isEmpty()) {
            return x.length();
        }

        int substitution = calculate(x.substring(1), y.substring(1))
                + costOfSubstitution(x.charAt(0), y.charAt(0));
        int insertion = calculate(x, y.substring(1)) + 1;
        int deletion = calculate(x.substring(1), y) + 1;

        return min(substitution, insertion, deletion);
    }

    public static int costOfSubstitution(char a, char b) {
        return a == b ? 0 : 1;
    }

    public static int min(int... numbers) {
        return Arrays.stream(numbers)
                .min().orElse(Integer.MAX_VALUE);
    }
}