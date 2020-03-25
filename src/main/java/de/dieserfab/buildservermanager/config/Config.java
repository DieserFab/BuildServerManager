package de.dieserfab.buildservermanager.config;

import de.dieserfab.buildservermanager.BSM;
import de.dieserfab.buildservermanager.utilities.Logger;
import lombok.Getter;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Config {

    @Getter
    private File file;
    @Getter
    private FileConfiguration config;
    @Getter
    private String name;

    public Config(String name) {
        this.name = name;
        file = new File(BSM.getInstance().getDataFolder(), name + ".yml");
        if (!this.file.exists()) {
            this.file.getParentFile().mkdirs();
            BSM.getInstance().saveResource(name + ".yml", false);
        }
        this.config = new YamlConfiguration();
        try {
            this.config.load(this.file);
        } catch (IOException | InvalidConfigurationException e) {
            Logger.l("eError while loading the Config File:"+e.getMessage());
        }
    }

    public void save() {
        try {
            getConfig().save(getFile());
        }catch (IOException e) {
            Logger.l("eError while saving the config "+getName()+":"+e.getMessage());
        }
    }



}
