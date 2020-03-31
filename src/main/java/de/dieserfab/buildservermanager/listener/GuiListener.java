package de.dieserfab.buildservermanager.listener;

import de.dieserfab.buildservermanager.BSM;
import de.dieserfab.buildservermanager.data.PlayerData;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerChatEvent;

public class GuiListener implements Listener {

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        PlayerData data = BSM.getInstance().getDataManager().getPlayerData(player);
        if (data.getCurrentGui() == null)
            return;
        if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR || !event.getView().getTitle().equalsIgnoreCase(data.getCurrentGui().getTitle()))
            return;
        data.getCurrentGui().onGuiUse(player, event.getCurrentItem(), event.getClick());
        event.setCancelled(true);
        return;
    }

    @EventHandler
    public void onPlayerChat(PlayerChatEvent event) {
        Player player = event.getPlayer();
        PlayerData data = BSM.getInstance().getDataManager().getPlayerData(player);
        event.setCancelled(data.getCurrentGui() != null && data.getCurrentGui().onPlayerChat(player, event.getMessage()));
    }
}
