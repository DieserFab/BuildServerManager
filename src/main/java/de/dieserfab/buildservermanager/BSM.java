package de.dieserfab.buildservermanager;

import de.dieserfab.buildservermanager.api.BSMAPI;
import de.dieserfab.buildservermanager.commands.CommandManager;
import de.dieserfab.buildservermanager.config.ConfigManager;
import de.dieserfab.buildservermanager.data.ProtocolVersion;
import de.dieserfab.buildservermanager.gui.GuiManager;
import de.dieserfab.buildservermanager.listener.ConnectionListener;
import de.dieserfab.buildservermanager.listener.InteractListener;
import de.dieserfab.buildservermanager.mapselector.DomainManager;
import de.dieserfab.buildservermanager.utilities.Logger;
import de.dieserfab.buildservermanager.utilities.chunkgenerator.VoidWorld;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.PluginManager;
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
        startupTime = System.currentTimeMillis();
        startupSplash();
        ProtocolVersion.setProtocolVersion();
        api = new BSMAPI();
        loadModules();
        loadListener(Bukkit.getPluginManager());
        api.getMapsToClassify();
        Logger.l("iSuccessfully loaded the Plugin in " + (System.currentTimeMillis() - startupTime) + " milliseconds");
    }

    public void loadModules() {
        this.configManager = new ConfigManager();
        this.domainManager = new DomainManager();
        this.guiManager = new GuiManager();
        new CommandManager();
    }

    public void loadListener(PluginManager pluginManager) {
        pluginManager.registerEvents(new ConnectionListener(), this);
        pluginManager.registerEvents(new InteractListener(), this);
    }

    public static BSM getInstance() {
        return getPlugin(BSM.class);
    }

    @Override
    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
        return new VoidWorld();
    }

    public void startupSplash() {
        Logger.l("i ");
        Logger.l("i 88\"\"Yb .dP\"Y8 8b    d8");
        Logger.l("i 88__dP `Ybo.\" 88b  d88   " + ChatColor.GREEN + BSM.getInstance().getDescription().getName() + " " + ChatColor.GRAY + BSM.getInstance().getDescription().getVersion());
        Logger.l("i 88\"\"Yb o.`Y8b 88YbdP88   " + ChatColor.GRAY + "Running on: " + Bukkit.getVersion());
        Logger.l("i 88oodP 8bodP' 88 YY 88");
        Logger.l("i ");
        Logger.l("iStarting the Plugin");
    }

}
