package de.dieserfab.buildservermanager.utilities;


import de.dieserfab.buildservermanager.annotations.LoadPermission;

public class Permissions {

    public Permissions() {
        Logger.l("iLoading Permissions class");
    }

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
    @LoadPermission(path = "commands.gamemode")
    public static String COMMAND_GAMEMODE;

}
