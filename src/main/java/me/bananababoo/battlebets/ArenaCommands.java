package me.bananababoo.battlebets;

import me.bananababoo.battlebets.Utils.StorageUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ArenaCommands {

    public static void addArena(Player p, String arenaName) {
        StorageUtil.addArena(arenaName, "red");
        StorageUtil.addArena(arenaName, "blue");

        Component parsed = MiniMessage.miniMessage().deserialize("<gradient:dark_green:blue>Arena Created</gradient>");

        p.sendMessage(parsed);
    }
    public static void setSpawnPosition(String arena,String team, String type, int x, int y, int z){
        if(type.equals("spawn")) {
            Arena a = new Arena(arena,team,x,y,z,StorageUtil.getArena(arena,team).getLives());
            StorageUtil.setArena(a);
            Bukkit.getLogger().info("tried to set spawn of" + a);
        } else if (type.equals("deathspawn")){
            Arena a = StorageUtil.getArena(arena,team);
            a.setDeathX(x);
            a.setDeathY(y);
            a.setDeathZ(z);
            StorageUtil.setArena(a);
        }
    }

}
