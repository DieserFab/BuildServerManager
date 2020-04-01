package de.dieserfab.buildservermanager.commands.cmds;

import de.dieserfab.buildservermanager.commands.AbstractCommand;
import de.dieserfab.buildservermanager.utilities.Messages;
import de.dieserfab.buildservermanager.utilities.Permissions;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class NightvisionCMD extends AbstractCommand {
    public NightvisionCMD() {
        super("nightvision");
    }

    @Override
    public void handleCommandAsConsole(String[] args) {

    }

    @Override
    public void handleCommandAsPlayer(Player player, String[] args) {
        if (args.length == 0) {
            if (!hasPermission(player, Permissions.COMMAND_NIGHTVISION_SELF))
                return;
            if (player.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
                player.removePotionEffect(PotionEffectType.NIGHT_VISION);
            } else {
                player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0, false, false));
            }
            player.sendMessage(Messages.COMMANDS_NIGHTVISION_SELF);
            return;
        }
        if (args.length == 1) {
            if (!hasPermission(player, Permissions.COMMAND_NIGHTVISION_OTHER))
                return;
            Player target = Bukkit.getPlayer(args[0]);
            if (target != null) {
                if (target.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
                    target.removePotionEffect(PotionEffectType.NIGHT_VISION);
                } else {
                    target.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0, false, false));
                }
                player.sendMessage(Messages.COMMANDS_NIGHTVISION_OTHER.replace("%player%", target.getName()));
                return;
            }
            player.sendMessage(Messages.COMMANDS_NIGHTVISION_PLAYER_NOT_ONLINE);
            return;
        }
        player.sendMessage(Messages.COMMANDS_NIGHTVISION_FAIL_COMMAND);
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return null;
    }
}
