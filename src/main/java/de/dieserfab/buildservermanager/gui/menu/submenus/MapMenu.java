package de.dieserfab.buildservermanager.gui.menu.submenus;

import de.dieserfab.buildservermanager.BSM;
import de.dieserfab.buildservermanager.api.BSMAPI;
import de.dieserfab.buildservermanager.gui.AbstractGui;
import de.dieserfab.buildservermanager.mapselector.Map;
import de.dieserfab.buildservermanager.utilities.ItemCreator;
import de.dieserfab.buildservermanager.utilities.Logger;
import de.dieserfab.buildservermanager.utilities.Messages;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class MapMenu extends AbstractGui {
    public MapMenu(GuiType guiType, String title, String name) {
        super(guiType, title, name);
    }

    @Getter
    private String domain, category;

    public MapMenu setValues(String domain, String category) {
        this.domain = domain;
        this.category = category;
        postInit();
        return this;
    }


    @Override
    public void init() {

    }

    public void postInit() {
        BSMAPI api = BSMAPI.getInstance();
        getInventory().clear();
        if (!api.getMaps(getDomain(), getName()).isEmpty()) {
            int count = 0;
            for (Map map : api.getMaps(getDomain(), getName())) {
                setItem(count, new ItemCreator(Material.PAPER, count + 1, "§8§l" + map.getName() + loadPrefix(!(Bukkit.getWorld(map.getName()) == null)), Bukkit.getWorld(map.getName()) == null ? Messages.GUIS_MAPMENU_MAP_NOT_LOADED_LORE : Messages.GUIS_MAPMENU_MAP_LORE).create());
                count++;
            }
            setItem(SlotPosition.BIG_CHEST_BOTTOM_RIGHT.getSlot(), new ItemCreator(Material.EMERALD_BLOCK, 1, Messages.GUIS_MAPMENU_CREATE_MAP, Messages.GUIS_MAPMENU_CREATE_MAP_LORE).create());
        } else {
            setItem(31, new ItemCreator(Material.BARRIER, 1, Messages.GUIS_MAPMENU_NO_MAP, Messages.GUIS_MAPMENU_NO_MAP_LORE).create());
        }
        setItem(SlotPosition.BIG_CHEST_BOTTOM_LEFT.getSlot(), new ItemCreator("MHF_ArrowLeft", 1, Messages.GUIS_MAPMENU_BACK, Messages.GUIS_MAPMENU_BACK_LORE).create());
    }

    @Override
    public void onGuiOpen(Player player) {
        init();
    }

    @Override
    public void onGuiUse(Player player, ItemStack itemUsed, ClickType clickType) {
        String itemName = itemUsed.getItemMeta().getDisplayName();
        if (itemName.equalsIgnoreCase(Messages.GUIS_MAPMENU_BACK)) {
            new CategoryMenu(GuiType.SMALL_CHEST, "§8§l" + getDomain() + " (§a" + BSMAPI.getInstance().getCategories(getDomain()).size() + "§8§l)", getDomain().toLowerCase()).setDomain(getDomain()).openGui(player);
            return;
        }
        if (itemName.equalsIgnoreCase(Messages.GUIS_MAPMENU_NO_MAP) || itemName.equalsIgnoreCase(Messages.GUIS_MAPMENU_CREATE_MAP)) {
            create.add(player);
            player.closeInventory();
            player.sendMessage(Messages.GUIS_MAPMENU_CREATE_MSG);
            return;
        }
        if (clickType == ClickType.LEFT) {
            String map = ChatColor.stripColor(itemName).replaceAll("\\(.*\\)", "");
            if (Bukkit.getWorld(map) == null) {
                WorldCreator creator = new WorldCreator(map);
                try {
                    Bukkit.getServer().createWorld(creator);
                    player.performCommand("maps tp " + map);
                } catch (Exception e) {
                    Logger.l("eError while teleporting:" + e.getMessage());
                }
            } else {
                player.teleport(Bukkit.getWorld(map).getSpawnLocation());
            }
        }
        if (clickType == ClickType.RIGHT) {
            String map = ChatColor.stripColor(itemName).replaceAll("\\(.*\\)", "");
            new MapSettingsMenu(GuiType.SMALL_CHEST, "§8§l" + map + loadPrefix(!(Bukkit.getWorld(map) == null)), map.toLowerCase()).setValue(getDomain(), getCategory()).openGui(player);
        }
    }

    private List<Player> create = new ArrayList<>();

    @Override
    public boolean onPlayerChat(Player player, String message) {
        if (create.contains(player)) {
            String[] strings = message.split(" ");
            if (strings.length != 2) {
                player.sendMessage(Messages.GUIS_MAPMENU_WRONG_USAGE);
                return true;
            }
            player.performCommand("maps addMap " + getDomain() + " " + getCategory() + " " + strings[0] + " " + strings[1]);
            create.remove(player);
            return true;
        }
        return false;
    }

    private String loadPrefix(boolean loaded) {
        return loaded ? "§8(§aloaded§8)" : "§8(§cnot loaded§8)";
    }

}
