package de.dieserfab.buildservermanager.gui.menu.submenus;

import de.dieserfab.buildservermanager.gui.AbstractGui;
import de.dieserfab.buildservermanager.gui.menu.MainMenu;
import de.dieserfab.buildservermanager.utilities.ItemCreator;
import de.dieserfab.buildservermanager.utilities.Messages;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class PlayerUtilitiyMenu extends AbstractGui {
    public PlayerUtilitiyMenu(GuiType guiType, String title, String name, Player player) {
        super(guiType, title, name, player);
    }

    @Override
    public void init() {
        setItem(10, new ItemCreator(GuiHead.BLACK_0.getId(), 1, Messages.GUIS_PLAYERUTILITYMENU_GAMEMODE_0, null).create());
        setItem(11, new ItemCreator(GuiHead.BLACK_1.getId(), 1, Messages.GUIS_PLAYERUTILITYMENU_GAMEMODE_1, null).create());
        setItem(12, new ItemCreator(GuiHead.BLACK_2.getId(), 1, Messages.GUIS_PLAYERUTILITYMENU_GAMEMODE_2, null).create());
        setItem(13, new ItemCreator(GuiHead.BLACK_3.getId(), 1, Messages.GUIS_PLAYERUTILITYMENU_GAMEMODE_3, null).create());
        setItem(SlotPosition.BIG_CHEST_BOTTOM_LEFT.getSlot(), new ItemCreator(GuiHead.LEFT_ARROW.getId(), 1, Messages.GUIS_PLAYERUTILITYMENU_BACK, Messages.GUIS_PLAYERUTILITYMENU_BACK_LORE).create());
    }

    @Override
    public void onGuiOpen(Player player) {

    }

    @Override
    public void onGuiUse(Player player, ItemStack itemUsed, ClickType clickType) {
        String itemName = itemUsed.getItemMeta().getDisplayName();
        if (itemName.equalsIgnoreCase(Messages.GUIS_PLAYERUTILITYMENU_BACK)) {
            new MainMenu(AbstractGui.GuiType.SMALL_CHEST, Messages.GUIS_MAINMENU_TITLE, player.getName().toLowerCase() + "_mainmenu", player);
            return;
        }
        if (itemName.equalsIgnoreCase(Messages.GUIS_PLAYERUTILITYMENU_GAMEMODE_0)) {
            player.performCommand("gamemode 0");
        }
        if (itemName.equalsIgnoreCase(Messages.GUIS_PLAYERUTILITYMENU_GAMEMODE_1)) {
            player.performCommand("gamemode 1");
        }
        if (itemName.equalsIgnoreCase(Messages.GUIS_PLAYERUTILITYMENU_GAMEMODE_2)) {
            player.performCommand("gamemode 2");
        }
        if (itemName.equalsIgnoreCase(Messages.GUIS_PLAYERUTILITYMENU_GAMEMODE_3)) {
            player.performCommand("gamemode 3");
        }
    }

    @Override
    public boolean onPlayerChat(Player player, String message) {
        return false;
    }
}
