package me.bananababoo.battlebets.SubCommands;


import me.bananababoo.battlebets.Arena;
import me.bananababoo.battlebets.BattleBets;
import me.bananababoo.battlebets.Events.OnDeath;
import me.bananababoo.battlebets.Scoreboard;
import me.bananababoo.battlebets.TeamM;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class StartStop {
    public static boolean frozen = false;
    private static boolean isBattleRunning;
    private static Arena redArena;
    private static Arena blueArena;
    private static String mode = "";
    private static final List<String> redDeathList = new ArrayList<>();
    private static final List<String> blueDeathList = new ArrayList<>();
    private static Location redSpawn = new Location(Bukkit.getWorld("battlebets"), 0d,150d,0d);
    private static Location blueSpawn = new Location(Bukkit.getWorld("battlebets"), 0d,150d,0d);
    private static boolean cancle = false;

    public static Arena getArena() {
        return redArena;
    }

    public static Arena getArena(String color) {
        if (color.equals("red")) return redArena;
        else if (color.equals("blue")) return blueArena;
        return null;
    }

    public static void resetDeathLists(){
        redDeathList.clear();
        blueDeathList.clear();

    }

    public static void start(Arena red, Arena blue) {
        redArena = red;
        blueArena = blue;
        resetDeathLists();
        if(mode.equals("")){
            mode = "lives";
        }
        setBattleRunning(true);
        Bukkit.getLogger().info("tried to start");
        OnDeath.resetDeathCounter();
        Lives.resetLives(redArena);
        Lives.resetLives(blueArena);
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.getInventory().clear();
            if (TeamM.Team(p).equals("red")) {
                redSpawn = new Location(p.getWorld(), red.getX(), red.getY(), red.getZ());
                p.teleport(redSpawn);
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "spawnpoint " + p.getName() + " " + red.getX() + " " + red.getY() + " " + red.getZ());
            } else if (TeamM.Team(p).equals("blue")) {
                blueSpawn = new Location(p.getWorld(), blue.getX(), blue.getY(), blue.getZ());
                p.teleport(blueSpawn);
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "spawnpoint " + p.getName() + " " + blue.getX() + " " + blue.getY() + " " + blue.getZ());
            }
        }

        new BukkitRunnable() {
            int i = 0;

            @Override
            public void run() {
                if (i == 0) {
                    setFrozen(true);
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        final Component mainTitle = Component.text("You are on " + TeamM.Team(p) + " Team",  TeamM.teamColor(p));
                        final Component subtitle = Component.empty();
                        Title title = Title.title(mainTitle, subtitle);
                        p.showTitle(title);
                    }
                } else if (i == 1) {
                    final Component mainTitle = Component.text("3", NamedTextColor.RED);
                    final Component subtitle = Component.empty();
                    Title title = Title.title(mainTitle, subtitle);
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        p.showTitle(title);
                    }
                } else if (i == 2) {
                    final Component mainTitle = Component.text("2", NamedTextColor.YELLOW);
                    final Component subtitle = Component.empty();
                    Title title = Title.title(mainTitle, subtitle);
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        p.showTitle(title);
                    }

                } else if (i == 3) {
                    final Component mainTitle = Component.text("1", NamedTextColor.GREEN);
                    final Component subtitle = Component.empty();
                    Title title = Title.title(mainTitle, subtitle);
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        p.showTitle(title);

                    }
                } else if (i == 4) {
                    setFrozen(false);
                    final Component mainTitle = Component.text("GO", NamedTextColor.GOLD);
                    final Component subtitle = Component.empty();
                    Title title = Title.title(mainTitle, subtitle);
                    Lives.Init(redArena,blueArena);
                    Scoreboard.startScoreboard();
                    Lives.setLivesFromArena(redArena.getName());
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        p.showTitle(title);
                    }
                    Kits.giveKit(redArena.getKit(), blueArena.getKit());
                }
                if(i < 5) { i++; }


            }
        }.runTaskTimer(BattleBets.getPlugin(), 0L, 20L);

    }


    public static void giveKit(){
             Kits.giveKit(redArena.getKit(), blueArena.getKit());

    }
    public static void giveKit(Player p){
        Kits.giveKit(redArena.getKit(), blueArena.getKit(), p);

    }


    public static void rebirthPlayer(@NotNull Player p){
        cancle = false;
        int num = 0;
        if(TeamM.Team(p).equals("red")){
            if(!redDeathList.contains(p.getName())){
                redDeathList.add(p.getName());
            }
        } else if (TeamM.Team(p).equals("blue")) {
            if (!blueDeathList.contains(p.getName())) {
                blueDeathList.add(p.getName());
            }
        }
        Bukkit.getLogger().info(redDeathList + " " + blueDeathList);
        for(Player ppl : Bukkit.getOnlinePlayers()){
            if(TeamM.Team(ppl).equals(TeamM.Team(p))) num++;
        }
        if(redDeathList.size() == num){      // if number of people on a team == number of dead people on same team then other team wins
            StartStop.stop("blue");
            Bukkit.getLogger().info("blue team won");
        }else if(blueDeathList.size() == num){
            StartStop.stop("red");
            Bukkit.getLogger().info("red team won");
        }else{
            Arena a = StartStop.getArena();
            Location l = new Location(p.getWorld(), a.getDeathX(), a.getDeathY(), a.getDeathZ());
            if(TeamM.Team(p).equals("red")){
                p.teleport(redSpawn);
            }else if (TeamM.Team(p).equals("red")){
                p.teleport(blueSpawn);
            }
            p.getInventory().clear();
            p.setInvulnerable(true);
            p.setGameMode(GameMode.SPECTATOR);
            BukkitTask task = new BukkitRunnable(){
                int i = 0;
                final int deaths = OnDeath.getDeaths(p);
                final double time = (.6*(deaths*deaths))+(3*deaths)-2.6;

                @Override
                public void run() {
                    if(cancle){
                        cancel();
                    }
                    p.sendActionBar(Component.text("Time until revived: " + Math.round(time - i)));
                    if((time-i) <= 0){
                        if(TeamM.Team(p).equals("red")){
                            redDeathList.remove(p.getName());
                            p.teleport(redSpawn);
                            p.getInventory().clear();
                        }else if(TeamM.Team(p).equals("blue")){
                            blueDeathList.remove(p.getName());
                            p.teleport(blueSpawn);
                            p.getInventory().clear();
                        }
                        Scoreboard.updateScoreBoard();
                        giveKit();
                        p.setInvulnerable(false);

                        cancel();
                    }
                    i++;
                }
            }.runTaskTimer(BattleBets.getPlugin(),0,20);
        }
    }
    public static boolean isPlayerDead(Player player) {
        if (TeamM.Team(player).equals("blue"))
            return blueDeathList.contains(player.getName());
        else if(TeamM.Team(player).equals("red")){
            return redDeathList.contains(player.getName());
        }
       return false;
    }

    public static void stop(String winner){
        Bukkit.getLogger().info("tried to stop");
        setBattleRunning(false);
        resetDeathLists();
        for(Player p : Bukkit.getOnlinePlayers()){
            p.getInventory().clear();
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "spawnpoint " + p.getName() + " 0 150 0");
            //p.setHealth(0);    todo check if this needs to exist
            Location location = new Location(p.getWorld(),0,150,0);
            p.teleport(location);
        }
        if(winner.equals("blue")){
            for(Player p : Bukkit.getOnlinePlayers()){
                Component mainTitle = Component.text("Blue Team Wins", NamedTextColor.BLUE);
                Component subtitle = Component.text("");
                final Title title = Title.title(mainTitle,subtitle);
                p.showTitle(title);
            }
        }
        else if(winner.equals("red")){
            for(Player p : Bukkit.getOnlinePlayers()){
                Component mainTitle = Component.text("Red Team Wins", NamedTextColor.RED);
                Component subtitle = Component.text("");
                final Title title = Title.title(mainTitle,subtitle);
                p.showTitle(title);
            }
        }else{
            for(Player p: Bukkit.getOnlinePlayers()){
                Component mainTitle = Component.text(winner,NamedTextColor.GRAY);
                Component subtitle = Component.text("");
                final Title title = Title.title(mainTitle,subtitle);
                p.showTitle(title);
            }
        }
        Scoreboard.stopScoreBoard();
        OnDeath.resetDeathCounter();
        cancle = true;

    }

    public static boolean isBattleRunning() {
        return isBattleRunning;
    }

    public static void setBattleRunning(boolean battleRunning) {
        isBattleRunning = battleRunning;
    }

    public static boolean isFrozen() {
        return frozen;
    }

    public static void setFrozen(boolean froze) {
        frozen = froze;

    }

    public static String getMode() {
        return mode;
    }

    public static void setMode(String mode) {
        StartStop.mode = mode;
    }
}
