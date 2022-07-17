package me.bananababoo.battlebets;

import me.bananababoo.battlebets.SubCommands.Lives;
import me.bananababoo.battlebets.SubCommands.StartStop;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.Objects;

public class Scoreboard {
    public
        static Objective battle;
        static Score s5;
        static Score s4;
        static Score s3 = null;
        static Score s2;
        static Score s1;
        static Score s0;

    private
        static String alive = (ChatColor.GREEN + "✔" + ChatColor.RESET);
        static String dead = (ChatColor.RED + "✖" + ChatColor.RESET);


    public static void startScoreboard() {
        stopScoreBoard();
        Bukkit.getLogger().info("tried to start scoreboard");
        ScoreboardManager sbm = Bukkit.getScoreboardManager();
        var board = sbm.getNewScoreboard();  //todo something around here is buggy maybe? but only on rebirth are we not correctly closing other one we shold be closing right
        battle = board.registerNewObjective("battle", "dummy", Component.text("     BattleBets    ").color(NamedTextColor.GOLD).decorate(TextDecoration.BOLD));
        battle.setDisplaySlot(DisplaySlot.SIDEBAR);
        updateScoreBoard();
        for(Player p : Bukkit.getOnlinePlayers()){
            p.setScoreboard(board);
        }
    }
    public static void stopScoreBoard(){
        try {
            battle.unregister();
        }catch(Exception e){
            Bukkit.getLogger().warning(e.getMessage());

        }
    }

    public static void updateScoreBoard(){
        for(Player p : Bukkit.getOnlinePlayers()) {
            try {
                s2.resetScore();
            }catch(Exception e){
                Bukkit.getLogger().warning(e.getMessage());
                Bukkit.getLogger().info(ChatColor.RED + "Lives: " + ChatColor.WHITE + ChatColor.BOLD + Lives.getLivesFromPlayer(p));
            }

            s5 = battle.getScore(ChatColor.GOLD + "Arena: " + ChatColor.WHITE + ChatColor.BOLD + StartStop.getArena().getName());
            s4 = battle.getScore(ChatColor.GOLD + "Team: " + TeamM.teamChatColor(p) + ChatColor.BOLD + Extra.capitalize(TeamM.Team(p)));
            s3 = battle.getScore(ChatColor.GOLD + "Mode: " + ChatColor.WHITE + ChatColor.BOLD + StartStop.getMode().substring(0, 1).toUpperCase() + StartStop.getMode().substring(1));
            if (StartStop.getMode().equals("lives")){
                if (TeamM.Team(p).equals("red") || TeamM.Team(p).equals("blue")) {
                    s2 = battle.getScore(ChatColor.RED + "Lives: " + ChatColor.WHITE + ChatColor.BOLD + Lives.getLivesFromPlayer(p));
                } else {
                    s2 = battle.getScore("");
                }
                s1 = battle.getScore("");
            }else if(StartStop.getMode().equals("rebirth")){
                StringBuilder red = new StringBuilder();
                StringBuilder blue = new StringBuilder();
                for(Player ppl : TeamM.peopleOnTeam("blue")){
                    try {
                        s2.resetScore();
                    }catch(Exception e){
                        Bukkit.getLogger().warning(e.getMessage());
                        Bukkit.getLogger().info(ChatColor.RED + "Lives: " + ChatColor.WHITE + ChatColor.BOLD + Lives.getLivesFromPlayer(p));
                    }
                    if (StartStop.isPlayerDead(ppl)) {
                        blue.append(dead).append(" ");
                    } else blue.append(alive).append(" ");

                }for(Player ppl : TeamM.peopleOnTeam("red")){
                    try {
                        s1.resetScore();
                    }catch(Exception e){
                        Bukkit.getLogger().warning(e.getMessage());
                        Bukkit.getLogger().info(ChatColor.RED + "Lives: " + ChatColor.WHITE + ChatColor.BOLD + Lives.getLivesFromPlayer(p));
                    }
                    if (StartStop.isPlayerDead(ppl)) {
                        red.append(dead).append(" ");
                    } else red.append(alive).append(" ");
                }


                s2 = battle.getScore(ChatColor.BLUE + "Blue Team: " + blue); //todo make scoreboard display info about rebirth  ✓
                s1 = battle.getScore(ChatColor.RED + "Red Team:  " + red);
            }
            //s0 = battle.getScore("");
            //s0.setScore(0);
            s1.setScore(1);
            s2.setScore(2);
            s3.setScore(3);
            s4.setScore(4);
            s5.setScore(5);
            p.setScoreboard(Objects.requireNonNull(battle.getScoreboard()));
        }
    }



}