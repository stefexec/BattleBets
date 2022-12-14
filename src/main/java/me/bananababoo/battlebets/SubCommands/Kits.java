package me.bananababoo.battlebets.SubCommands;

import com.Zrips.CMI.CMI;
import com.Zrips.CMI.Modules.Kits.Kit;
import me.bananababoo.battlebets.Arena;
import me.bananababoo.battlebets.TeamM;
import me.bananababoo.battlebets.utils.StorageUtil;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

public class Kits {
    public static void giveKit(String redKit, String blueKit) {
        CMI cmi = CMI.getInstance();
        if(redKit == null || blueKit == null){
            Bukkit.getLogger().warning("no kit provided");
            return;
        }
        for (Player p : Bukkit.getOnlinePlayers()) {
            if(TeamM.Team(p).equals("red")){

                p.getInventory().clear();

                //Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "cmi kit " + redKit + " " + p.getName());
                CMI.getInstance().getKitsManager().giveKit(p, cmi.getKitsManager().getKitMap().get(redKit));


                // spawn particles
                p.getWorld().spawnParticle(Particle.REDSTONE, p.getLocation(), 25, 1, 2, 1, new Particle.DustOptions(org.bukkit.Color.fromRGB(255, 0, 0), 2));
            }
            else if (TeamM.Team(p).equals("blue")){

                p.getInventory().clear();

                // give armour with cmi bc its easy
//                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "cmi kit " + blueKit + " " + p.getName());
                CMI.getInstance().getKitsManager().giveKit(p, cmi.getKitsManager().getKitMap().get(blueKit));

                // spawn particles
                p.getWorld().spawnParticle(Particle.REDSTONE, p.getLocation(), 25, 1, 2, 1, new Particle.DustOptions(org.bukkit.Color.fromRGB(0, 0, 255), 2));
            }
        }
    }

    public static void giveKit(String redKit, String blueKit, Player pl) {
        if(redKit == null || blueKit == null){
            Bukkit.getLogger().warning("no kit provided");
            return;
        }
        if(TeamM.Team(pl).equals("red")){
            Bukkit.getLogger().info("");

            pl.getInventory().clear();

            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "cmi kit " + redKit + " " + pl.getName());

            // spawn particles
            pl.getWorld().spawnParticle(Particle.REDSTONE, pl.getLocation(), 25, 1, 2, 1, new Particle.DustOptions(org.bukkit.Color.fromRGB(255, 0, 0), 2));
        }
        else if (TeamM.Team(pl).equals("blue")){

            pl.getInventory().clear();

            // give armour with cmi bc its easy
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "cmi kit " + blueKit + " " + pl.getName());

            // spawn particles
            pl.getWorld().spawnParticle(Particle.REDSTONE, pl.getLocation(), 25, 1, 2, 1, new Particle.DustOptions(org.bukkit.Color.fromRGB(0, 0, 255), 2));
        }
    }


    public static void setKit(String arena, String team, String kit){
        Arena a = StorageUtil.getArena(arena, team);
        assert a != null;
        Arena b = new Arena(a.getName(), a.getTeam(), a.getLives(), kit, a.getLocation());
        StorageUtil.setArena(b);
    }
}
