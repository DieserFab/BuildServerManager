package de.dieserfab.buildservermanager.config;

import de.dieserfab.buildservermanager.BSM;
import de.dieserfab.buildservermanager.config.configs.MapsConfig;
import de.dieserfab.buildservermanager.config.configs.MessagesConfig;
import de.dieserfab.buildservermanager.config.configs.PermissionsConfig;
import de.dieserfab.buildservermanager.utilities.Logger;
import de.dieserfab.buildservermanager.utilities.Messages;
import lombok.Getter;

public class ConfigManager {

    @Getter
    private MapsConfig mapsConfig;

    @Getter
    private PermissionsConfig permissionsConfig;

    @Getter
    private MessagesConfig messagesConfig;

    public ConfigManager(){
        Logger.l("iLoading Config files");
        try {
            BSM.getInstance().saveDefaultConfig();
            this.mapsConfig = new MapsConfig();
            this.permissionsConfig = new PermissionsConfig();
            this.messagesConfig = new MessagesConfig().populateMessages(new Messages());
            Logger.l("iSucessfully loaded the Config files");
        }catch (Exception e){
            Logger.l("eError while loading and caching the config files:"+e.getMessage());
        }
    }

}
