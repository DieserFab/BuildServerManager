package de.dieserfab.buildservermanager.gui.menu;

import de.dieserfab.buildservermanager.BSM;
import de.dieserfab.buildservermanager.gui.AbstractGui;
import de.dieserfab.buildservermanager.utilities.ItemCreator;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class MainMenu extends AbstractGui {
    public MainMenu() {
        super(GuiType.SMALL_CHEST, TITLE, "mainmenu");
    }

    private static final String TITLE = "§8§lMain Menu";
    private static final String MAP_SELECTION = "§8Map Selection";
    private static final List<String> MAP_SELECTION_LORE = Arrays.asList("§7Click to open the overview of all Domains", " ");
    private static final String PLAYERS = "§8Players";
    private static final List<String> PLAYERS_LORE = Arrays.asList("§7Leftclick to view all online players.", " ");
    private static final String PLAYERS_ERROR = "§cError couldnt load Playerhead";

    @Override
    public void init() {
        setItem(10, new ItemCreator(Material.REDSTONE, 1, PLAYERS_ERROR, null).create());
        setItem(13, new ItemCreator(Material.PAPER, 1, MAP_SELECTION, MAP_SELECTION_LORE).create());
        setItem(16, new ItemCreator(Material.BARRIER, 1, "§7Coming soon...", null).create());

    }

    @Override
    public void onGuiOpen(Player player) {
        BSM.getInstance().getGuiManager().addCurrentGui(player, this);
        setItem(10, new ItemCreator(player.getName(), 1, PLAYERS, PLAYERS_LORE).create());
    }

    @Override
    public void onGuiUse(Player player, ItemStack itemUsed, ClickType clickType) {
        if (clickType == ClickType.LEFT) {
            String string = ChatColor.stripColor(itemUsed.getItemMeta().getDisplayName()).replaceAll(" ", "");
            switch (string) {
                case "Players":
                    BSM.getInstance().getGuiManager().getGui("playersmenu").openGui(player);
                    break;
                case "MapSelection":
                    BSM.getInstance().getGuiManager().getGui("domainmenu").openGui(player);
                    break;
            }
        }
    }

    @Override
    public boolean onPlayerChat(Player player, String message) {
        return false;
    }
}
