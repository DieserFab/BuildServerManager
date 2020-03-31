package de.dieserfab.buildservermanager.gui.menu.submenus;

import de.dieserfab.buildservermanager.BSM;
import de.dieserfab.buildservermanager.gui.AbstractGui;
import de.dieserfab.buildservermanager.utilities.ItemCreator;
import de.dieserfab.buildservermanager.utilities.Messages;
import de.dieserfab.buildservermanager.utilities.StringUtilities;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class GameRuleMenu extends AbstractGui {
    public GameRuleMenu(GuiType guiType, String title, String name, Player player) {
        super(guiType, title, name, player);
    }

    private GameRule currentGameRule;

    @Override
    public void init() {
        int count = 0;
        System.out.println(getName());
        String[] values = getName().split("\\$");
        Arrays.stream(values).forEach(m -> System.out.println(m));
        World world = Bukkit.getWorld(values[2]);
        for (GameRule gameRule : GameRule.values()) {
            if (gameRule.getType() == Integer.class) {
                Integer value = (Integer) world.getGameRuleValue(gameRule);
                setItem(count, new ItemCreator(Material.PAPER, 1, "§8" + gameRule.getName() + "(§7" + value + "§8)", null).create());
            } else {
                Boolean value = (Boolean) world.getGameRuleValue(gameRule);
                setItem(count, new ItemCreator(value ? Material.EMERALD_BLOCK : Material.REDSTONE_BLOCK, 1, value ? "§8" + gameRule.getName() + "(§atrue§8)" : "§8" + gameRule.getName() + "(§cfalse§8)", null).create());
            }
            count++;
        }
        setItem(SlotPosition.BIG_CHEST_BOTTOM_LEFT.getSlot(), new ItemCreator(GuiHead.LEFT_ARROW.getId(), 1, Messages.GUIS_GAMERULEMENU_BACK, Messages.GUIS_GAMERULEMENU_BACK_LORE).create());
    }

    @Override
    public void onGuiOpen(Player player) {
    }

    @Override
    public void onGuiUse(Player player, ItemStack itemUsed, ClickType clickType) {
        String[] values = getName().split("\\$");
        if (itemUsed.getItemMeta().getDisplayName().equalsIgnoreCase(Messages.GUIS_GAMERULEMENU_BACK)) {
            new MapSettingsMenu(GuiType.SMALL_CHEST, "§8§l" + values[2] + Bukkit.getWorld(values[2]) == null ? "§8(§cnot loaded§8)" : "§8(§aloaded§8)", getName(), player);
            return;
        }
        String itemName = ChatColor.stripColor(itemUsed.getItemMeta().getDisplayName()).replaceAll("\\(.*\\)", "");
        GameRule gameRule = GameRule.getByName(itemName);
        World world = Bukkit.getWorld(values[2]);
        if (gameRule.getType() == Integer.class) {
            setListenForChat(true);
            player.sendMessage(Messages.GUIS_GAMERULEMENU_TYPE_VALUE.replace("%current%", String.valueOf((Integer) world.getGameRuleValue(gameRule))));
            player.closeInventory();
            this.currentGameRule = gameRule;
        } else {
            world.setGameRule(gameRule, !(Boolean) world.getGameRuleValue(gameRule));
            init();
        }
    }

    @Override
    public boolean onPlayerChat(Player player, String message) {
        if (isListenForChat()) {
            String[] values = getName().split("\\$");
            World world = Bukkit.getWorld(values[2]);
            String[] strings = message.split(" ");
            if (strings.length != 1 || !StringUtilities.isStringNumeric(strings[0])) {
                player.sendMessage(Messages.GUIS_GAMERULEMENU_WRONG_VALUE);
                return true;
            }
            reopenGui(player);
            player.sendMessage(Messages.GUIS_GAMERULEMENU_CHANGE_VALUE.replaceAll("%from%", String.valueOf((Integer) world.getGameRuleValue(currentGameRule))).replaceAll("%to%", strings[0]));
            world.setGameRule(currentGameRule, Integer.valueOf(strings[0]));
            setListenForChat(false);
            init();
            return true;
        }
        return false;
    }

}
