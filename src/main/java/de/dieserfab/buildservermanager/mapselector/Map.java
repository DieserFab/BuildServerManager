package de.dieserfab.buildservermanager.mapselector;

import de.dieserfab.buildservermanager.BSM;
import de.dieserfab.buildservermanager.utilities.Logger;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;

public class Map {

    @Getter
    private String name, type;

    public Map(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public Map(String name, String type, boolean load) {
        this.name = name;
        this.type = type;
        if (Bukkit.getWorld(name) == null && BSM.getInstance().getConfig().getBoolean("load_maps_on_server_startup")) {
            WorldCreator creator = new WorldCreator(name);
            try {
                Bukkit.getServer().createWorld(creator);
            } catch (Exception e) {
                Logger.l("eError while loading the world:" + e.getMessage());
            }
        }
    }

}
