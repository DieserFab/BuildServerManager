package de.dieserfab.buildservermanager.gui.menu.submenus;

import de.dieserfab.buildservermanager.gui.AbstractGui;
import de.dieserfab.buildservermanager.gui.menu.MainMenu;
import de.dieserfab.buildservermanager.utilities.ItemCreator;
import de.dieserfab.buildservermanager.utilities.Messages;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class PlayerUtilitiyMenu extends AbstractGui {
    public PlayerUtilitiyMenu(GuiType guiType, String title, String name, Player player) {
        super(guiType, title, name, player);
    }

    @Override
    public void init() {
        setItem(10, new ItemCreator(GuiHead.BLACK_0.getId(), 1, "§7Gamemode Survival", null).create());
        setItem(11, new ItemCreator(GuiHead.BLACK_1.getId(), 1, "§7Gamemode Creative", null).create());
        setItem(12, new ItemCreator(GuiHead.BLACK_2.getId(), 1, "§7Gamemode Adventure", null).create());
        setItem(13, new ItemCreator(GuiHead.BLACK_3.getId(), 1, "§7Gamemode Spectator", null).create());
        setItem(16, new ItemCreator(GuiHead.EYE_OF_ENDER.getId(), 1, "§dNightvision", null).create());
        setItem(19, new ItemCreator(GuiHead.PURPLE_P.getId(), 1, "§7Difficulty Peaceful", null).create());
        setItem(20, new ItemCreator(GuiHead.DIAMOND_E.getId(), 1, "§7Difficulty Easy", null).create());
        setItem(21, new ItemCreator(GuiHead.QUARZ_N.getId(), 1, "§7Difficulty Normal", null).create());
        setItem(22, new ItemCreator(GuiHead.BIRCH_H.getId(), 1, "§7Difficulty Hard", null).create());
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
        String command = ChatColor.stripColor(itemName);
        player.performCommand(command);
    }

    @Override
    public boolean onPlayerChat(Player player, String message) {
        return false;
    }
}
