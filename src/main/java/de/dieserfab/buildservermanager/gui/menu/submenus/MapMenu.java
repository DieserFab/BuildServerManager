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

public class MapMenu extends AbstractGui {
    public MapMenu(GuiType guiType, String title, String name, Player player) {
        super(guiType, title, name, player);
    }

    @Override
    public void init() {
        BSMAPI api = BSMAPI.getInstance();
        String[] strings = getName().split("\\$");
        if (!api.getMaps(strings[0], strings[1]).isEmpty()) {
            int count = 0;
            for (Map map : api.getMaps(strings[0], strings[1])) {
                setItem(count, new ItemCreator(Material.BOOK, count + 1, "§8§l" + map.getName() + String.valueOf(Bukkit.getWorld(map.getName()) == null).replaceAll("true", "§8(§cnot loaded§8)").replaceAll("false", "§8(§aloaded§8)"), Bukkit.getWorld(map.getName()) == null ? Messages.GUIS_MAPMENU_MAP_NOT_LOADED_LORE : Messages.GUIS_MAPMENU_MAP_LORE).create());
                count++;
            }
            setItem(SlotPosition.BIG_CHEST_BOTTOM_RIGHT.getSlot(), new ItemCreator(Material.EMERALD_BLOCK, 1, Messages.GUIS_MAPMENU_CREATE_MAP, Messages.GUIS_MAPMENU_CREATE_MAP_LORE).create());
        } else {
            setItem(SlotPosition.SMALL_CHEST_MIDDLE.getSlot(), new ItemCreator(Material.BARRIER, 1, Messages.GUIS_MAPMENU_NO_MAP, Messages.GUIS_MAPMENU_NO_MAP_LORE).create());
        }
        setItem(SlotPosition.BIG_CHEST_BOTTOM_LEFT.getSlot(), new ItemCreator(GuiHead.LEFT_ARROW.getId(), 1, Messages.GUIS_MAPMENU_BACK, Messages.GUIS_MAPMENU_BACK_LORE).create());

    }

    @Override
    public void onGuiOpen(Player player) {
        init();
    }

    @Override
    public void onGuiUse(Player player, ItemStack itemUsed, ClickType clickType) {
        String itemName = itemUsed.getItemMeta().getDisplayName();
        String[] strings = getName().split("\\$");
        if (itemName.equalsIgnoreCase(Messages.GUIS_MAPMENU_BACK)) {
            new CategoryMenu(GuiType.SMALL_CHEST, "§8§l" + strings[0] + " (§a" + BSMAPI.getInstance().getCategories(strings[0]).size() + "§8§l)", strings[0], player);
            return;
        }
        if (itemName.equalsIgnoreCase(Messages.GUIS_MAPMENU_NO_MAP) || itemName.equalsIgnoreCase(Messages.GUIS_MAPMENU_CREATE_MAP)) {
            setListenForChat(true);
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
            new MapSettingsMenu(GuiType.SMALL_CHEST, "§8§l" + map + String.valueOf(Bukkit.getWorld(map) == null).replaceAll("true", "§8(§cnot loaded§8)").replaceAll("false", "§8(§aloaded§8)"),
                    getName() + "$" + map, player);
        }
    }

    @Override
    public boolean onPlayerChat(Player player, String message) {
        if (isListenForChat()) {
            String[] strings = message.split(" ");
            String[] values = getName().split("\\$");
            if (strings.length != 2) {
                player.sendMessage(Messages.GUIS_MAPMENU_WRONG_USAGE);
                return true;
            }
            player.performCommand("maps addMap " + values[0] + " " + values[1] + " " + strings[0] + " " + strings[1]);
            setListenForChat(false);
            return true;
        }
        return false;
    }
}
