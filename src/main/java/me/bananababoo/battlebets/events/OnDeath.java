package me.bananababoo.battlebets.events;

import me.bananababoo.battlebets.Arena;
import me.bananababoo.battlebets.Scoreboard;
import me.bananababoo.battlebets.SubCommands.Lives;
import me.bananababoo.battlebets.SubCommands.StartStop;
import me.bananababoo.battlebets.TeamM;
import me.bananababoo.battlebets.utils.StorageUtil;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.HashMap;
import java.util.logging.Level;

public class OnDeath implements Listener {

    private static final HashMap<Player, Integer> deaths = new HashMap<>();

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player p = e.getPlayer();
        if (StartStop.isBattleRunning()) {
            if (StartStop.getMode().equals("lives")) {
                livesDeath(p);
            } else if (StartStop.getMode().equals("rebirth")) {
                rebirthDeath(p);
            }
        }
    }

    public static void rebirthDeath(Player p){
        deaths.computeIfPresent(p, (k, v) -> v + 1);
        Arena a = StartStop.getArena(TeamM.Team(p));
        Location l = a.getLocation();
        try {
            StorageUtil.LoadFiles();
        } catch (Exception error) {
            Bukkit.getLogger().warning(error.getMessage());
        }
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "spawnpoint " + p.getName() + " " + a.getDeathX() + " " + a.getDeathY() + " " + a.getDeathZ() + " " + a.getYaw() + " " + a.getPitch() + " ");
        p.teleport(l);
        StartStop.rebirthPlayer(p);
        if (StartStop.isBattleRunning()) {
            Scoreboard.updateScoreBoard();
        } else {
            Scoreboard.stopScoreBoard();
        }
    }
    public static void livesDeath(Player p){
        Arena a = StartStop.getArena(TeamM.Team(p));
        Lives.removeLives(TeamM.Team(p));
        Bukkit.getLogger().log(Level.WARNING,  () -> p + ": died points remaining for" + TeamM.Team(p));

        if (Lives.getLivesFromTeam(TeamM.Team(p)) <= 0) {
            p.setGameMode(GameMode.SPECTATOR);
            int aa = 0;  // amount of people on team
            int b = 0;  // amount of people on team with = lives
            for (Player ppl : TeamM.peopleOnTeam(TeamM.Team(p))) {
                aa++;
                if (Lives.getLivesFromTeam(TeamM.Team(ppl)) <= 0) {
                    b++;
                }
            }
            if (aa == b) {
                if (TeamM.Team(p).equals("blue")) {
                    StartStop.stop("red");
                } else {
                    StartStop.stop("blue");
                }
            }
        } else {
            StartStop.giveKit(p);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "spawnpoint " + p.getName() + " " + a.getX() + " " + a.getY() + " " + a.getZ() + " " + a.getYaw() + " " + a.getPitch() + " ");
        }
        p.teleport(a.getLocation());
    }
    public static void resetDeathCounter() {
        deaths.clear();
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (TeamM.onValidTeam(p)) {
                deaths.put(p, 0);
            }
        }
    }

    public static int getDeaths(Player p) {
        return deaths.get(p);
    }
}
