package de.dieserfab.buildservermanager.config;

import de.dieserfab.buildservermanager.BSM;
import de.dieserfab.buildservermanager.config.configs.MapsConfig;
import de.dieserfab.buildservermanager.utilities.Logger;
import lombok.Getter;

public class ConfigManager {

    @Getter
    private MapsConfig mapsConfig;

    public ConfigManager(){
        Logger.l("iLoading Config files");
        try {
            BSM.getInstance().saveDefaultConfig();
            this.mapsConfig = new MapsConfig();
            Logger.l("iSucessfully loaded the Config files");
        }catch (Exception e){
            Logger.l("eError while loading and caching the config files:"+e.getMessage());
        }
    }

}
