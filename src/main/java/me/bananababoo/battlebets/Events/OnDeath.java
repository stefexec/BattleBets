package me.bananababoo.battlebets.Events;

import me.bananababoo.battlebets.Arena;
import me.bananababoo.battlebets.Scoreboard;
import me.bananababoo.battlebets.SubCommands.Lives;
import me.bananababoo.battlebets.SubCommands.StartStop;
import me.bananababoo.battlebets.TeamM;
import me.bananababoo.battlebets.Utils.StorageUtil;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.HashMap;
import java.util.Objects;

public class OnDeath implements Listener {

    private static final HashMap<Player, Integer> deaths = new HashMap<>();

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player p = e.getPlayer();
        e.getPlayer().setHealth(Objects.requireNonNull(e.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue());
        if (StartStop.isBattleRunning()) {
            if (StartStop.getMode().equals("lives")) {
                p.getInventory().clear();
                Arena a = StartStop.getArena();
                Lives.removeLives(TeamM.Team(e.getPlayer()));
                Scoreboard.updateScoreBoard();
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
                    StartStop.giveKit();
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "spawnpoint " + e.getPlayer().getName() + " " + a.getDeathX() + " " + a.getDeathY() + " " + a.getDeathZ());
                }
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
