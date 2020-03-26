package de.dieserfab.buildservermanager.gui.menu.submenus;

import de.dieserfab.buildservermanager.BSM;
import de.dieserfab.buildservermanager.gui.AbstractGui;
import de.dieserfab.buildservermanager.utilities.ItemCreator;
import de.dieserfab.buildservermanager.utilities.Logger;
import de.dieserfab.buildservermanager.utilities.Messages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class PlayersMenu extends AbstractGui {
    public PlayersMenu() {
        super(GuiType.BIG_CHEST, Messages.GUIS_MAINMENU_PLAYERS, "playersmenu");
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
        BSM.getInstance().getGuiManager().addCurrentGui(player, this);
    }

    @Override
    public void onGuiUse(Player player, ItemStack itemUsed, ClickType clickType) {
        if (itemUsed.getItemMeta().getDisplayName().equalsIgnoreCase(Messages.GUIS_PLAYERSMENU_BACK)) {
            BSM.getInstance().getGuiManager().getGui("mainmenu").openGui(player);
            return;
        }
        String string = ChatColor.stripColor(itemUsed.getItemMeta().getDisplayName());
        try {
            Player p = Bukkit.getPlayer(string);
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
