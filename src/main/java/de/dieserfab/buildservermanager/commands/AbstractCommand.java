package de.dieserfab.buildservermanager.commands;

import de.dieserfab.buildservermanager.BSM;
import de.dieserfab.buildservermanager.utilities.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public abstract class AbstractCommand implements CommandExecutor, TabCompleter {

    public String name;

    private static final String NO_PERMISSION = "§cYou dont have permission to use that command.";

    public AbstractCommand(String name) {
        this.name = name;
        BSM.getInstance().getCommand(name).setExecutor(this);
        BSM.getInstance().getCommand(name).setTabCompleter(this);
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