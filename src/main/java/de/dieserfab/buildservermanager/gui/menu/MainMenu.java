package de.dieserfab.buildservermanager.gui.menu;

import de.dieserfab.buildservermanager.BSM;
import de.dieserfab.buildservermanager.api.BSMAPI;
import de.dieserfab.buildservermanager.gui.AbstractGui;
import de.dieserfab.buildservermanager.gui.menu.submenus.ClassifyMenu;
import de.dieserfab.buildservermanager.gui.menu.submenus.PlayerUtilitiyMenu;
import de.dieserfab.buildservermanager.utilities.FileUtilities;
import de.dieserfab.buildservermanager.utilities.ItemCreator;
import de.dieserfab.buildservermanager.utilities.Messages;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class MainMenu extends AbstractGui {
    public MainMenu() {
        super(GuiType.SMALL_CHEST, Messages.GUIS_MAINMENU_TITLE, "mainmenu");
    }

    @Override
    public void init() {
        setItem(10, new ItemCreator(Material.BARRIER, 1, Messages.GUIS_MAINMENU_PLAYERS_ERROR, null).create());
        setItem(11, new ItemCreator(Material.REDSTONE, 1, Messages.GUIS_MAINMENU_PLAYER_UTILITY, Messages.GUIS_MAINMENU_PLAYER_UTILITY_LORE).create());
        setItem(13, new ItemCreator(Material.PAPER, 1, Messages.GUIS_MAINMENU_MAP_SELECTION, Messages.GUIS_MAINMENU_MAP_SELECTION_LORE).create());
        setItem(16, new ItemCreator(Material.NAME_TAG, 1, Messages.GUIS_MAINMENU_INFORMATION, Arrays.asList("§7Plugin Version: " + BSM.getInstance().getDescription().getVersion(), "§7CPU Cores: " + Runtime.getRuntime().availableProcessors(), "§7Available RAM: " + FileUtilities.bytesToMb(Runtime.getRuntime().maxMemory()) + "MB", " ")).create());

    }

    @Override
    public void onGuiOpen(Player player) {
        setItem(10, new ItemCreator(player.getName(), 1, Messages.GUIS_MAINMENU_PLAYERS, Messages.GUIS_MAINMENU_PLAYERS_LORE).create());
        if (!BSMAPI.getInstance().getMapsToClassify().isEmpty()) {
            setItem(SlotPosition.CHEST_UPPER_RIGHT.getSlot(), new ItemCreator("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWMzYWFmMTEzN2U1NzgzY2UyNzE0MDJkMDE1YWM4MzUyOWUyYzNmNTNkOTFmNjlhYjYyOWY2YTk1NjVmZWU3OCJ9fX0=", 1, Messages.GUIS_MAINMENU_MAPS_TO_CLASSIFY, Messages.GUIS_MAINMENU_MAPS_TO_CLASSIFY_LORE).create());
        }else{
            setItem(SlotPosition.CHEST_UPPER_RIGHT.getSlot(), null);
        }
    }

    @Override
    public void onGuiUse(Player player, ItemStack itemUsed, ClickType clickType) {
        String itemName = itemUsed.getItemMeta().getDisplayName();
        if (itemName.equalsIgnoreCase(Messages.GUIS_MAINMENU_MAP_SELECTION)) {
            BSM.getInstance().getGuiManager().getGui("domainmenu").openGui(player);
        }
        if (itemName.equalsIgnoreCase(Messages.GUIS_MAINMENU_PLAYERS)) {
            BSM.getInstance().getGuiManager().getGui("playersmenu").openGui(player);
        }
        if (itemName.equalsIgnoreCase(Messages.GUIS_MAINMENU_PLAYER_UTILITY)) {
            new PlayerUtilitiyMenu(GuiType.BIG_CHEST, Messages.GUIS_MAINMENU_PLAYER_UTILITY, "player_utility_menu").openGui(player);
        }
        if (itemName.equalsIgnoreCase(Messages.GUIS_MAINMENU_MAPS_TO_CLASSIFY)) {
            new ClassifyMenu(GuiType.BIG_CHEST, Messages.GUIS_CLASSIFYMENU_TITLE, "classifymenu").openGui(player);
        }
    }

    @Override
    public boolean onPlayerChat(Player player, String message) {
        return false;
    }
}
