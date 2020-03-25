package de.dieserfab.buildservermanager.utilities;

import de.dieserfab.buildservermanager.BSM;

public class Logger {

    private static final String CONSOLE_PREFIX = "[" + BSM.getInstance().getDescription().getName() + "] ";

    /**
     * Based on the first letter the logging message is interpreted (e.G. e=error i=info)
     * @param message
     */
    public static void l(String message)  {
        switch (message.charAt(0)) {
            case 'e':
                logError(message.substring(1));
                return;
            case 'i':
                logInformation(message.substring(1));
                return;
        }
        logError("Incorrect Logger Prefix:"+message.charAt(0));
    }

    private static void logError(String message) {
        System.err.println(CONSOLE_PREFIX + message);
    }

    private static void logInformation(String message) {
        System.out.println(CONSOLE_PREFIX + message);
    }

}

