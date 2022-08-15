package me.bananababoo.battlebets.Events;

import com.google.gson.Gson;
import me.bananababoo.battlebets.Arena;
import me.bananababoo.battlebets.Scoreboard;
import me.bananababoo.battlebets.SubCommands.Lives;
import me.bananababoo.battlebets.SubCommands.StartStop;
import me.bananababoo.battlebets.TeamM;
import me.bananababoo.battlebets.Utils.StorageUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.HashMap;

public class OnDeath implements Listener {

    private static final HashMap<Player, Integer> deaths = new HashMap<>();

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Bukkit.getServer().broadcast(Component.text("SOMEONE DIED AAAAAAA " + e.getPlayer().getName()));
        Player p = e.getPlayer();
        if (StartStop.isBattleRunning()) {
//            e.getPlayer().setHealth(Objects.requireNonNull(e.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue());
            if (StartStop.getMode().equals("lives")) {
                Arena a = StartStop.getArena();
                Lives.removeLives(TeamM.Team(e.getPlayer()));
                Bukkit.getLogger().warning(e.getPlayer() + ": died points remaining for " + TeamM.Team(p) + StartStop.getArena(TeamM.Team(p)).getLives());
                Bukkit.getLogger().info(e.getPlayer() + "died and battlebets tried to increment counter");

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
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "spawnpoint " + e.getPlayer().getName() + " " + a.getX() + " " + a.getY() + " " + a.getZ());
                }
                Location l = new Location(e.getPlayer().getWorld(), a.getX(), a.getY(), a.getZ());
                e.getPlayer().teleport(l);
            } else if (StartStop.getMode().equals("rebirth")) {
                deaths.computeIfPresent(e.getPlayer(), (k, v) -> v + 1);
                Arena a = StartStop.getArena();
                Location l = new Location(e.getPlayer().getWorld(), a.getDeathX(), a.getDeathY(), a.getDeathZ());
                try {
                    StorageUtil.getArena();
                } catch (Exception error) {
                    Bukkit.getLogger().warning(error.getMessage());
                }
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "spawnpoint " + e.getPlayer().getName() + " " + a.getDeathX() + " " + a.getDeathY() + " " + a.getDeathZ());
                e.getPlayer().teleport(l);
                StartStop.rebirthPlayer(e.getPlayer());
                if (StartStop.isBattleRunning()) {
                    Scoreboard.updateScoreBoard();
                } else {
                    Scoreboard.stopScoreBoard();
                }
            }
        }
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

    public static int getTeamDeaths(String team) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (TeamM.Team(p).equals(team)) {
                return getDeaths(p);
            }
        }
        return 0;
    }
}

//    1:hi
//    2:by
//    3:hello
//
//
//
//
//t
