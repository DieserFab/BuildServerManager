package de.dieserfab.buildservermanager.gui.menu.submenus;

import de.dieserfab.buildservermanager.BSM;
import de.dieserfab.buildservermanager.api.BSMAPI;
import de.dieserfab.buildservermanager.gui.AbstractGui;
import de.dieserfab.buildservermanager.mapselector.Category;
import de.dieserfab.buildservermanager.utilities.ItemCreator;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CategoryMenu extends AbstractGui {
    public CategoryMenu(GuiType guiType, String title, String name) {
        super(guiType, title, name);
    }

    private static final String BACK = "§8§lBack to the Domain Menu";
    private static final List<String> BACK_LORE = Arrays.asList("§7Click here to get back to the Domain Menu.", " ");
    private static final String CREATE_CATEGORY = "§a§lCreate a new Category";
    private static final List<String> CREATE_CATEGORY_LORE = Arrays.asList("§aClick here to create a new Category.", " ");
    private static final String WRONG_USAGE = "§7The Category cant have spaces.";
    private static final String CREATE_MSG = "§7Type the desired §aname §7for the §acategory §7(you cant use spaces in the category):";
    private static final String NO_DOMAIN = "§cTheres no Category to choose from.";
    private static final List<String> NO_DOMAIN_LORE = Arrays.asList("§7To create a Category you can either click this", "§7icon or use the command /maps addCategory <domain> <name>", " ");

    @Getter
    private String domain;

    public AbstractGui setDomain(String domain) {
        this.domain = domain;
        return this;
    }

    @Override
    public void init() {
        BSMAPI api = BSMAPI.getInstance();
        getInventory().clear();
        if (!api.getCategories(getName()).isEmpty()) {
            int count = 0;
            for (Category category : api.getCategories(getName())) {
                setItem(count, new ItemCreator(Material.PAPER, 1, "§8§l" + category.getName(), NO_DOMAIN_LORE).create());
                count++;
            }
            setItem(SlotPosition.SMALL_CHEST_BOTTOM_RIGHT.getSlot(), new ItemCreator(Material.EMERALD_BLOCK, 1, CREATE_CATEGORY, CREATE_CATEGORY_LORE).create());
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
            BSM.getInstance().getGuiManager().getGui("domainmenu").openGui(player);
            return;
        }
        if (itemUsed.getItemMeta().getDisplayName().equalsIgnoreCase(NO_DOMAIN) || itemUsed.getItemMeta().getDisplayName().equalsIgnoreCase(CREATE_CATEGORY)) {
            create.add(player);
            player.closeInventory();
            player.sendMessage(CREATE_MSG);
            return;
        }
        String string = ChatColor.stripColor(itemUsed.getItemMeta().getDisplayName()).replaceAll("\\(.*\\)", "");
        new MapMenu(GuiType.BIG_CHEST, "§8§l" + string + " (§a" + BSMAPI.getInstance().getMaps(getName(), string).size() + "§8§l)", string.toLowerCase()).setValues(getName(), string).openGui(player);

    }

    private List<Player> create = new ArrayList<>();

    @Override
    public boolean onPlayerChat(Player player, String message) {
        if (create.contains(player)) {
            String[] strings = message.split(" ");
            if (strings.length != 1) {
                player.sendMessage(WRONG_USAGE);
                return true;
            }
            player.performCommand("maps addCategory " + getDomain() + " " + strings[0]);
            create.remove(player);
            return true;
        }
        return false;
    }
}