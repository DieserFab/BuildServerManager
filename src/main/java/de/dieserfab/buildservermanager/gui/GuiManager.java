package de.dieserfab.buildservermanager.gui;

import de.dieserfab.buildservermanager.BSM;
import de.dieserfab.buildservermanager.gui.menu.MainMenu;
import de.dieserfab.buildservermanager.gui.menu.submenus.DomainMenu;
import de.dieserfab.buildservermanager.gui.menu.submenus.PlayersMenu;
import de.dieserfab.buildservermanager.listener.GuiListener;
import de.dieserfab.buildservermanager.utilities.Logger;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GuiManager {

    @Getter
    private List<AbstractGui> guis;

    @Getter
    private HashMap<Player, AbstractGui> currentGuis;

    public GuiManager() {
        Logger.l("iLoading all GUIs");
        this.guis = new ArrayList<>();
        this.currentGuis = new HashMap<>();
        try {
            this.guis.add(new MainMenu());
            this.guis.add(new DomainMenu());
            this.guis.add(new PlayersMenu());
            Logger.l("iSuccessfully loaded all GUIS");
        } catch (Exception e) {
            Logger.l("eError while loading the Guis:" + e.getMessage());
        }
        Bukkit.getPluginManager().registerEvents(new GuiListener(), BSM.getInstance());
    }

    public void addCurrentGui(Player player, AbstractGui gui) {
        currentGuis.put(player, gui);
    }

    public AbstractGui getGui(String name) {
        return this.guis.stream().filter(gui -> gui.getName().equalsIgnoreCase(name)).findAny().orElse(null);
    }

}
