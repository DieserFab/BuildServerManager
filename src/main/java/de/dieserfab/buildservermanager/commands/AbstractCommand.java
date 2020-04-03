package de.dieserfab.buildservermanager.commands;

import de.dieserfab.buildservermanager.BSM;
import de.dieserfab.buildservermanager.utilities.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractCommand implements CommandExecutor, TabCompleter {

    public String name;

    public AbstractCommand(String name) {
        this.name = name;
        BSM.getInstance().getCommand(name).setExecutor(this);
        BSM.getInstance().getCommand(name).setTabCompleter(this);
    }

    public List<String> intelligentTabComplete(List<String> possibillities, String current) {
        if(current.isEmpty()||possibillities.isEmpty())
            return possibillities;
        List<String> out = new ArrayList<>();
        for (String string : possibillities) {
            String var1 = string.toLowerCase();
            String var2 = current.toLowerCase();
            if(var1.contains(var2))
                out.add(string);
        }
        return out;
    }

    public boolean onCommand(CommandSender sender, Command command, String name, String[] args) {
        if (sender instanceof Player) {
            handleCommandAsPlayer((Player) sender, args);
        } else {
            handleCommandAsConsole(args);
        }
        return false;
    }

    public boolean hasPermission(Player player, String permission) {
        if (player.hasPermission(permission)) {
            return true;
        }
        player.sendMessage(Messages.COMMANDS_NO_PERMISSION);
        return false;
    }

    public abstract void handleCommandAsConsole(String[] args);

    public abstract void handleCommandAsPlayer(Player player, String[] args);
}