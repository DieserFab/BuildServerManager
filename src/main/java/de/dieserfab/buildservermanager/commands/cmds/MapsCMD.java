package de.dieserfab.buildservermanager.commands.cmds;

import de.dieserfab.buildservermanager.BSM;
import de.dieserfab.buildservermanager.api.BSMAPI;
import de.dieserfab.buildservermanager.commands.AbstractCommand;
import de.dieserfab.buildservermanager.gui.AbstractGui;
import de.dieserfab.buildservermanager.gui.menu.MainMenu;
import de.dieserfab.buildservermanager.utilities.Messages;
import de.dieserfab.buildservermanager.utilities.Permissions;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MapsCMD extends AbstractCommand {
    public MapsCMD() {
        super("maps");
    }

    @Override
    public void handleCommandAsConsole(String[] args) {

    }

    @Override
    public void handleCommandAsPlayer(Player player, String[] args) {
        if (args.length == 0) {
            player.sendMessage("§8§m--------------------");
            player.sendMessage("§7/maps §aopen ");
            player.sendMessage("§7/maps §aaddDomain/removeDomain §7<name> ");
            player.sendMessage("§7/maps §aaddCategory/removeCategory §7<domain> <name>");
            player.sendMessage("§7/maps §aremoveMap §7<domain> <category> <name>");
            player.sendMessage("§7/maps §aaddMap §7<domain> <category> <name> <void/nether/end/normal>");
            player.sendMessage("§8§m--------------------");
        }
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("open")) {
                if (hasPermission(player, Permissions.COMMAND_MAPS_OPEN))
                    new MainMenu(AbstractGui.GuiType.SMALL_CHEST, Messages.GUIS_MAINMENU_TITLE, player.getName().toLowerCase() + "_mainmenu", player);
                return;
            }
            player.sendMessage(Messages.COMMANDS_FAIL_COMMAND);
            return;
        }
        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("tp")) {
                if (hasPermission(player, Permissions.COMMAND_MAPS_TP))
                    try {
                        World world = Bukkit.getWorld(args[1]);
                        player.teleport(world.getSpawnLocation());
                        player.sendMessage(Messages.COMMANDS_TP_SUCCESS.replace("%world%", world.getName()));
                    } catch (Exception e) {
                        player.sendMessage(Messages.COMMANDS_TP_FAIL);
                    }
                return;
            }
            if (args[0].equalsIgnoreCase("addDomain")) {
                if (hasPermission(player, Permissions.COMMAND_MAPS_ADDDOMAIN))
                    player.sendMessage(BSMAPI.getInstance().addDomain(args[1]) ? Messages.COMMANDS_ADDDOMAIN_SUCCESS.replace("%domain%", args[1]) : Messages.COMMANDS_ADDDOMAIN_FAIL.replace("%domain%", args[1]));
                return;
            }
            if (args[0].equalsIgnoreCase("removeDomain")) {
                if (hasPermission(player, Permissions.COMMAND_MAPS_REMOVEDOMAIN))
                    player.sendMessage(BSMAPI.getInstance().removeDomain(args[1]) ? Messages.COMMANDS_REMOVEDOMAIN_SUCCESS.replace("%domain%", args[1]) : Messages.COMMANDS_REMOVEDOMAIN_FAIL.replace("%domain%", args[1]));
                return;
            }
            player.sendMessage(Messages.COMMANDS_FAIL_COMMAND);
            return;
        }
        if (args.length == 3) {
            if (args[0].equalsIgnoreCase("addCategory")) {
                if (hasPermission(player, Permissions.COMMAND_MAPS_ADDCATEGORY))
                    player.sendMessage(BSMAPI.getInstance().addCategory(args[1], args[2]) ? Messages.COMMANDS_ADDCATEGORY_SUCCESS.replace("%domain%", args[1]).replace("%category%", args[2]) : Messages.COMMANDS_ADDCATEGORY_FAIL.replace("%domain%", args[1]).replace("%category%", args[2]));
                return;
            }
            if (args[0].equalsIgnoreCase("removeCategory")) {
                if (hasPermission(player, Permissions.COMMAND_MAPS_REMOVECATEGORY))
                    player.sendMessage(BSMAPI.getInstance().removeCategory(args[1], args[2]) ? Messages.COMMANDS_REMOVECATEGORY_SUCCESS.replace("%domain%", args[1]).replace("%category%", args[2]) : Messages.COMMANDS_REMOVECATEGORY_FAIL.replace("%domain%", args[1]).replace("%category%", args[2]));
                return;
            }
            player.sendMessage(Messages.COMMANDS_FAIL_COMMAND);
            return;
        }
        if (args.length == 4) {
            if (args[0].equalsIgnoreCase("removeMap")) {
                if (hasPermission(player, Permissions.COMMAND_MAPS_REMOVEMAP))
                    player.sendMessage(BSMAPI.getInstance().removeMap(args[1], args[2], args[3]) ? Messages.COMMANDS_REMOVEMAP_SUCCESS.replace("%domain%", args[1]).replace("%category%", args[2]).replace("%map%", args[3]) : Messages.COMMANDS_REMOVEMAP_FAIL.replace("%domain%", args[1]).replace("%category%", args[2]).replace("%map%", args[3]));
                return;
            }
            player.sendMessage(Messages.COMMANDS_FAIL_COMMAND);
            return;
        }
        if (args.length == 5) {
            if (args[0].equalsIgnoreCase("addMap")) {
                if (hasPermission(player, Permissions.COMMAND_MAPS_ADDMAP))
                    player.sendMessage(BSMAPI.getInstance().addMap(args[1], args[2], args[3], args[4]) ? Messages.COMMANDS_ADDMAP_SUCCESS.replace("%domain%", args[1]).replace("%category%", args[2]).replace("%map%", args[3]).replace("%type%", args[4].toLowerCase()) : Messages.COMMANDS_ADDMAP_FAIL.replace("%domain%", args[1]).replace("%category%", args[2]).replace("%map%", args[3]).replace("%type%", args[4].toLowerCase()));
                return;
            }
            player.sendMessage(Messages.COMMANDS_FAIL_COMMAND);
            return;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player = (Player) commandSender;
        if (strings.length == 1) {
            return intelligentTabComplete(Arrays.asList("open", "tp", "addDomain", "removeDomain", "addCategory", "removeCategory", "addMap", "removeMap"), strings[0]);
        }
        if (strings.length == 2) {
            if (strings[0].equalsIgnoreCase("tp")) {
                List<String> worlds = new ArrayList<>();
                Bukkit.getWorlds().forEach(world -> worlds.add(world.getName()));
                return intelligentTabComplete(worlds,strings[1]);
            }
            if(strings[0].equalsIgnoreCase("addCategory")||strings[0].equalsIgnoreCase("addMap")||strings[0].equalsIgnoreCase("removeDomain")||strings[0].equalsIgnoreCase("removeCategory")||strings[0].equalsIgnoreCase("removeMap")){
                List<String> domains = new ArrayList<>();
                BSMAPI.getInstance().getDomains().forEach(domain -> domains.add(domain.getName()));
                return intelligentTabComplete(domains,strings[1]);
            }
        }
        if(strings.length == 3){
            if(strings[0].equalsIgnoreCase("addMap")||strings[0].equalsIgnoreCase("removeMap")||strings[0].equalsIgnoreCase("removeCategory")){
                List<String> categories = new ArrayList<>();
                BSMAPI.getInstance().getCategories(strings[1]).forEach(category -> categories.add(category.getName()));
                return intelligentTabComplete(categories,strings[2]);
            }
        }
        if(strings.length == 4){
            if(strings[0].equalsIgnoreCase("removeMap")){
                List<String> maps = new ArrayList<>();
                BSMAPI.getInstance().getMaps(strings[1],strings[2]).forEach(map -> maps.add(map.getName()));
                return intelligentTabComplete(maps,strings[3]);
            }
        }
        if (strings.length == 5) {
            if (strings[0].equalsIgnoreCase("addMap")) {
                return intelligentTabComplete(Arrays.asList("normal", "nether", "end", "void","flat"),strings[4]);
            }
        }
        return Arrays.asList("");
    }
}
