package de.dieserfab.buildservermanager.gui.menu.submenus;

import de.dieserfab.buildservermanager.BSM;
import de.dieserfab.buildservermanager.api.BSMAPI;
import de.dieserfab.buildservermanager.gui.AbstractGui;
import de.dieserfab.buildservermanager.mapselector.Map;
import de.dieserfab.buildservermanager.utilities.ItemCreator;
import de.dieserfab.buildservermanager.utilities.Logger;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MapMenu extends AbstractGui {
    public MapMenu(GuiType guiType, String title, String name) {
        super(guiType, title, name);
    }

    private static final String TELEPORT_FAIL = "§cThere was an error while teleporting check the console for more information.";
    private static final String BACK = "§8§lBack to the Category Menu";
    private static final List<String> BACK_LORE = Arrays.asList("§7Click here to get back to the Category Menu.", " ");
    private static final String CREATE_MAP = "§a§lCreate a new Map";
    private static final List<String> CREATE_MAP_LORE = Arrays.asList("§aClick here to create a new Map.", " ");
    private static final String WRONG_USAGE = "§7Wrong arguments the §amap §7must consist of the following: §a<name> <void/nether/end/normal>§7.";
    private static final String CREATE_MSG = "§7Type the desired §aname §7for the §amap §7(you cant use spaces in the name):";
    private static final String NO_MAP = "§cTheres no Map to choose from.";
    private static final List<String> NO_MAP_LORE = Arrays.asList("§7To create a Map you can either §aleftclick §7this", "§7icon or use the command /maps addMap <domain> <category> <name> <void/nether/end/normal>", " ");
    private static final List<String> MAP_LORE = Arrays.asList("§7Press leftclick to teleport to that map.", "§7Press rightclick to manage the settings of that map.", " ");
    private static final List<String> MAP_LORE_NOT_LOADED = Arrays.asList("§7This Map isnt loaded press leftclick to load and teleport to the Map.", "§7Keep in mind loading the Map will cause a short lag.", " ");

    @Getter
    private String domain, category;

    public MapMenu setValues(String domain, String category) {
        this.domain = domain;
        this.category = category;
        postInit();
        return this;
    }


    @Override
    public void init() {

    }

    public void postInit() {
        BSMAPI api = BSMAPI.getInstance();
        getInventory().clear();
        if (!api.getMaps(getDomain(), getName()).isEmpty()) {
            int count = 0;
            for (Map map : api.getMaps(getDomain(), getName())) {
                setItem(count, new ItemCreator(Material.PAPER, count + 1, "§8§l" + map.getName() + loadPrefix(!(Bukkit.getWorld(map.getName()) == null)), Bukkit.getWorld(map.getName()) == null ? MAP_LORE_NOT_LOADED : MAP_LORE).create());
                count++;
            }
            setItem(SlotPosition.BIG_CHEST_BOTTOM_RIGHT.getSlot(), new ItemCreator(Material.EMERALD_BLOCK, 1, CREATE_MAP, CREATE_MAP_LORE).create());
            setItem(SlotPosition.BIG_CHEST_BOTTOM_LEFT.getSlot(), new ItemCreator("MHF_ArrowLeft", 1, BACK, BACK_LORE).create());
        } else {
            setItem(31, new ItemCreator(Material.BARRIER, 1, NO_MAP, NO_MAP_LORE).create());
            setItem(SlotPosition.BIG_CHEST_BOTTOM_LEFT.getSlot(), new ItemCreator("MHF_ArrowLeft", 1, BACK, BACK_LORE).create());
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
            new CategoryMenu(GuiType.SMALL_CHEST, "§8§l" + getDomain() + " (§a" + BSMAPI.getInstance().getCategories(getDomain()).size() + "§8§l)", getDomain().toLowerCase()).setDomain(getDomain()).openGui(player);
            return;
        }
        if (itemUsed.getItemMeta().getDisplayName().equalsIgnoreCase(NO_MAP) || itemUsed.getItemMeta().getDisplayName().equalsIgnoreCase(CREATE_MAP)) {
            create.add(player);
            player.closeInventory();
            player.sendMessage(CREATE_MSG);
            return;
        }
        if (clickType == ClickType.LEFT) {

            String string = ChatColor.stripColor(itemUsed.getItemMeta().getDisplayName()).replaceAll("\\(.*\\)", "");
            if (Bukkit.getWorld(string) == null) {
                WorldCreator creator = new WorldCreator(string);
                try {
                    Bukkit.getServer().createWorld(creator);
                    player.teleport(Bukkit.getWorld(string).getSpawnLocation());
                } catch (Exception e) {
                    player.sendMessage(TELEPORT_FAIL);
                    Logger.l("eError while teleporting:" + e.getMessage());
                }
            } else {
                player.teleport(Bukkit.getWorld(string).getSpawnLocation());
            }
        }
        if (clickType == ClickType.RIGHT) {
            String string = ChatColor.stripColor(itemUsed.getItemMeta().getDisplayName()).replaceAll("\\(.*\\)", "");
            new MapSettingsMenu(GuiType.SMALL_CHEST, "§8§l" + string + loadPrefix(!(Bukkit.getWorld(string) == null)), string.toLowerCase()).setValue(getDomain(), getCategory()).openGui(player);
        }
    }

    private List<Player> create = new ArrayList<>();

    @Override
    public boolean onPlayerChat(Player player, String message) {
        if (create.contains(player)) {
            String[] strings = message.split(" ");
            if (strings.length != 2) {
                player.sendMessage(WRONG_USAGE);
                return true;
            }
            player.performCommand("maps addMap " + getDomain() + " " + getCategory() + " " + strings[0] + " " + strings[1]);
            create.remove(player);
            return true;
        }
        return false;
    }

    private String loadPrefix(boolean loaded) {
        return loaded ? "§8(§aloaded§8)" : "§8(§cnot loaded§8)";
    }

}
