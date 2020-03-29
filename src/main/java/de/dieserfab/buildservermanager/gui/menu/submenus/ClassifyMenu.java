package de.dieserfab.buildservermanager.gui.menu.submenus;

import de.dieserfab.buildservermanager.BSM;
import de.dieserfab.buildservermanager.api.BSMAPI;
import de.dieserfab.buildservermanager.gui.AbstractGui;
import de.dieserfab.buildservermanager.mapselector.Category;
import de.dieserfab.buildservermanager.mapselector.Domain;
import de.dieserfab.buildservermanager.utilities.ItemCreator;
import de.dieserfab.buildservermanager.utilities.Messages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class ClassifyMenu extends AbstractGui {
    public ClassifyMenu(GuiType guiType, String title, String name) {
        super(guiType, title, name);
    }

    private int state = 0;
    private String classification;

    @Override
    public void init() {
        getInventory().clear();
        int count = 0;
        switch (state) {
            case 0:
                for (String map : BSMAPI.getInstance().getMapsToClassify()) {
                    setItem(count, new ItemCreator(Material.PAPER, count + 1, "§7" + map, null).create());
                    count++;
                }
                break;
            case 1:
                for (Domain domain : BSMAPI.getInstance().getDomains()) {
                    setItem(count, new ItemCreator(Material.PAPER, count + 1, "§7" + domain.getName(), null).create());
                    count++;
                }
                if (BSMAPI.getInstance().getDomains().isEmpty()) {
                    setItem(SlotPosition.SMALL_CHEST_MIDDLE.getSlot(), new ItemCreator(Material.BARRIER, 1, Messages.GUIS_DOMAINMENU_NO_DOMAIN, Messages.GUIS_DOMAINMENU_NO_DOMAIN_LORE).create());
                } else {
                    setItem(SlotPosition.BIG_CHEST_BOTTOM_RIGHT.getSlot(), new ItemCreator(Material.EMERALD_BLOCK, 1, Messages.GUIS_DOMAINMENU_CREATE_DOMAIN, Messages.GUIS_DOMAINMENU_CREATE_DOMAIN_LORE).create());
                }
                break;
            case 2:
                String[] parts = classification.split(" ");
                for (Category category : BSMAPI.getInstance().getCategories(ChatColor.stripColor(parts[1]))) {
                    setItem(count, new ItemCreator(Material.PAPER, count + 1, "§7" + category.getName(), null).create());
                    count++;
                }
                if (BSMAPI.getInstance().getCategories(ChatColor.stripColor(parts[1])).isEmpty()) {
                    setItem(SlotPosition.SMALL_CHEST_MIDDLE.getSlot(), new ItemCreator(Material.BARRIER, 1, Messages.GUIS_CATEGORYMENU_NO_CATEGORY, Messages.GUIS_CATEGORYMENU_NO_CATEGORY_LORE).create());
                } else {
                    setItem(SlotPosition.BIG_CHEST_BOTTOM_RIGHT.getSlot(), new ItemCreator(Material.EMERALD_BLOCK, 1, Messages.GUIS_CATEGORYMENU_CREATE_CATEGORY, Messages.GUIS_CATEGORYMENU_CREATE_CATEGORY_LORE).create());
                }
                break;
        }
    }

    @Override
    public void onGuiOpen(Player player) {
        init();
    }

    private String input;

    @Override
    public void onGuiUse(Player player, ItemStack itemUsed, ClickType clickType) {
        String itemName = itemUsed.getItemMeta().getDisplayName();
        if (itemName.equalsIgnoreCase(Messages.GUIS_DOMAINMENU_NO_DOMAIN) || itemName.equalsIgnoreCase(Messages.GUIS_DOMAINMENU_CREATE_DOMAIN)) {
            input = "domain";
            player.closeInventory();
            player.sendMessage(Messages.GUIS_DOMAINMENU_CREATE_MSG);
            return;
        }
        if (itemName.equalsIgnoreCase(Messages.GUIS_CATEGORYMENU_NO_CATEGORY) || itemName.equalsIgnoreCase(Messages.GUIS_CATEGORYMENU_CREATE_CATEGORY)) {
            input = "category";
            player.closeInventory();
            player.sendMessage(Messages.GUIS_CATEGORYMENU_CREATE_MSG);
            return;
        }
        switch (state) {
            case 0:
                classification = ChatColor.stripColor(itemUsed.getItemMeta().getDisplayName()) + " ";
                state = 1;
                setTitle(Messages.GUIS_CLASSIFYMENU_DOMAIN_TITLE);
                setInventory(Bukkit.createInventory(null, GuiType.BIG_CHEST.getSize(), getTitle()));
                reopenGui(player);
                return;
            case 1:
                classification = classification + ChatColor.stripColor(itemUsed.getItemMeta().getDisplayName() + " ");
                state = 2;
                setTitle(Messages.GUIS_CLASSIFYMENU_CATEGORY_TITLE);
                setInventory(Bukkit.createInventory(null, GuiType.BIG_CHEST.getSize(), getTitle()));
                reopenGui(player);
                return;
            case 2:
                classification = classification + ChatColor.stripColor(itemUsed.getItemMeta().getDisplayName());
                String[] parts = classification.split(" ");
                BSMAPI.getInstance().addMap(parts[1], parts[2], parts[0]);
                classification = "";
                state = 0;
                setTitle(Messages.GUIS_CLASSIFYMENU_TITLE);
                setInventory(Bukkit.createInventory(null, GuiType.BIG_CHEST.getSize(), getTitle()));
                reopenGui(player);
                return;
        }
    }

    @Override
    public boolean onPlayerChat(Player player, String message) {
        if (input == null)
            return false;
        if (input.equalsIgnoreCase("domain")) {
            String[] strings = message.split(" ");
            if (strings.length != 1) {
                player.sendMessage(Messages.GUIS_DOMAINMENU_WRONG_USAGE);
                return true;
            }
            player.performCommand("maps addDomain " + message);
            BSM.getInstance().getGuiManager().getCurrentGuis().get(player).openGui(player);
            input = null;
            return true;
        }
        if (input.equalsIgnoreCase("category")) {
            String[] strings = message.split(" ");
            if (strings.length != 1) {
                player.sendMessage(Messages.GUIS_CATEGORYMENU_WRONG_USAGE);
                return true;
            }
            String[] parts = classification.split(" ");
            player.performCommand("maps addCategory " + parts[1] + " " + strings[0]);
            BSM.getInstance().getGuiManager().getCurrentGuis().get(player).openGui(player);
            input = null;
            return true;
        }
        return false;
    }
}
