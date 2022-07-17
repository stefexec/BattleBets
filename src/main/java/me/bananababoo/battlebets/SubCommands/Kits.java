package me.bananababoo.battlebets.SubCommands;

import me.bananababoo.battlebets.Arena;
import me.bananababoo.battlebets.TeamM;
import me.bananababoo.battlebets.Utils.StorageUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Kits {
    public static void giveKit(String redKit, String blueKit) {
        if(redKit == null || blueKit == null){
            Bukkit.getLogger().warning("no kit provided");
            return;
        }
        for (Player p : Bukkit.getOnlinePlayers()) {
            if(TeamM.Team(p).equals("red")){
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "cmi kit " + redKit + " " + p.getName());
            }
            else if (TeamM.Team(p).equals("blue")){
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "cmi kit " + blueKit + " " + p.getName());
            }
        }
    }
    public static void setKit(String arena, String team, String kit){
        Arena a = StorageUtil.getArena(arena, team);
        assert a != null;
        Arena b = new Arena(a.getName(), a.getTeam(), a.getX(), a.getY(), a.getZ(), a.getLives(), kit);
        StorageUtil.setArena(b);
    }
}
