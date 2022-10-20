package me.bananababoo.battlebets.events;

import me.bananababoo.battlebets.BattleBets;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.persistence.PersistentDataType;

public class OnJoin implements Listener {
    //when a player joins make sure they have all needed data
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if(!p.getPersistentDataContainer().has(new NamespacedKey(BattleBets.getPlugin(), "team"), PersistentDataType.STRING)) {
            p.getPersistentDataContainer().set(new NamespacedKey(BattleBets.getPlugin(), "team"), PersistentDataType.STRING, "none");
        }
        //p.setPlayerListName(GTC.getTeamColor(e.getPlayer()) + p.getDisplayName());  TODO: set team color on join
    }

}
