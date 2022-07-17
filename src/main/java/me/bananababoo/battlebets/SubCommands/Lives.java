package me.bananababoo.battlebets.SubCommands;

import me.bananababoo.battlebets.Arena;
import me.bananababoo.battlebets.Scoreboard;
import me.bananababoo.battlebets.TeamM;
import me.bananababoo.battlebets.Utils.StorageUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class Lives {
    public static int redLives;
    public static int blueLives;
    public static HashMap<Player, Integer> livesList= new HashMap<>();

    public static void Init(Arena red, Arena blue){
        initLives(red);
        initLives(blue);
        for(Player p : Bukkit.getOnlinePlayers()){
            if(TeamM.Team(p).equals("red")){
                livesList.put(p,redLives);
            }else if(TeamM.Team(p).equals("blue")) {
                livesList.put(p,redLives);
            }
        }
    }
    public static void Init(){
        for(Player p : Bukkit.getOnlinePlayers()){
            if(TeamM.Team(p).equals("red")){
                livesList.put(p,redLives);
            }else if(TeamM.Team(p).equals("blue")) {
                livesList.put(p,redLives);
            }
        }
    }
    public static void unInit(){
        livesList.clear();
    }

    public static void setMaxLives(int Lives, Arena a ){
        Arena arena = new Arena(a.getName(), a.getTeam(), a.getX(), a.getY(), a.getZ(), Lives);
        StorageUtil.setArena(arena);
        if(a.getTeam().equals("red")){
            redLives = Lives;
        }else if(a.getTeam().equals("blue")){
            blueLives = Lives;
        }
    }
    public static void initLives(Arena a){
        if(a.getTeam().equals("red")){
            redLives = a.getLives();
        }else if(a.getTeam().equals("blue")){
            blueLives = a.getLives();
        }

    }

    public static void setLivesFromArena(String arenaName){
        Arena red = StorageUtil.getArena(arenaName, "red");
        Arena blue = StorageUtil.getArena(arenaName, "blue");
        redLives = red.getLives();
        blueLives = red.getLives();
        Scoreboard.updateScoreBoard();
    }

    public static void removeLives(Player p){
        livesList.computeIfPresent(p, (k,v) -> v - 1);
        Scoreboard.updateScoreBoard();

    }
    public static int getLivesFromPlayer(Player p){
        return livesList.get(p);
    }

    public static void resetLives(Arena a){
        Arena redArena = new Arena(a.getName(), "red", a.getX(), a.getY(), a.getZ(), a.getLives());
        Arena blueArena = new Arena(a.getName(), "blue", a.getX(), a.getY(), a.getZ(), a.getLives());
        setRedMaxLives(redArena.getLives());
        setBlueMaxLives(blueArena.getLives());
        Init();
    }

    public static int getRedMaxLives() {
        return redLives;
    }

    public static void setRedMaxLives(int redLives) {
        Lives.redLives = redLives;
    }

    public static int getBlueMaxLives() {
        return blueLives;
    }

    public static void setBlueMaxLives(int blueLives) {
        Lives.blueLives = blueLives;
    }
}
