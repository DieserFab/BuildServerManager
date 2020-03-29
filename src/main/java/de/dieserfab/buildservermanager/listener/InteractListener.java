package de.dieserfab.buildservermanager.listener;

import de.dieserfab.buildservermanager.utilities.Messages;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class InteractListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack itemStack = event.getCurrentItem();
        if (itemStack == null || itemStack.getType() == Material.AIR)
            return;
        if (itemStack.getItemMeta().getDisplayName().equalsIgnoreCase(Messages.ITEMS_EMERALD_NAME)) {
            if (event.getClick() == ClickType.CREATIVE) {
                event.setCancelled(true);
                return;
            }
            player.performCommand("maps open");
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            ItemStack inHand = player.getItemInHand();
            if (inHand == null || inHand.getType() == Material.AIR)
                return;
            if (inHand.getItemMeta().getDisplayName().equalsIgnoreCase(Messages.ITEMS_EMERALD_NAME)) {
                player.performCommand("maps open");
            }
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        event.getDrops().clear();
        event.setKeepInventory(true);
    }

}
