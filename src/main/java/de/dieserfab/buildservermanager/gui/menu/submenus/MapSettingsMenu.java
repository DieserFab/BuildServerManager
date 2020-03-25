package de.dieserfab.buildservermanager.gui.menu.submenus;

import de.dieserfab.buildservermanager.BSM;
import de.dieserfab.buildservermanager.api.BSMAPI;
import de.dieserfab.buildservermanager.gui.AbstractGui;
import de.dieserfab.buildservermanager.utilities.FileUtilities;
import de.dieserfab.buildservermanager.utilities.ItemCreator;
import de.dieserfab.buildservermanager.utilities.Logger;
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

    private static final String LOAD_SUCCESS = "§7Successfully loaded the Map.";
    private static final String LOAD_FAIL = "§cError while loading the Map.";
    private static final String LOAD = "§8§lLoad the Map";
    private static final List<String> LOAD_LORE = Arrays.asList("§7Click here to load the Map.", "§7This can cause a short lag spike.", " ");
    private static final String UNLOAD_SUCCESS = "§7Successfully unloaded the Map.";
    private static final String UNLOAD_FAIL = "§cError while unloading the Map.";
    private static final String UNLOAD = "§8§lUnload the Map";
    private static final List<String> UNLOAD_LORE = Arrays.asList("§7Click here to unload the Map.", "§7This can save some Server Resources.", " ");
    private static final String BACK = "§8§lBack to the Maps Menu";
    private static final List<String> BACK_LORE = Arrays.asList("§7Click here to get back to the Maps Menu.", " ");
    private static final String INFORMATION = "§8§lInformation";
    private static final String TELEPORT = "§8§lTeleport";
    private static final List<String> TELEPORT_LORE = Arrays.asList("§7Click to teleport to this World", " ");
    private static final String DELETE = "§c§lDelete World";
    private static final List<String> DELETE_LORE = Arrays.asList("§cTo delete the World you have to STRG+Q this icon.", "§cBeware this canot be undone!!", " ");

    @Override
    public void init() {
        setItem(SlotPosition.SMALL_CHEST_MIDDLE_LEFT.getSlot() + 1, new ItemCreator(Material.TNT, 1, DELETE, DELETE_LORE).create());
        setItem(SlotPosition.SMALL_CHEST_MIDDLE.getSlot(), new ItemCreator(Material.ENDER_PEARL, 1, TELEPORT, TELEPORT_LORE).create());
        setItem(SlotPosition.SMALL_CHEST_MIDDLE.getSlot() + 1, new ItemCreator("MHF_ArrowUp", 1, LOAD, LOAD_LORE).create());
        setItem(SlotPosition.SMALL_CHEST_MIDDLE.getSlot() + 2, new ItemCreator("MHF_ArrowDown", 1, UNLOAD, UNLOAD_LORE).create());
        setItem(SlotPosition.SMALL_CHEST_MIDDLE_RIGHT.getSlot() - 1, new ItemCreator(Material.NAME_TAG, 1, INFORMATION, Arrays.asList("§7Name: " + ChatColor.stripColor(getTitle()), "§7Disk Space: ~" + FileUtilities.bytesToMb(FileUtilities.sizeOfDirectory(new File(ChatColor.stripColor(getTitle())))) + "MB", " ")).create());
        setItem(SlotPosition.SMALL_CHEST_BOTTOM_LEFT.getSlot(), new ItemCreator("MHF_ArrowLeft", 1, BACK, BACK_LORE).create());
    }

    @Override
    public void onGuiOpen(Player player) {
        init();
        BSM.getInstance().getGuiManager().addCurrentGui(player, this);
    }

    @Override
    public void onGuiUse(Player player, ItemStack itemUsed, ClickType clickType) {
        if (itemUsed.getItemMeta().getDisplayName().equalsIgnoreCase(BACK)) {
            new MapMenu(GuiType.BIG_CHEST, "§8§l" + getCategory() + " (§a" + BSMAPI.getInstance().getMaps(getDomain(), getCategory()).size() + "§8§l)", getCategory().toLowerCase()).setValues(getDomain(), getCategory()).openGui(player);
            return;
        }

        if (clickType == ClickType.CONTROL_DROP) {
            if (itemUsed.getItemMeta().getDisplayName().equals(DELETE)) {
                player.performCommand("maps removeMap " + getDomain() + " " + getCategory() + " " + getName());
            }
        }
        switch (itemUsed.getItemMeta().getDisplayName()) {
            case TELEPORT:
                player.teleport(Bukkit.getWorld(getName()).getSpawnLocation());
                break;
            case LOAD:
                    if (Bukkit.getWorld(ChatColor.stripColor(getTitle())) == null) {
                    WorldCreator creator = new WorldCreator(ChatColor.stripColor(getName()));
                    try {
                        Bukkit.getServer().createWorld(creator);
                        player.sendMessage(LOAD_SUCCESS);
                    } catch (Exception e) {
                        Logger.l("eError while loading the World:" + e.getMessage());
                        player.sendMessage(LOAD_FAIL);
                    }
                }
                break;
            case UNLOAD:
                try {
                    Bukkit.getServer().unloadWorld(Bukkit.getWorld(ChatColor.stripColor(getName())), true);
                    player.sendMessage(UNLOAD_SUCCESS);
                } catch (Exception e) {
                    Logger.l("eError while unloading the World:" + e.getMessage());
                    player.sendMessage(UNLOAD_FAIL);
                }
                break;
        }
    }

    @Override
    public boolean onPlayerChat(Player player, String message) {
        return false;
    }

}
