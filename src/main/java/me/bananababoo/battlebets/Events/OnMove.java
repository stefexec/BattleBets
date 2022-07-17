package me.bananababoo.battlebets.Events;

import me.bananababoo.battlebets.SubCommands.StartStop;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class OnMove implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        if(StartStop.isFrozen()) {
            e.setTo(e.getFrom());
        }
    }
}
