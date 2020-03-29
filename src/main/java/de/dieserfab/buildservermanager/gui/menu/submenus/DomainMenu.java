package de.dieserfab.buildservermanager.gui.menu.submenus;

import de.dieserfab.buildservermanager.BSM;
import de.dieserfab.buildservermanager.api.BSMAPI;
import de.dieserfab.buildservermanager.gui.AbstractGui;
import de.dieserfab.buildservermanager.mapselector.Domain;
import de.dieserfab.buildservermanager.utilities.ItemCreator;
import de.dieserfab.buildservermanager.utilities.Messages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public class DomainMenu extends AbstractGui {
    public DomainMenu() {
        super(GuiType.SMALL_CHEST, "§8§lDomain Menu (§a" + BSMAPI.getInstance().getDomains().size() + "§8§l)", "domainmenu");
    }


    @Override
    public void init() {
        BSMAPI api = BSMAPI.getInstance();
        setInventory(Bukkit.createInventory(null, GuiType.SMALL_CHEST.getSize(), "§8§lDomain Menu (§a" + BSMAPI.getInstance().getDomains().size() + "§8§l)"));
        if (!api.getDomains().isEmpty()) {
            int count = 0;
            for (Domain domain : api.getDomains()) {
                setItem(count, new ItemCreator(Material.PAPER, 1, "§8§l" + domain.getName(), null).create());
                count++;
            }
            setItem(SlotPosition.SMALL_CHEST_BOTTOM_RIGHT.getSlot(), new ItemCreator(Material.EMERALD_BLOCK, 1, Messages.GUIS_DOMAINMENU_CREATE_DOMAIN, Messages.GUIS_DOMAINMENU_CREATE_DOMAIN_LORE).create());
            setItem(SlotPosition.SMALL_CHEST_BOTTOM_LEFT.getSlot(), new ItemCreator("MHF_ArrowLeft", 1, Messages.GUIS_DOMAINMENU_BACK, Messages.GUIS_DOMAINMENU_BACK_LORE).create());
        } else {
            setItem(13, new ItemCreator(Material.BARRIER, 1, Messages.GUIS_DOMAINMENU_NO_DOMAIN, Messages.GUIS_DOMAINMENU_NO_DOMAIN_LORE).create());
            setItem(SlotPosition.SMALL_CHEST_BOTTOM_LEFT.getSlot(), new ItemCreator("MHF_ArrowLeft", 1, Messages.GUIS_DOMAINMENU_BACK, Messages.GUIS_DOMAINMENU_BACK_LORE).create());
        }
    }

    @Override
    public void onGuiOpen(Player player) {
        init();
    }

    @Override
    public void onGuiUse(Player player, ItemStack itemUsed, ClickType clickType) {
        String itemName = itemUsed.getItemMeta().getDisplayName();
        if (itemName.equalsIgnoreCase(Messages.GUIS_DOMAINMENU_BACK)) {
            BSM.getInstance().getGuiManager().getGui("mainmenu").openGui(player);
            return;
        }
        if (itemName.equalsIgnoreCase(Messages.GUIS_DOMAINMENU_NO_DOMAIN) || itemName.equalsIgnoreCase(Messages.GUIS_DOMAINMENU_CREATE_DOMAIN)) {
            create.add(player);
            player.closeInventory();
            player.sendMessage(Messages.GUIS_DOMAINMENU_CREATE_MSG);
            return;
        }
        String category = ChatColor.stripColor(itemName).replaceAll("\\(.*\\)", "");
        new CategoryMenu(GuiType.SMALL_CHEST, "§8§l" + category + " (§a" + BSMAPI.getInstance().getCategories(category).size() + "§8§l)", category.toLowerCase()).setDomain(category).openGui(player);
    }

    private List<Player> create = new ArrayList<>();
    public BukkitTask task;

    @Override
    public boolean onPlayerChat(Player player, String message) {
        if (create.contains(player)) {
            String[] strings = message.split(" ");
            if (strings.length != 1) {
                player.sendMessage(Messages.GUIS_DOMAINMENU_WRONG_USAGE);
                return true;
            }
            player.performCommand("maps addDomain " + message);
            create.remove(player);
            return true;
        }
        return false;
    }

}
