package de.dieserfab.buildservermanager.config;

import de.dieserfab.buildservermanager.BSM;
import de.dieserfab.buildservermanager.config.configs.MapsConfig;
import de.dieserfab.buildservermanager.utilities.Logger;
import lombok.Getter;

public class ConfigManager {

    @Getter
    private MapsConfig mapsConfig;

    public ConfigManager(){
        try {
            BSM.getInstance().saveDefaultConfig();
            this.mapsConfig = new MapsConfig();
        }catch (Exception e){
            Logger.l("eError while loading and caching the config files:"+e.getMessage());
        }
    }

}
