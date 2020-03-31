package de.dieserfab.buildservermanager.commands.cmds;

import de.dieserfab.buildservermanager.commands.AbstractCommand;
import de.dieserfab.buildservermanager.utilities.Messages;
import de.dieserfab.buildservermanager.utilities.Permissions;
import de.dieserfab.buildservermanager.utilities.StringUtilities;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.EnumUtils;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class GamemodeCMD extends AbstractCommand {
    public GamemodeCMD() {
        super("gamemode");
    }

    @Override
    public void handleCommandAsConsole(String[] args) {
    }

    @Override
    public void handleCommandAsPlayer(Player player, String[] args) {
        if (args.length == 1) {
            if (StringUtilities.isStringNumeric(args[0]) ? StringUtilities.isStringBetween(args[0], 0, 3) : EnumUtils.isValidEnum(GameMode.class, args[0].toUpperCase())) {
                if (!hasPermission(player, Permissions.COMMAND_GAMEMODE_SELF))
                    return;
                GameMode gamemode = GameMode.valueOf(args[0].toUpperCase().replace("0", "SURVIVAL").replace("1", "CREATIVE").replace("2", "ADVENTURE").replace("3", "SPECTATOR"));
                player.sendMessage(Messages.COMMANDS_GAMEMODE_SUCCESS_SELF.replace("%gamemode%", gamemode.name()));
                player.setGameMode(gamemode);
                return;
            }
        }
        if (args.length == 2) {
            if (StringUtilities.isStringNumeric(args[0]) ? StringUtilities.isStringBetween(args[0], 0, 3) : EnumUtils.isValidEnum(GameMode.class, args[0].toUpperCase())) {
                if (!hasPermission(player, Permissions.COMMAND_GAMEMODE_OTHER))
                    return;
                Player target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    player.sendMessage(Messages.COMMANDS_GAMEMODE_PLAYER_NOT_FOUND);
                    return;
                }
                GameMode gamemode = GameMode.valueOf(args[0].toUpperCase().replace("0", "SURVIVAL").replace("1", "CREATIVE").replace("2", "ADVENTURE").replace("3", "SPECTATOR"));
                player.sendMessage(Messages.COMMANDS_GAMEMODE_SUCCESS_OTHER.replace("%gamemode%", gamemode.name()).replace("%player%", target.getName()));
                target.setGameMode(gamemode);
                return;
            }
        }
        player.sendMessage(Messages.COMMANDS_GAMEMODE_FAIL_COMMAND);
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length == 1) {
            return intelligentTabComplete(Arrays.asList("survival", "creative", "adventure", "spectator"), strings[0]);
        }
        return null;
    }
}
