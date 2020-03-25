package de.dieserfab.buildservermanager.commands.cmds;

import de.dieserfab.buildservermanager.BSM;
import de.dieserfab.buildservermanager.api.BSMAPI;
import de.dieserfab.buildservermanager.commands.AbstractCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MapsCMD extends AbstractCommand {
    public MapsCMD() {
        super("maps");
    }

    private static final String FAIL_COMMAND = "§7Wrong command usage try §a/maps §7to get an overview of all commands.";
    private static final String SUCCESS_ADDDOMAIN = "§7You successfully added the domain §a%domain%§7.";
    private static final String FAIL_ADDDOMAIN = "§7Unable to add the domain §a%domain%§7.";
    private static final String SUCCESS_REMOVEDOMAIN = "§7You successfully deleted the domain §a%domain%§7.";
    private static final String FAIL_REMOVEDOMAIN = "§7Unable to delete the domain §a%domain%§7.";
    private static final String SUCCESS_ADDCATEGORY = "§7You successfully added the category §a%category% §7to the domain §a%domain%§7.";
    private static final String FAIL_ADDCATEGORY = "§7Unable to add the category §a%category% §7to the domain §a%domain%§7.";
    private static final String SUCCESS_REMOVECATEGORY = "§7You successfully removed the category §a%category% §7from the domain §a%domain%§7.";
    private static final String FAIL_REMOVECATEGORY = "§7Unable to remove the category §a%category% §7from the domain §a%domain%§7.";
    private static final String SUCCESS_ADDMAP = "§7You successfully added the map §a%map% §7to the category §a%category% §7from the domain §a%domain% §7with the generator §a%type%§7.";
    private static final String FAIL_ADDMAP = "§7Unable to add the map §a%map% §7to the category §a%category% §7from the domain §a%domain% §7with the generator §a%type%§7.";
    private static final String SUCCESS_REMOVEMAP = "§7You successfully removed the map §a%map% §7from the category §a%category% §7of the domain §a%domain%§7.";
    private static final String FAIL_REMOVEMAP = "§7Unable to removed the map §a%map% §7from the category §a%category% §7of the domain §a%domain%§7.";

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
                BSM.getInstance().getGuiManager().getGui("mainmenu").openGui(player);
                return;
            }
            player.sendMessage(FAIL_COMMAND);
            return;
        }
        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("tp")) {
                try {
                    player.teleport(Bukkit.getWorld(args[1]).getSpawnLocation());
                } catch (Exception e) {
                    player.sendMessage("§7Error while teleporting:" + e.getMessage());
                }
                return;
            }
            if (args[0].equalsIgnoreCase("addDomain")) {
                player.sendMessage(BSMAPI.getInstance().addDomain(args[1]) ? SUCCESS_ADDDOMAIN.replace("%domain%", args[1]) : FAIL_ADDDOMAIN.replace("%domain%", args[1]));
                return;
            }
            if (args[0].equalsIgnoreCase("removeDomain")) {
                player.sendMessage(BSMAPI.getInstance().removeDomain(args[1]) ? SUCCESS_REMOVEDOMAIN.replace("%domain%", args[1]) : FAIL_REMOVEDOMAIN.replace("%domain%", args[1]));
                return;
            }
            player.sendMessage(FAIL_COMMAND);
            return;
        }
        if (args.length == 3) {
            if (args[0].equalsIgnoreCase("addCategory")) {
                player.sendMessage(BSMAPI.getInstance().addCategory(args[1], args[2]) ? SUCCESS_ADDCATEGORY.replace("%domain%", args[1]).replace("%category%", args[2]) : FAIL_ADDCATEGORY.replace("%domain%", args[1]).replace("%category%", args[2]));
                return;
            }
            if (args[0].equalsIgnoreCase("removeCategory")) {
                player.sendMessage(BSMAPI.getInstance().removeCategory(args[1], args[2]) ? SUCCESS_REMOVECATEGORY.replace("%domain%", args[1]).replace("%category%", args[2]) : FAIL_REMOVECATEGORY.replace("%domain%", args[1]).replace("%category%", args[2]));
                return;
            }
            player.sendMessage(FAIL_COMMAND);
            return;
        }
        if (args.length == 4) {
            if (args[0].equalsIgnoreCase("removeMap")) {
                player.sendMessage(BSMAPI.getInstance().removeMap(args[1], args[2], args[3]) ? SUCCESS_REMOVEMAP.replace("%domain%", args[1]).replace("%category%", args[2]).replace("%map%", args[3]) : FAIL_REMOVEMAP.replace("%domain%", args[1]).replace("%category%", args[2]).replace("%map%", args[3]));
                return;
            }
            player.sendMessage(FAIL_COMMAND);
            return;
        }
        if (args.length == 5) {
            if (args[0].equalsIgnoreCase("addMap")) {
                player.sendMessage(BSMAPI.getInstance().addMap(args[1], args[2], args[3], args[4]) ? SUCCESS_ADDMAP.replace("%domain%", args[1]).replace("%category%", args[2]).replace("%map%", args[3]).replace("%type%", args[4].toLowerCase()) : FAIL_ADDMAP.replace("%domain%", args[1]).replace("%category%", args[2]).replace("%map%", args[3]).replace("%type%", args[4].toLowerCase()));
                return;
            }
            player.sendMessage(FAIL_COMMAND);
            return;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player = (Player) commandSender;
        if (strings.length == 1) {
            return Arrays.asList("open", "tp", "addDomain", "removeDomain", "addCategory", "removeCategory", "addMap", "removeMap");
        }
        if (strings.length == 2) {
            if(strings[0].equalsIgnoreCase("tp")){
                List<String> worlds = new ArrayList<>();
                Bukkit.getWorlds().forEach(world -> worlds.add(world.getName()));
                return worlds;
            }
        }
        if(strings.length == 5){
            if(strings[0].equalsIgnoreCase("addMap")){
                return Arrays.asList("normal","nether","end","void");
            }
        }
        return Arrays.asList(" ");
    }
}
