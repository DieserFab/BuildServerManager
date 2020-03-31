package de.dieserfab.buildservermanager.mapselector;

import de.dieserfab.buildservermanager.BSM;
import de.dieserfab.buildservermanager.utilities.Logger;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;

public class Map {

    @Getter
    private String name, type,domain,category;


    public Map(String name, String type, String domain, String category) {
        this.name = name;
        this.type = type;
        this.domain = domain;
        this.category = category;
    }

    public Map(String name, String type,String domain,String category, boolean load) {
        this.name = name;
        this.type = type;
        this.domain = domain;
        this.category = category;
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
