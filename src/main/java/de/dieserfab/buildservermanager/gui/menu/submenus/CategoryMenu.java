package de.dieserfab.buildservermanager.gui.menu.submenus;

import de.dieserfab.buildservermanager.BSM;
import de.dieserfab.buildservermanager.api.BSMAPI;
import de.dieserfab.buildservermanager.gui.AbstractGui;
import de.dieserfab.buildservermanager.mapselector.Category;
import de.dieserfab.buildservermanager.utilities.ItemCreator;
import de.dieserfab.buildservermanager.utilities.Messages;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class CategoryMenu extends AbstractGui {
    public CategoryMenu(GuiType guiType, String title, String name, Player player) {
        super(guiType, title, name, player);
    }

    @Override
    public void init() {
        BSMAPI api = BSMAPI.getInstance();
        if (!api.getCategories(getName()).isEmpty()) {
            int count = 0;
            for (Category category : api.getCategories(getName())) {
                setItem(count, new ItemCreator(Material.PAPER, 1, "§8§l" + category.getName(), null).create());
                count++;
            }
            setItem(SlotPosition.SMALL_CHEST_BOTTOM_RIGHT.getSlot(), new ItemCreator(Material.EMERALD_BLOCK, 1, Messages.GUIS_CATEGORYMENU_CREATE_CATEGORY, Messages.GUIS_CATEGORYMENU_CREATE_CATEGORY_LORE).create());
        } else {
            setItem(SlotPosition.SMALL_CHEST_MIDDLE.getSlot(), new ItemCreator(Material.BARRIER, 1, Messages.GUIS_CATEGORYMENU_NO_CATEGORY, Messages.GUIS_CATEGORYMENU_NO_CATEGORY_LORE).create());
        }
        setItem(SlotPosition.SMALL_CHEST_BOTTOM_LEFT.getSlot(), new ItemCreator(GuiHead.LEFT_ARROW.getId(), 1, Messages.GUIS_CATEGORYMENU_BACK, Messages.GUIS_CATEGORYMENU_BACK_LORE).create());
    }

    @Override
    public void onGuiOpen(Player player) {
    }

    @Override
    public void onGuiUse(Player player, ItemStack itemUsed, ClickType clickType) {
        String itemName = itemUsed.getItemMeta().getDisplayName();
        if (itemName.equalsIgnoreCase(Messages.GUIS_CATEGORYMENU_BACK)) {
            new DomainMenu(GuiType.SMALL_CHEST, "§8§lDomain Menu (§a" + BSMAPI.getInstance().getDomains().size() + "§8§l)", player.getName().toLowerCase() + "_domainmenu", player);
            return;
        }
        if (itemName.equalsIgnoreCase(Messages.GUIS_CATEGORYMENU_NO_CATEGORY) || itemName.equalsIgnoreCase(Messages.GUIS_CATEGORYMENU_CREATE_CATEGORY)) {
            setListenForChat(true);
            player.closeInventory();
            player.sendMessage(Messages.GUIS_CATEGORYMENU_CREATE_MSG);
            return;
        }
        String category = ChatColor.stripColor(itemName).replaceAll("\\(.*\\)", "");
        new MapMenu(GuiType.BIG_CHEST, "§8§l" + category + " (§a" + BSMAPI.getInstance().getMaps(getName(), category).size() + "§8§l)", getName() + "$" + category, player);

    }

    @Override
    public boolean onPlayerChat(Player player, String message) {
        if (isListenForChat()) {
            String[] strings = message.split(" ");
            if (strings.length != 1) {
                player.sendMessage(Messages.GUIS_CATEGORYMENU_WRONG_USAGE);
                return true;
            }
            String domain = ChatColor.stripColor(getTitle()).replaceAll("\\(.*\\)", "").replaceAll(" ", "");
            System.out.println("AA" + domain + "AA");
            System.out.println("AA" + strings[0] + "AA");
            System.out.println("maps addCategory " + domain + " " + strings[0]);
            System.out.println(BSM.getInstance().getDomainManager().getDomain(domain) == null);
            System.out.println(BSM.getInstance().getDomainManager().getDomain(domain).getCategories() == null);
            player.performCommand("maps addCategory " + domain + " " + strings[0]);
            setListenForChat(false);
            return true;
        }
        return false;
    }
}