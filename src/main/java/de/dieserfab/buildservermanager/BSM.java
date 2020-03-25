package de.dieserfab.buildservermanager;

import de.dieserfab.buildservermanager.api.BSMAPI;
import de.dieserfab.buildservermanager.commands.CommandManager;
import de.dieserfab.buildservermanager.config.ConfigManager;
import de.dieserfab.buildservermanager.data.ProtocolVersion;
import de.dieserfab.buildservermanager.gui.GuiManager;
import de.dieserfab.buildservermanager.mapselector.DomainManager;
import de.dieserfab.buildservermanager.utilities.Logger;
import de.dieserfab.buildservermanager.utilities.chunkgenerator.VoidWorld;
import lombok.Getter;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

public class BSM extends JavaPlugin {

    private long startupTime;

    private BSMAPI api;

    @Getter
    private ConfigManager configManager;

    @Getter
    private DomainManager domainManager;

    @Getter
    private GuiManager guiManager;

    @Override
    public void onEnable() {
        Logger.l("iStarting the Plugin");
        startupTime = System.currentTimeMillis();
        ProtocolVersion.setProtocolVersion();
        api = new BSMAPI();
        loadModules();
        Logger.l("iSuccessfully loaded the Plugin in "+(System.currentTimeMillis()-startupTime)+" milliseconds");
    }

    public void loadModules(){
        this.configManager = new ConfigManager();
        this.domainManager = new DomainManager();
        this.guiManager = new GuiManager();
        new CommandManager();
    }


    public static BSM getInstance(){
        return getPlugin(BSM.class);
    }

    @Override
    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
        if(worldName.equalsIgnoreCase("void")){
            return new VoidWorld();
        }
        return null;
    }

}
