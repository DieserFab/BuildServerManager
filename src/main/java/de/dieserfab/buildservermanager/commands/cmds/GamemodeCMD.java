package de.dieserfab.buildservermanager.commands.cmds;

import de.dieserfab.buildservermanager.commands.AbstractCommand;
import de.dieserfab.buildservermanager.utilities.CommandUtilities;
import de.dieserfab.buildservermanager.utilities.Messages;
import de.dieserfab.buildservermanager.utilities.Permissions;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.EnumUtils;
import org.bukkit.entity.Player;

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
            if (CommandUtilities.isStringNumeric(args[0]) ? CommandUtilities.isStringBetween(args[0], 0, 3) : EnumUtils.isValidEnum(GameMode.class, args[0].toUpperCase())) {
                if (!hasPermission(player, Permissions.COMMAND_GAMEMODE))
                    return;
                GameMode gamemode = GameMode.valueOf(args[0].replace("0", "SURVIVAL").replace("1", "CREATIVE").replace("2", "ADVENTURE").replace("3", "SPECTATOR"));
                player.sendMessage(Messages.COMMANDS_GAMEMODE_SUCCESS.replace("%gamemode%",gamemode.name()));
                player.setGameMode(gamemode);
                return;
            }
        }
        player.sendMessage(Messages.COMMANDS_GAMEMODE_FAIL_COMMAND);
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return null;
    }
}
