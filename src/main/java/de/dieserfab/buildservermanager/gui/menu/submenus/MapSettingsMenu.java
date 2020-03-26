package de.dieserfab.buildservermanager.gui.menu.submenus;

import de.dieserfab.buildservermanager.BSM;
import de.dieserfab.buildservermanager.api.BSMAPI;
import de.dieserfab.buildservermanager.gui.AbstractGui;
import de.dieserfab.buildservermanager.utilities.FileUtilities;
import de.dieserfab.buildservermanager.utilities.ItemCreator;
import de.dieserfab.buildservermanager.utilities.Logger;
import de.dieserfab.buildservermanager.utilities.Messages;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class MapSettingsMenu extends AbstractGui {
    public MapSettingsMenu(GuiType guiType, String title, String name) {
        super(guiType, title, name);
    }

    @Getter
    private String domain, category;

    public AbstractGui setValue(String domain, String category) {
        this.domain = domain;
        this.category = category;
        return this;
    }

    @Override
    public void init() {
        setItem(SlotPosition.SMALL_CHEST_MIDDLE_LEFT.getSlot() + 1, new ItemCreator(Material.TNT, 1, Messages.GUIS_MAPSETTINGSMENU_DELETE, Messages.GUIS_MAPSETTINGSMENU_DELETE_LORE).create());
        setItem(SlotPosition.SMALL_CHEST_MIDDLE.getSlot(), new ItemCreator(Material.ENDER_PEARL, 1, Messages.GUIS_MAPSETTINGSMENU_TELEPORT, Messages.GUIS_MAPSETTINGSMENU_TELEPORT_LORE).create());
        setItem(SlotPosition.SMALL_CHEST_MIDDLE.getSlot() + 1, new ItemCreator("MHF_ArrowUp", 1, Messages.GUIS_MAPSETTINGSMENU_LOAD, Messages.GUIS_MAPSETTINGSMENU_LOAD_LORE).create());
        setItem(SlotPosition.SMALL_CHEST_MIDDLE.getSlot() + 2, new ItemCreator("MHF_ArrowDown", 1, Messages.GUIS_MAPSETTINGSMENU_UNLOAD, Messages.GUIS_MAPSETTINGSMENU_UNLOAD_LORE).create());
        setItem(SlotPosition.SMALL_CHEST_MIDDLE_RIGHT.getSlot() - 1, new ItemCreator(Material.NAME_TAG, 1, Messages.GUIS_MAPSETTINGSMENU_INFORMATION, Arrays.asList("§7Name: " + ChatColor.stripColor(getTitle()), "§7Disk Space: ~" + FileUtilities.humanReadableByteCountBin(FileUtilities.sizeOfDirectory(new File(ChatColor.stripColor(getTitle())))) + "MB", " ")).create());
        setItem(SlotPosition.SMALL_CHEST_BOTTOM_LEFT.getSlot(), new ItemCreator("MHF_ArrowLeft", 1, Messages.GUIS_MAPSETTINGSMENU_BACK, Messages.GUIS_MAPSETTINGSMENU_BACK_LORE).create());
    }

    @Override
    public void onGuiOpen(Player player) {
        init();
        BSM.getInstance().getGuiManager().addCurrentGui(player, this);
    }

    @Override
    public void onGuiUse(Player player, ItemStack itemUsed, ClickType clickType) {
        if (itemUsed.getItemMeta().getDisplayName().equalsIgnoreCase(Messages.GUIS_MAPSETTINGSMENU_BACK)) {
            new MapMenu(GuiType.BIG_CHEST, "§8§l" + getCategory() + " (§a" + BSMAPI.getInstance().getMaps(getDomain(), getCategory()).size() + "§8§l)", getCategory().toLowerCase()).setValues(getDomain(), getCategory()).openGui(player);
            return;
        }

        if (clickType == ClickType.CONTROL_DROP) {
            if (itemUsed.getItemMeta().getDisplayName().equals(Messages.GUIS_MAPSETTINGSMENU_DELETE)) {
                player.performCommand("maps removeMap " + getDomain() + " " + getCategory() + " " + getName());
            }
        }
        if (itemUsed.getItemMeta().getDisplayName().equalsIgnoreCase(Messages.GUIS_MAPSETTINGSMENU_LOAD)) {
            if (Bukkit.getWorld(ChatColor.stripColor(getTitle())) == null) {
                WorldCreator creator = new WorldCreator(ChatColor.stripColor(getName()));
                try {
                    Bukkit.getServer().createWorld(creator);
                    player.sendMessage(Messages.GUIS_MAPSETTINGSMENU_LOAD_SUCCESS);
                } catch (Exception e) {
                    Logger.l("eError while loading the World:" + e.getMessage());
                    player.sendMessage(Messages.GUIS_MAPSETTINGSMENU_LOAD_FAIL);
                }
            }
        }
        if (itemUsed.getItemMeta().getDisplayName().equalsIgnoreCase(Messages.GUIS_MAPSETTINGSMENU_TELEPORT)) {
            player.performCommand("maps tp " + getName());
        }
        if (itemUsed.getItemMeta().getDisplayName().equalsIgnoreCase(Messages.GUIS_MAPSETTINGSMENU_UNLOAD)) {
            try {
                Bukkit.getServer().unloadWorld(Bukkit.getWorld(ChatColor.stripColor(getName())), true);
                player.sendMessage(Messages.GUIS_MAPSETTINGSMENU_UNLOAD_SUCCESS);
            } catch (Exception e) {
                Logger.l("eError while unloading the World:" + e.getMessage());
                player.sendMessage(Messages.GUIS_MAPSETTINGSMENU_UNLOAD_FAIL);
            }
        }

    }

    @Override
    public boolean onPlayerChat(Player player, String message) {
        return false;
    }

}
