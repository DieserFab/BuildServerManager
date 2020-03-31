package de.dieserfab.buildservermanager.gui.menu.submenus;

import de.dieserfab.buildservermanager.BSM;
import de.dieserfab.buildservermanager.gui.AbstractGui;
import de.dieserfab.buildservermanager.gui.menu.MainMenu;
import de.dieserfab.buildservermanager.utilities.ItemCreator;
import de.dieserfab.buildservermanager.utilities.Logger;
import de.dieserfab.buildservermanager.utilities.Messages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class PlayersMenu extends AbstractGui {
    public PlayersMenu(GuiType guiType, String title, String name, Player player) {
        super(guiType, title, name, player);
    }

    @Override
    public void init() {
        int count = 0;
        for (Player player : Bukkit.getOnlinePlayers()) {
            setItem(count, new ItemCreator(player.getName(), 1, "§7" + player.getName(), Messages.GUIS_PLAYERSMENU_PLAYER_LORE).create());
            count++;
        }
        setItem(SlotPosition.BIG_CHEST_BOTTOM_LEFT.getSlot(), new ItemCreator("MHF_ArrowLeft", 1, Messages.GUIS_PLAYERSMENU_BACK, Messages.GUIS_PLAYERSMENU_BACK_LORE).create());
    }

    @Override
    public void onGuiOpen(Player player) {
        init();
    }

    @Override
    public void onGuiUse(Player player, ItemStack itemUsed, ClickType clickType) {
        String itemName = itemUsed.getItemMeta().getDisplayName();
        if (itemName.equalsIgnoreCase(Messages.GUIS_PLAYERSMENU_BACK)) {
            new MainMenu(AbstractGui.GuiType.SMALL_CHEST, Messages.GUIS_MAINMENU_TITLE, player.getName().toLowerCase() + "_mainmenu", player);
            return;
        }
        try {
            Player p = Bukkit.getPlayer(ChatColor.stripColor(itemName));
            player.sendMessage(Messages.GUIS_PLAYERSMENU_TELEPORT_SUCCESS.replace("%player%", p.getName()));
            player.teleport(p.getLocation());
        } catch (Exception e) {
            player.sendMessage(Messages.GUIS_PLAYERSMENU_TELEPORT_FAIL);
            Logger.l("eError occured while teleporting player:" + e.getMessage());
        }
    }

    @Override
    public boolean onPlayerChat(Player player, String message) {
        return false;
    }
}
