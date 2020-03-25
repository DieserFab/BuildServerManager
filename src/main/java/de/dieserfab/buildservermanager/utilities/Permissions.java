package de.dieserfab.buildservermanager.utilities;

import de.dieserfab.buildservermanager.BSM;
import org.bukkit.configuration.file.FileConfiguration;

public class Permissions {

    private static FileConfiguration config = BSM.getInstance().getConfigManager().getPermissionsConfig().getConfig();

    private static String path = "maps.";

    public static final String COMMAND_MAPS_OPEN = config.getString(path+".open");
    public static final String COMMAND_MAPS_TP = config.getString(path+".tp");
    public static final String COMMAND_MAPS_ADDDOMAIN = config.getString(path+".adddomain");
    public static final String COMMAND_MAPS_REMOVEDOMAIN = config.getString(path+".removedomain");
    public static final String COMMAND_MAPS_ADDCATEGORY = config.getString(path+".addcategory");
    public static final String COMMAND_MAPS_REMOVECATEGORY = config.getString(path+".removecategory");
    public static final String COMMAND_MAPS_ADDMAP = config.getString(path+".addmap");
    public static final String COMMAND_MAPS_REMOVEMAP = config.getString(path+".removemap");

}
