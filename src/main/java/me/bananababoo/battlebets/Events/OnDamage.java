package me.bananababoo.battlebets.Events;

import me.bananababoo.battlebets.TeamM;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class OnDamage implements Listener {

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e){
        if(e.getEntityType().equals(EntityType.PLAYER)){
            Player damager = (Player) e.getDamager();  // does damage
            Player damagee = (Player) e.getEntity();   // receives damage
            if(TeamM.Team(damagee).equals(TeamM.Team(damager))){
                e.setCancelled(true);
            }
        }
    }

}
