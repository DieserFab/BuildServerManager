package de.dieserfab.buildservermanager.commands.cmds;

import de.dieserfab.buildservermanager.commands.AbstractCommand;
import de.dieserfab.buildservermanager.utilities.Messages;
import de.dieserfab.buildservermanager.utilities.Permissions;
import de.dieserfab.buildservermanager.utilities.StringUtilities;
import org.bukkit.Difficulty;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.EnumUtils;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class DifficultyCMD extends AbstractCommand {
    public DifficultyCMD() {
        super("difficulty");
    }

    @Override
    public void handleCommandAsConsole(String[] args) {

    }

    @Override
    public void handleCommandAsPlayer(Player player, String[] args) {
        if (args.length == 1) {
            if (StringUtilities.isStringNumeric(args[0]) ? StringUtilities.isStringBetween(args[0], 0, 3) : EnumUtils.isValidEnum(Difficulty.class, args[0].toUpperCase())) {
                if (!hasPermission(player, Permissions.COMMAND_DIFFICULTY_USE))
                    return;
                Difficulty difficulty = Difficulty.valueOf(args[0].toUpperCase().replace("0", "PEACEFUL").replace("1", "EASY").replace("2", "NORMAL").replace("3", "HARD"));
                player.sendMessage(Messages.COMMANDS_DIFFICULTY_SUCCESS.replace("%world%", player.getWorld().getName()).replace("%difficulty%",difficulty.name()));
                player.getWorld().setDifficulty(difficulty);
                return;
            }
        }
        player.sendMessage(Messages.COMMANDS_DIFFICULTY_FAIL_COMMAND);
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length == 1) {
            return intelligentTabComplete(Arrays.asList("survival", "creative", "adventure", "spectator"), strings[0]);
        }
        return null;
    }
}
