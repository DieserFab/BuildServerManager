package de.dieserfab.buildservermanager.gui.menu.submenus;

import de.dieserfab.buildservermanager.api.BSMAPI;
import de.dieserfab.buildservermanager.gui.AbstractGui;
import de.dieserfab.buildservermanager.utilities.FileUtilities;
import de.dieserfab.buildservermanager.utilities.ItemCreator;
import de.dieserfab.buildservermanager.utilities.Logger;
import de.dieserfab.buildservermanager.utilities.Messages;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.Arrays;

public class MapSettingsMenu extends AbstractGui {
    public MapSettingsMenu(GuiType guiType, String title, String name, Player player) {
        super(guiType, title, name, player);
    }

    @Override
    public void init() {
        setItem(SlotPosition.SMALL_CHEST_MIDDLE_LEFT.getSlot() + 1, new ItemCreator(Material.TNT, 1, Messages.GUIS_MAPSETTINGSMENU_DELETE, Messages.GUIS_MAPSETTINGSMENU_DELETE_LORE).create());
        setItem(SlotPosition.SMALL_CHEST_MIDDLE.getSlot() - 1, new ItemCreator(Material.DAYLIGHT_DETECTOR, GameRule.values().length, Messages.GUIS_MAPSETTINGSMENU_GAMERULE_MENU, null).create());
        setItem(SlotPosition.SMALL_CHEST_MIDDLE.getSlot(), new ItemCreator(Material.ENDER_PEARL, 1, Messages.GUIS_MAPSETTINGSMENU_TELEPORT, Messages.GUIS_MAPSETTINGSMENU_TELEPORT_LORE).create());
        setItem(SlotPosition.SMALL_CHEST_MIDDLE.getSlot() + 1, new ItemCreator(GuiHead.UP_ARROW.getId(), 1, Messages.GUIS_MAPSETTINGSMENU_LOAD, Messages.GUIS_MAPSETTINGSMENU_LOAD_LORE).create());
        setItem(SlotPosition.SMALL_CHEST_MIDDLE.getSlot() + 2, new ItemCreator(GuiHead.DOWN_ARROW.getId(), 1, Messages.GUIS_MAPSETTINGSMENU_UNLOAD, Messages.GUIS_MAPSETTINGSMENU_UNLOAD_LORE).create());
        setItem(SlotPosition.SMALL_CHEST_MIDDLE_RIGHT.getSlot() - 1, new ItemCreator(Material.NAME_TAG, 1, Messages.GUIS_MAPSETTINGSMENU_INFORMATION, Arrays.asList("§7Name: " + ChatColor.stripColor(getTitle()),
                "§7Disk Space: " + FileUtilities.readableFileSize(FileUtilities.folderSize(new File(ChatColor.stripColor(getTitle()).replaceAll("\\(.*\\)", "")))), " ")).create());
        setItem(SlotPosition.SMALL_CHEST_BOTTOM_LEFT.getSlot(), new ItemCreator(GuiHead.LEFT_ARROW.getId(), 1, Messages.GUIS_MAPSETTINGSMENU_BACK, Messages.GUIS_MAPSETTINGSMENU_BACK_LORE).create());
    }

    @Override
    public void onGuiOpen(Player player) {
        init();
    }

    @Override
    public void onGuiUse(Player player, ItemStack itemUsed, ClickType clickType) {
        String itemName = itemUsed.getItemMeta().getDisplayName();
        String[] values = getName().split("\\$");
        if (itemName.equalsIgnoreCase(Messages.GUIS_MAPSETTINGSMENU_BACK)) {
            new MapMenu(GuiType.BIG_CHEST, "§8§l" + values[1] + " (§a" + BSMAPI.getInstance().getMaps(values[0], values[1]).size() + "§8§l)", values[0] + "$" + values[1], player);
            return;
        }

        if (clickType == ClickType.CONTROL_DROP) {
            if (itemName.equalsIgnoreCase(Messages.GUIS_MAPSETTINGSMENU_DELETE)) {
                player.performCommand("maps removeMap " + values[0] + " " + values[1] + " " + values[2]);
                new MapMenu(GuiType.BIG_CHEST, "§8§l" + values[1] + " (§a" + BSMAPI.getInstance().getMaps(values[0], values[1]).size() + "§8§l)", values[0] + "$" + values[1], player);
                return;
            }
        }
        if (itemName.equalsIgnoreCase(Messages.GUIS_MAPSETTINGSMENU_GAMERULE_MENU)) {
            new GameRuleMenu(GuiType.BIG_CHEST, Messages.GUIS_MAPSETTINGSMENU_GAMERULE_MENU, values[2], player);
            return;
        }
        if (itemName.equalsIgnoreCase(Messages.GUIS_MAPSETTINGSMENU_LOAD)) {
            if (Bukkit.getWorld(ChatColor.stripColor(getTitle())) == null) {
                WorldCreator creator = new WorldCreator(ChatColor.stripColor(values[2]));
                try {
                    Bukkit.getServer().createWorld(creator);
                    player.sendMessage(Messages.GUIS_MAPSETTINGSMENU_LOAD_SUCCESS);
                    return;
                } catch (Exception e) {
                    Logger.l("eError while loading the World:" + e.getMessage());
                    player.sendMessage(Messages.GUIS_MAPSETTINGSMENU_LOAD_FAIL);
                    return;
                }
            }
        }
        if (itemName.equalsIgnoreCase(Messages.GUIS_MAPSETTINGSMENU_TELEPORT)) {
            player.performCommand("maps tp " + values[2]);
            return;
        }
        if (itemName.equalsIgnoreCase(Messages.GUIS_MAPSETTINGSMENU_UNLOAD)) {
            try {
                Bukkit.getOnlinePlayers().forEach(p -> {
                    if (p.getWorld().getName().equalsIgnoreCase(values[2])) {
                        p.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
                    }
                });
                Bukkit.getServer().unloadWorld(Bukkit.getWorld(ChatColor.stripColor(values[2])), true);
                player.sendMessage(Messages.GUIS_MAPSETTINGSMENU_UNLOAD_SUCCESS);
                return;
            } catch (Exception e) {
                Logger.l("eError while unloading the World:" + e.getMessage());
                player.sendMessage(Messages.GUIS_MAPSETTINGSMENU_UNLOAD_FAIL);
                return;
            }
        }

    }

    @Override
    public boolean onPlayerChat(Player player, String message) {
        return false;
    }

}
