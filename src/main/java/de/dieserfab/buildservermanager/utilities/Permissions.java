package de.dieserfab.buildservermanager.utilities;


import de.dieserfab.buildservermanager.annotations.LoadPermission;

public class Permissions {

    public Permissions() {
        Logger.l("iLoading Permissions class");
    }

    /* Command Permissions */
    @LoadPermission(path = "commands.maps.open")
    public static String COMMAND_MAPS_OPEN;
    @LoadPermission(path = "commands.maps.tp")
    public static String COMMAND_MAPS_TP;
    @LoadPermission(path = "commands.maps.adddomain")
    public static String COMMAND_MAPS_ADDDOMAIN;
    @LoadPermission(path = "commands.maps.removedomain")
    public static String COMMAND_MAPS_REMOVEDOMAIN;
    @LoadPermission(path = "commands.maps.addcategory")
    public static String COMMAND_MAPS_ADDCATEGORY;
    @LoadPermission(path = "commands.maps.removecategory")
    public static String COMMAND_MAPS_REMOVECATEGORY;
    @LoadPermission(path = "commands.maps.addmap")
    public static String COMMAND_MAPS_ADDMAP;
    @LoadPermission(path = "commands.maps.removemap")
    public static String COMMAND_MAPS_REMOVEMAP;
    @LoadPermission(path = "commands.gamemode.other")
    public static String COMMAND_GAMEMODE_OTHER;
    @LoadPermission(path = "commands.gamemode.self")
    public static String COMMAND_GAMEMODE_SELF;
    @LoadPermission(path = "commands.difficulty.use")
    public static String COMMAND_DIFFICULTY_USE;
    @LoadPermission(path = "commands.nightvision.self")
    public static String COMMAND_NIGHTVISION_SELF;
    @LoadPermission(path = "commands.nightvision.other")
    public static String COMMAND_NIGHTVISION_OTHER;

}
