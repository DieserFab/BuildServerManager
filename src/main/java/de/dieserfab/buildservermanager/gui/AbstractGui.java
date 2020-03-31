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

    @Getter
    @Setter
    private Player player;

    @Getter
    @Setter
    private boolean listenForChat;

    /**
     * Creates an AbstractGui Object based on the parameters
     *
     * @param guiType represents the size of the GUI
     * @param title   is the Displayname/Title of the GUI
     * @param name    is an identifier to differ AbstractGui Objects
     */
    public AbstractGui(GuiType guiType, String title, String name, Player player) {
        Bukkit.getPluginManager().registerEvents(this, BSM.getInstance());
        this.inventory = Bukkit.createInventory(null, guiType.getSize(), title);
        this.title = title;
        this.name = name;
        this.player = player;
        this.listenForChat = false;
        init();
        openGui(player);
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

    public void reopenGui(Player player) {
        player.closeInventory();
        openGui(player);
    }

    public AbstractGui openGui(Player player) {
        onGuiOpen(player);
        player.openInventory(this.inventory);
        BSM.getInstance().getDataManager().getPlayerData(player).setCurrentGui(this);
        return this;
    }

    public void setItem(int slot, ItemStack itemStack) {
        this.inventory.setItem(slot, itemStack);
    }

    public enum GuiType {

        SMALL_CHEST(27),
        BIG_CHEST(54);

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

        CHEST_UPPER_RIGHT(8),
        SMALL_CHEST_MIDDLE_LEFT(9),
        SMALL_CHEST_MIDDLE(13),
        SMALL_CHEST_MIDDLE_RIGHT(17),
        SMALL_CHEST_BOTTOM_MIDDLE(22),
        SMALL_CHEST_BOTTOM_RIGHT(26),
        BIG_CHEST_BOTTOM_LEFT(45),
        BIG_CHEST_BOTTOM_RIGHT(53),
        SMALL_CHEST_BOTTOM_LEFT(18);

        @Getter
        private int slot;

        SlotPosition(int slot) {
            this.slot = slot;
        }
    }

    ;

    public enum GuiHead {
        BLACK_0("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmQ2ODM0M2JkMGIxMjlkZTkzY2M4ZDNiYmEzYjk3YTJmYWE3YWRlMzhkOGE2ZTJiODY0Y2Q4NjhjZmFiIn19fQ=="),
        BLACK_1("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDJhNmYwZTg0ZGFlZmM4YjIxYWE5OTQxNWIxNmVkNWZkYWE2ZDhkYzBjM2NkNTkxZjQ5Y2E4MzJiNTc1In19fQ=="),
        BLACK_2("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTZmYWI5OTFkMDgzOTkzY2I4M2U0YmNmNDRhMGI2Y2VmYWM2NDdkNDE4OWVlOWNiODIzZTljYzE1NzFlMzgifX19"),
        BLACK_3("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2QzMTliOTM0M2YxN2EzNTYzNmJjYmMyNmI4MTk2MjVhOTMzM2RlMzczNjExMWYyZTkzMjgyN2M4ZTc0OSJ9fX0="),
        DOWN_ARROW("MHF_ArrowDown"),
        UP_ARROW("MHF_ArrowUp"),
        LEFT_ARROW("MHF_ArrowLeft"),
        ALERT("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWMzYWFmMTEzN2U1NzgzY2UyNzE0MDJkMDE1YWM4MzUyOWUyYzNmNTNkOTFmNjlhYjYyOWY2YTk1NjVmZWU3OCJ9fX0=");

        @Getter
        private String id;

        GuiHead(String id) {
            this.id = id;
        }
    }

    ;
}
