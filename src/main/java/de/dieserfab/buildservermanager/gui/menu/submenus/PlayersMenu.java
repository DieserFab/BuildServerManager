package de.dieserfab.buildservermanager.gui.menu.submenus;

import de.dieserfab.buildservermanager.BSM;
import de.dieserfab.buildservermanager.gui.AbstractGui;
import de.dieserfab.buildservermanager.utilities.ItemCreator;
import de.dieserfab.buildservermanager.utilities.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class PlayersMenu extends AbstractGui {
    public PlayersMenu() {
        super(GuiType.BIG_CHEST, TITLE, "playersmenu");
    }

    private static final String BACK = "§8§lBack to the Main Menu";
    private static final List<String> BACK_LORE = Arrays.asList("§7Click here to get back to the Main Menu.", " ");
    private static final String TITLE = "§8§lPlayers";
    private static final String TELEPORT_SUCCESS = "§7You teleported to the player §a%player%§7.";
    private static final String TELEPORT_FAIL = "§cError while trying to teleport see console for more information.";
    private static final List<String> PLAYER_LORE = Arrays.asList("§7To teleport to the player click here.", " ");

    @Override
    public void init() {
        int count = 0;
        for (Player player : Bukkit.getOnlinePlayers()) {
            setItem(count, new ItemCreator(player.getName(), 1, "§7" + player.getName(), PLAYER_LORE).create());
            count++;
        }
        setItem(SlotPosition.BIG_CHEST_BOTTOM_LEFT.getSlot(), new ItemCreator("MHF_ArrowLeft", 1, BACK, BACK_LORE).create());
    }

    @Override
    public void onGuiOpen(Player player) {
        init();
        BSM.getInstance().getGuiManager().addCurrentGui(player, this);
    }

    @Override
    public void onGuiUse(Player player, ItemStack itemUsed, ClickType clickType) {
        if (itemUsed.getItemMeta().getDisplayName().equalsIgnoreCase(BACK)) {
            BSM.getInstance().getGuiManager().getGui("mainmenu").openGui(player);
            return;
        }
        String string = ChatColor.stripColor(itemUsed.getItemMeta().getDisplayName());
        try {
            Player p = Bukkit.getPlayer(string);
            player.sendMessage(TELEPORT_SUCCESS.replace("%player%", p.getName()));
            player.teleport(p.getLocation());
        } catch (Exception e) {
            player.sendMessage(TELEPORT_FAIL);
            Logger.l("eError occured while teleporting player:" + e.getMessage());
        }
    }

    @Override
    public boolean onPlayerChat(Player player, String message) {
        return false;
    }
}
