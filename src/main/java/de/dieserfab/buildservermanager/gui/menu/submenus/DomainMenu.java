package de.dieserfab.buildservermanager.gui.menu.submenus;

import de.dieserfab.buildservermanager.BSM;
import de.dieserfab.buildservermanager.api.BSMAPI;
import de.dieserfab.buildservermanager.gui.AbstractGui;
import de.dieserfab.buildservermanager.mapselector.Domain;
import de.dieserfab.buildservermanager.utilities.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DomainMenu extends AbstractGui {
    public DomainMenu() {
        super(GuiType.SMALL_CHEST, "§8§lDomain Menu (§a" + BSMAPI.getInstance().getDomains().size() + "§8§l)", "domainmenu");
    }

    private static final String BACK = "§8§lBack to the Main Menu";
    private static final List<String> BACK_LORE = Arrays.asList("§7Click here to get back to the Main Menu.", " ");
    private static final String CREATE_DOMAIN = "§a§lCreate a new Domain";
    private static final List<String> CREATE_DOMAIN_LORE = Arrays.asList("§aClick here to create a new Domain.", " ");
    private static final String WRONG_USAGE = "§7The Domain cant have spaces in the name.";
    private static final String CREATE_MSG = "§7Type the desired §aname §7for the §adomain §7(you cant use spaces in the name):";
    private static final String NO_DOMAIN = "§cTheres no Domain to choose from.";
    private static final List<String> NO_DOMAIN_LORE = Arrays.asList("§7To create a Domain you can either leftclick this", "§7icon or use the command /maps addDomain <name>", " ");

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
            setItem(SlotPosition.SMALL_CHEST_BOTTOM_RIGHT.getSlot(), new ItemCreator(Material.EMERALD_BLOCK, 1, CREATE_DOMAIN, CREATE_DOMAIN_LORE).create());
            setItem(SlotPosition.SMALL_CHEST_BOTTOM_LEFT.getSlot(), new ItemCreator("MHF_ArrowLeft", 1, BACK, BACK_LORE).create());
        } else {
            setItem(13, new ItemCreator(Material.BARRIER, 1, NO_DOMAIN, NO_DOMAIN_LORE).create());
            setItem(SlotPosition.SMALL_CHEST_BOTTOM_LEFT.getSlot(), new ItemCreator("MHF_ArrowLeft", 1, BACK, BACK_LORE).create());
        }
    }

    @Override
    public void onGuiOpen(Player player) {
        init();
        BSM.getInstance().getGuiManager().addCurrentGui(player, this);
    }

    @Override
    public void onGuiUse(Player player, ItemStack itemUsed, ClickType clickType) {
        if (itemUsed.getItemMeta().getDisplayName().equalsIgnoreCase(BACK)) {
            BSM.getInstance().getGuiManager().getGui("mainmenu").openGui(player);
            return;
        }
        if (itemUsed.getItemMeta().getDisplayName().equalsIgnoreCase(NO_DOMAIN) || itemUsed.getItemMeta().getDisplayName().equalsIgnoreCase(CREATE_DOMAIN)) {
            create.add(player);
            player.closeInventory();
            player.sendMessage(CREATE_MSG);
            return;
        }
        String string = ChatColor.stripColor(itemUsed.getItemMeta().getDisplayName()).replaceAll("\\(.*\\)", "");
        new CategoryMenu(GuiType.SMALL_CHEST, "§8§l" + string + " (§a" + BSMAPI.getInstance().getCategories(string).size() + "§8§l)", string.toLowerCase()).setDomain(string).openGui(player);
    }

    private List<Player> create = new ArrayList<>();
    public BukkitTask task;

    @Override
    public boolean onPlayerChat(Player player, String message) {
        if (create.contains(player)) {
            String[] strings = message.split(" ");
            if (strings.length != 1) {
                player.sendMessage(WRONG_USAGE);
                return true;
            }
            player.performCommand("maps addDomain " + message);
            create.remove(player);
            return true;
        }
        return false;
    }

}
