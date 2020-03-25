package de.dieserfab.buildservermanager.utilities;

import de.dieserfab.buildservermanager.data.ProtocolVersion;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;

public class ItemCreator {

    ItemStack itemStack;

    /**
     * Creates an ItemCreator
     *
     * @param material is the Material of the ItemStack
     * @param amount   is the amount of the ItemStack's item
     * @param name     is the Displayname of the ItemStack
     * @param lore     is the Lore of the ItemStack
     */
    public ItemCreator(Material material, int amount, String name, List<String> lore) {
        this.itemStack = new ItemStack(material);
        this.itemStack.setAmount(amount);
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemMeta.setLore(lore);
        this.itemStack.setItemMeta(itemMeta);
    }

    /**
     * Creates an ItemCreator
     *
     * @param skullName instead of an item it auto creates an skull from the player
     * @param amount    is the amount of the ItemStack's item
     * @param name      is the Displayname of the ItemStack
     * @param lore      is the Lore of the ItemStack
     */
    public ItemCreator(String skullName, int amount, String name, List<String> lore) {
        if (ProtocolVersion.isHigherOrEqual(ProtocolVersion.V1_14)) {
            this.itemStack = new ItemStack(Material.valueOf("PLAYER_HEAD"));
        } else {
            this.itemStack = new ItemStack(Material.valueOf("SKULL_ITEM"), 1, (short) 3);
        }
        SkullMeta skullMeta = (SkullMeta) this.itemStack.getItemMeta();
        skullMeta.setOwner(skullName);
        this.itemStack.setItemMeta(skullMeta);
        this.itemStack.setAmount(amount);
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemMeta.setLore(lore);
        this.itemStack.setItemMeta(itemMeta);
    }

    /**
     * Used to get the actual ItemStack of this class
     *
     * @return
     */
    public ItemStack create() {
        return itemStack;
    }

}
