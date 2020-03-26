package de.dieserfab.buildservermanager.gui.menu;

import de.dieserfab.buildservermanager.BSM;
import de.dieserfab.buildservermanager.gui.AbstractGui;
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
        setItem(10, new ItemCreator(Material.REDSTONE, 1, Messages.GUIS_MAINMENU_PLAYERS_ERROR, null).create());
        setItem(13, new ItemCreator(Material.PAPER, 1, Messages.GUIS_MAINMENU_MAP_SELECTION, Messages.GUIS_MAINMENU_MAP_SELECTION_LORE).create());
        setItem(16, new ItemCreator(Material.NAME_TAG, 1, Messages.GUIS_MAINMENU_INFORMATION, Arrays.asList("§7Plugin Version: " + BSM.getInstance().getDescription().getVersion(), "§7CPU Cores: " + Runtime.getRuntime().availableProcessors(), "§7Available RAM: " + FileUtilities.bytesToMb(Runtime.getRuntime().maxMemory()) + "MB", " ")).create());

    }

    @Override
    public void onGuiOpen(Player player) {
        BSM.getInstance().getGuiManager().addCurrentGui(player, this);
        setItem(10, new ItemCreator(player.getName(), 1, Messages.GUIS_MAINMENU_PLAYERS, Messages.GUIS_MAINMENU_PLAYERS_LORE).create());
    }

    @Override
    public void onGuiUse(Player player, ItemStack itemUsed, ClickType clickType) {
        if (itemUsed.getItemMeta().getDisplayName().equalsIgnoreCase(Messages.GUIS_MAINMENU_MAP_SELECTION)) {
            BSM.getInstance().getGuiManager().getGui("domainmenu").openGui(player);
        }
        if (itemUsed.getItemMeta().getDisplayName().equalsIgnoreCase(Messages.GUIS_MAINMENU_PLAYERS)) {
            BSM.getInstance().getGuiManager().getGui("playersmenu").openGui(player);
        }

    }

    @Override
    public boolean onPlayerChat(Player player, String message) {
        return false;
    }
}
