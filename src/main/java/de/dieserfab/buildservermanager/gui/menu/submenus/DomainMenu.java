package de.dieserfab.buildservermanager.gui.menu.submenus;

import de.dieserfab.buildservermanager.BSM;
import de.dieserfab.buildservermanager.api.BSMAPI;
import de.dieserfab.buildservermanager.gui.AbstractGui;
import de.dieserfab.buildservermanager.gui.menu.MainMenu;
import de.dieserfab.buildservermanager.mapselector.Domain;
import de.dieserfab.buildservermanager.utilities.ItemCreator;
import de.dieserfab.buildservermanager.utilities.Messages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class DomainMenu extends AbstractGui {
    public DomainMenu(GuiType guiType, String title, String name, Player player) {
        super(guiType, title, name, player);
    }


    @Override
    public void init() {
        BSMAPI api = BSMAPI.getInstance();
        if (!api.getDomains().isEmpty()) {
            int count = 0;
            for (Domain domain : api.getDomains()) {
                setItem(count, new ItemCreator(Material.PAPER, 1, "§8§l" + domain.getName(), null).create());
                count++;
            }
            setItem(SlotPosition.SMALL_CHEST_BOTTOM_RIGHT.getSlot(), new ItemCreator(Material.EMERALD_BLOCK, 1, Messages.GUIS_DOMAINMENU_CREATE_DOMAIN, Messages.GUIS_DOMAINMENU_CREATE_DOMAIN_LORE).create());
        } else {
            setItem(SlotPosition.SMALL_CHEST_MIDDLE.getSlot(), new ItemCreator(Material.BARRIER, 1, Messages.GUIS_DOMAINMENU_NO_DOMAIN, Messages.GUIS_DOMAINMENU_NO_DOMAIN_LORE).create());
        }
        setItem(SlotPosition.SMALL_CHEST_BOTTOM_LEFT.getSlot(), new ItemCreator(GuiHead.LEFT_ARROW.getId(), 1, Messages.GUIS_DOMAINMENU_BACK, Messages.GUIS_DOMAINMENU_BACK_LORE).create());
    }

    @Override
    public void onGuiOpen(Player player) {
    }

    @Override
    public void onGuiUse(Player player, ItemStack itemUsed, ClickType clickType) {
        String itemName = itemUsed.getItemMeta().getDisplayName();
        if (itemName.equalsIgnoreCase(Messages.GUIS_DOMAINMENU_BACK)) {
            new MainMenu(AbstractGui.GuiType.SMALL_CHEST, Messages.GUIS_MAINMENU_TITLE, player.getName().toLowerCase() + "_mainmenu", player);
            return;
        }
        if (itemName.equalsIgnoreCase(Messages.GUIS_DOMAINMENU_NO_DOMAIN) || itemName.equalsIgnoreCase(Messages.GUIS_DOMAINMENU_CREATE_DOMAIN)) {
            setListenForChat(true);
            player.closeInventory();
            player.sendMessage(Messages.GUIS_DOMAINMENU_CREATE_MSG);
            return;
        }
        String domain = ChatColor.stripColor(itemName).replaceAll("\\(.*\\)", "");
        new CategoryMenu(GuiType.SMALL_CHEST, "§8§l" + domain + " (§a" + BSMAPI.getInstance().getCategories(domain).size() + "§8§l)", domain, player);
    }

    @Override
    public boolean onPlayerChat(Player player, String message) {
        if (isListenForChat()) {
            String[] strings = message.split(" ");
            if (strings.length != 1) {
                player.sendMessage(Messages.GUIS_DOMAINMENU_WRONG_USAGE);
                return true;
            }
            player.performCommand("maps addDomain " + message);
            setListenForChat(false);
            return true;
        }
        return false;
    }

}
