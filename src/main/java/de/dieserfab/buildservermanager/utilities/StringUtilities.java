package de.dieserfab.buildservermanager.utilities;

public class StringUtilities {

    /**
     * Used to determin wether a String represents a number
     *
     * @param input the String to check
     * @return
     */
    public static boolean isStringNumeric(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Used to determin if a String that represents a number is between value x and value y
     *
     * @param input the String to check
     * @param min   the min value the input has to be
     * @param max   the max value the input is allowed to be
     * @return
     */
    public static boolean isStringBetween(String input, int min, int max) {
        return Integer.valueOf(input) >= min && Integer.valueOf(input) <= max;
    }

}
