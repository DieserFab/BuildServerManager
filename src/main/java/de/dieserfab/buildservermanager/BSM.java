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
        if (worldName.equalsIgnoreCase("void")) {
            return new VoidWorld();
        }
        return null;
    }

    public void startupSplash() {
        Logger.l("i ");
        Logger.l("i 88\"\"Yb 88   88 88 88     8888b.  .dP\"Y8 888888 88\"\"Yb Yb    dP 888888 88\"\"Yb 8b    d8    db    88b 88    db     dP\"\"b8 888888 88\"\"Yb");
        Logger.l("i 88__dP 88   88 88 88      8I  Yb `Ybo.\" 88__   88__dP  Yb  dP  88__   88__dP 88b  d88   dPYb   88Yb88   dPYb   dP   `\" 88__   88__dP");
        Logger.l("i 88\"\"Yb Y8   8P 88 88  .o  8I  dY o.`Y8b 88\"\"   88\"Yb    YbdP   88\"\"   88\"Yb  88YbdP88  dP__Yb  88 Y88  dP__Yb  Yb  \"88 88\"\"   88\"Yb ");
        Logger.l("i 88oodP `YbodP' 88 88ood8 8888Y\"  8bodP' 888888 88  Yb    YP    888888 88  Yb 88 YY 88 dP\"\"\"\"Yb 88  Y8 dP\"\"\"\"Yb  YboodP 888888 88  Yb");
        Logger.l("i ");
        Logger.l("iStarting the Plugin");
    }

}
