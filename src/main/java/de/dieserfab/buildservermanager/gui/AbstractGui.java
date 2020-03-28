package de.dieserfab.buildservermanager.gui;

import de.dieserfab.buildservermanager.BSM;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public abstract class AbstractGui implements Listener {

    @Getter
    @Setter
    private Inventory inventory;

    @Getter
    @Setter
    private String title, name;

    /**
     * Creates an AbstractGui Object based on the parameters
     *
     * @param guiType represents the size of the GUI
     * @param title   is the Displayname/Title of the GUI
     * @param name    is an identifier to differ AbstractGui Objects
     */
    public AbstractGui(GuiType guiType, String title, String name) {
        Bukkit.getPluginManager().registerEvents(this, BSM.getInstance());
        this.inventory = Bukkit.createInventory(null, guiType.getSize(), title);
        this.title = title;
        this.name = name;
        init();
    }

    /**
     * Called when the Object get created
     */
    public abstract void init();

    /**
     * Gets called everytime a player opens the GUI
     *
     * @param player is the player that opened the GUI
     */
    public abstract void onGuiOpen(Player player);

    /**
     * Gets called everytime a player uses the GUI
     *
     * @param player    is the player that uses the GUI
     * @param itemUsed  is the ItemStack that got clicked
     * @param clickType is the way the player interacted with the ItemStack
     */
    public abstract void onGuiUse(Player player, ItemStack itemUsed, ClickType clickType);

    /**
     * Gets called every time the player uses the chat to make use of chat input
     *
     * @param player  is the player that used the chat
     * @param message is the message the player typed
     * @return
     */
    public abstract boolean onPlayerChat(Player player, String message);

    public AbstractGui openGui(Player player) {
        onGuiOpen(player);
        BSM.getInstance().getGuiManager().addCurrentGui(player, this);
        player.openInventory(this.inventory);
        return this;
    }

    public void setItem(int slot, ItemStack itemStack) {
        this.inventory.setItem(slot, itemStack);
    }

    public enum GuiType {

        SMALL_CHEST(27), BIG_CHEST(54);

        private int size;

        GuiType(int size) {
            this.size = size;
        }

        public int getSize() {
            return size;
        }
    }

    ;

    public enum SlotPosition {

        SMALL_CHEST_MIDDLE_LEFT(9), SMALL_CHEST_MIDDLE(13), SMALL_CHEST_MIDDLE_RIGHT(17), SMALL_CHEST_BOTTOM_RIGHT(26), BIG_CHEST_BOTTOM_LEFT(45),BIG_CHEST_BOTTOM_RIGHT(53),SMALL_CHEST_BOTTOM_LEFT(18);

        @Getter
        private int slot;

        SlotPosition(int slot) {
            this.slot = slot;
        }
    }

    ;
}
