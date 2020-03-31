package de.dieserfab.buildservermanager.gui.menu;

import de.dieserfab.buildservermanager.BSM;
import de.dieserfab.buildservermanager.api.BSMAPI;
import de.dieserfab.buildservermanager.gui.AbstractGui;
import de.dieserfab.buildservermanager.gui.menu.submenus.*;
import de.dieserfab.buildservermanager.utilities.FileUtilities;
import de.dieserfab.buildservermanager.utilities.ItemCreator;
import de.dieserfab.buildservermanager.utilities.Messages;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class MainMenu extends AbstractGui {
    public MainMenu(GuiType guiType, String title, String name, Player player) {
        super(guiType, title, name, player);
    }

    @Override
    public void init() {
        setItem(SlotPosition.SMALL_CHEST_MIDDLE_LEFT.getSlot() + 1, new ItemCreator(Material.BARRIER, 1, Messages.GUIS_MAINMENU_PLAYERS_ERROR, null).create());
        setItem(SlotPosition.SMALL_CHEST_MIDDLE_LEFT.getSlot() + 1, new ItemCreator(getPlayer().getName(), 1, Messages.GUIS_MAINMENU_PLAYERS, Messages.GUIS_MAINMENU_PLAYERS_LORE).create());
        setItem(SlotPosition.SMALL_CHEST_MIDDLE_LEFT.getSlot() + 2, new ItemCreator(Material.REDSTONE, 1, Messages.GUIS_MAINMENU_PLAYER_UTILITY, Messages.GUIS_MAINMENU_PLAYER_UTILITY_LORE).create());
        setItem(SlotPosition.SMALL_CHEST_MIDDLE.getSlot(), new ItemCreator(Material.PAPER, 1, Messages.GUIS_MAINMENU_MAP_SELECTION, Messages.GUIS_MAINMENU_MAP_SELECTION_LORE).create());
        setItem(SlotPosition.SMALL_CHEST_MIDDLE_RIGHT.getSlot() - 1, new ItemCreator(Material.NAME_TAG, 1, Messages.GUIS_MAINMENU_INFORMATION, Arrays.asList(
                "§7Plugin Version: " + BSM.getInstance().getDescription().getVersion(),
                "§7CPU Cores: " + Runtime.getRuntime().availableProcessors(),
                "§7Available RAM: " + FileUtilities.bytesToMb(Runtime.getRuntime().maxMemory()) + "MB",
                "§7Map Count: " + BSMAPI.getInstance().getAllDeclaredMaps().size(),
                "")).create());
        setItem(SlotPosition.CHEST_UPPER_RIGHT.getSlot(), BSMAPI.getInstance().getMapsToClassify().isEmpty() ? null : new ItemCreator(GuiHead.ALERT.getId(), 1, Messages.GUIS_MAINMENU_MAPS_TO_CLASSIFY, Messages.GUIS_MAINMENU_MAPS_TO_CLASSIFY_LORE).create());
    }

    @Override
    public void onGuiOpen(Player player) {
    }

    @Override
    public void onGuiUse(Player player, ItemStack itemUsed, ClickType clickType) {
        String itemName = itemUsed.getItemMeta().getDisplayName();
        if (itemName.equalsIgnoreCase(Messages.GUIS_MAINMENU_MAP_SELECTION)) {
            new DomainMenu(GuiType.SMALL_CHEST, "§8§lDomain Menu (§a" + BSMAPI.getInstance().getDomains().size() + "§8§l)", player.getName().toLowerCase() + "$domainmenu", player);
        }
        if (itemName.equalsIgnoreCase(Messages.GUIS_MAINMENU_PLAYERS)) {
            new PlayersMenu(GuiType.BIG_CHEST, Messages.GUIS_MAINMENU_PLAYERS, player.getName().toLowerCase() + "$playersmenu", player);
        }
        if (itemName.equalsIgnoreCase(Messages.GUIS_MAINMENU_PLAYER_UTILITY)) {
            new PlayerUtilitiyMenu(GuiType.BIG_CHEST, Messages.GUIS_MAINMENU_PLAYER_UTILITY, player.getName().toLowerCase() + "$playerutilitymenu", player);
        }
        if (itemName.equalsIgnoreCase(Messages.GUIS_MAINMENU_MAPS_TO_CLASSIFY)) {
            new ClassifyMenu(GuiType.BIG_CHEST, Messages.GUIS_CLASSIFYMENU_TITLE, player.getName().toLowerCase() + "$classifymenu", player);
        }
    }

    @Override
    public boolean onPlayerChat(Player player, String message) {
        return false;
    }
}
