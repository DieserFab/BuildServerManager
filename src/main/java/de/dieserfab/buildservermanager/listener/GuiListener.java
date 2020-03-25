package de.dieserfab.buildservermanager.listener;

import de.dieserfab.buildservermanager.BSM;
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
        if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR || !event.getView().getTitle().equalsIgnoreCase(BSM.getInstance().getGuiManager().getCurrentGuis().get(player).getTitle()))
            return;
        BSM.getInstance().getGuiManager().getCurrentGuis().get(player).onGuiUse(player, event.getCurrentItem(), event.getClick());
        event.setCancelled(true);
        return;
    }

    @EventHandler
    public void onPlayerChat(PlayerChatEvent event) {
        Player player = event.getPlayer();
        event.setCancelled(BSM.getInstance().getGuiManager().getCurrentGuis().get(player) != null && BSM.getInstance().getGuiManager().getCurrentGuis().get(player).onPlayerChat(player, event.getMessage()));
    }
}
