package de.dieserfab.buildservermanager.listener;

import de.dieserfab.buildservermanager.BSM;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class ConnectionListener implements Listener {

    private static final String AWARENESS_NOTIFICATION = "§7It might occur that this plugin §acrashes §7your server if it tries to §agenerate §7a world if the server is to slow.\nIf that happens you should try to §aincrease §7the §asettings.timeout §7value from default §a60 §7to a higher number like §a300 §7or §a500§7.\nYou can disable this message in the §aconfig.yml §7file under the section §aawareness_message§7.";

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if(BSM.getInstance().getConfig().getBoolean("awareness_message")){
            player.sendMessage(AWARENESS_NOTIFICATION);
        }
    }

}