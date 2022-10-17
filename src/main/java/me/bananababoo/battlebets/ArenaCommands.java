package me.bananababoo.battlebets;

import me.bananababoo.battlebets.utils.StorageUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class ArenaCommands {
    private ArenaCommands() {
        throw new IllegalStateException("Utility class");
    }

    public static void addArena(String arenaName) {
        StorageUtil.addArena(arenaName, "red");
        StorageUtil.addArena(arenaName, "blue");

    }

    public static void addItem(String itemName) {
        StorageUtil.addArena(itemName, "red");
        StorageUtil.addArena(itemName, "blue");

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
