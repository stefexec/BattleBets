package me.bananababoo.battlebets;

import me.bananababoo.battlebets.Utils.StorageUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class ArenaCommands {

    public static void addArena(Player p, String arenaName) {
        StorageUtil.addArena(arenaName, "red");
        StorageUtil.addArena(arenaName, "blue");

    }
    public static void setSpawnPosition(Arena arena, String type, Location l){
        if(type.equals("spawn")) {
            arena.setLocation(l);
            StorageUtil.setArena(arena);
            Bukkit.getLogger().info("tried to set spawn of" + arena);
        } else if (type.equals("deathspawn")){
            arena.setDeathX(l.getBlockX());
            arena.setDeathY(l.getBlockY());
            arena.setDeathZ(l.getBlockZ());
            StorageUtil.setArena(arena);
        }
    }

}
