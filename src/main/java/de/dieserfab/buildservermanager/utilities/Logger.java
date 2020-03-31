package de.dieserfab.buildservermanager.utilities;

import de.dieserfab.buildservermanager.BSM;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {

    private static final String CONSOLE_PREFIX = "[" + BSM.getInstance().getDescription().getName() + "] ";

    @Getter
    private static File directory, logFile;

    private static DateTimeFormatter date;
    private static LocalDateTime time;

    public Logger() {
        this.date = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
        this.time = LocalDateTime.now();
        this.directory = new File(BSM.getInstance().getDataFolder() + "/logs");
        this.logFile = new File(BSM.getInstance().getDataFolder() + "/logs/log_" + date.format(time) + ".txt");
        setup();
    }

    private void setup() {
        try {
            if (!this.directory.exists()) {
                this.directory.mkdirs();
            }
            if (!this.logFile.exists()) {
                this.logFile.createNewFile();
            }
        } catch (Exception e) {
            Logger.l("eError while creating the Logger Files:" + e.getMessage());
        }
    }

    /**
     * Based on the first letter the logging message is interpreted (e.G. e=error i=inf+o)
     *
     * @param message the message that will be logged to the console and to the logfile
     */
    public static void l(String message) {
        switch (message.charAt(0)) {
            case 'e':
                logError(message.substring(1));
                break;
            case 'i':
                logInformation(message.substring(1));
                break;
            default:
                Logger.l("eNo Logger Prefix specified.");
                return;
        }
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(getLogFile(), true));
            writer.append("[" + date.format(time) + "/" + message.charAt(0) + "] " + message.substring(1));
            writer.newLine();
            writer.close();
        } catch (Exception e) {
            Logger.l("eError while loggin to the file:" + e.getMessage());
        }
    }

    private static void logError(String message) {
        Bukkit.getServer().getConsoleSender().sendMessage("§c" + CONSOLE_PREFIX + message);
    }

    private static void logInformation(String message) {
        Bukkit.getServer().getConsoleSender().sendMessage(CONSOLE_PREFIX + message);
    }

}

