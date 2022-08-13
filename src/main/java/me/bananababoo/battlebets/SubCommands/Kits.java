package me.bananababoo.battlebets.SubCommands;

import me.bananababoo.battlebets.Arena;
import me.bananababoo.battlebets.BattleBets;
import me.bananababoo.battlebets.TeamM;
import me.bananababoo.battlebets.Utils.StorageUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;

public class Kits {
    public static void giveKit(String redKit, String blueKit) {
        if(redKit == null || blueKit == null){
            Bukkit.getLogger().warning("no kit provided");
            return;
        }
        for (Player p : Bukkit.getOnlinePlayers()) {
            if(TeamM.Team(p).equals("red")){
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "cmi kit " + redKit + " " + p.getName());

                // particles
                p.getWorld().spawnParticle(Particle.REDSTONE, p.getLocation(), 25, 1, 2, 1, new Particle.DustOptions(org.bukkit.Color.fromRGB(255, 0, 0), 2));
            }
            else if (TeamM.Team(p).equals("blue")){
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "cmi kit " + blueKit + " " + p.getName());

                // BEEF TEST
                p.getInventory().addItem(new ItemStack(Material.COOKED_BEEF));

                // create new .yml to store kit data
                // IDK if it is even needed if we use CMI for items
                // FileConfiguration bb_kits = YamlConfiguration.loadConfiguration(new File(BattleBets.getDataFolder(), "bb_kits.yml"));

                // ill just add some particles for now lol
                p.getWorld().spawnParticle(Particle.REDSTONE, p.getLocation(), 25, 1, 2, 1, new Particle.DustOptions(org.bukkit.Color.fromRGB(0, 0, 255), 2));
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
