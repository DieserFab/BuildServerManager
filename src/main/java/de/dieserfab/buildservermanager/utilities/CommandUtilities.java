package de.dieserfab.buildservermanager.utilities;

public class CommandUtilities {

    public static boolean isStringNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isStringBetween(String string, int x, int y) {
        return Integer.valueOf(string) >= x && Integer.valueOf(string) <= y;
    }

}
