package de.dieserfab.buildservermanager.data;

import de.dieserfab.buildservermanager.BSM;
import de.dieserfab.buildservermanager.utilities.Logger;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;

public class DataManager implements Listener {

    @Getter
    private HashMap<Player, PlayerData> playerDatas;

    public DataManager() {
        Logger.l("iLoading the Data Manager");
        this.playerDatas = new HashMap<>();
        Bukkit.getOnlinePlayers().forEach(player -> addPlayerData(player));
        Bukkit.getPluginManager().registerEvents(this, BSM.getInstance());
        Logger.l("iSuccessfully loaded the Data Manager");
    }

    public void addPlayerData(Player player) {
        this.playerDatas.put(player, new PlayerData());
    }

    public void removePlayerData(Player player) {
        this.playerDatas.remove(player);
    }

    public PlayerData getPlayerData(Player player) {
        return this.playerDatas.get(player);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        addPlayerData(event.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        removePlayerData(event.getPlayer());
    }

}
